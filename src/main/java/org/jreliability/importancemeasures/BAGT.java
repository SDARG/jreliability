/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.importancemeasures;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.ReliabilityFunction;


/**
 * The {@link BAGT} class calculates the time-independent importance of components
 * proposed by [BAGT16].
 * 
 * [BAGT16] ( https://doi.org/10.1016/j.ejor.2016.03.054 )
 * 
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class BAGT<T> implements ImportanceMeasure {
	protected final BDD<T> bdd;
	protected final Transformer<T, ReliabilityFunction> transformer;
	protected final BDDProvider<T> provider;
	protected final BDDTTRF<T> bddTTRF;
	protected final MomentEvaluator moment;
	
	protected final double mttf;
	
	public enum Variant {
		MINUS,
		PLUS,
		MINUS_NORMALIZED,
		PLUS_NORMALIZED
	}
	
	/**
	 * Returns a {@link BAGT} calculator for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public BAGT(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		this.bdd = bdd;
		this.transformer = transformer;
		provider = bdd.getProvider();
		bddTTRF = new BDDTTRF<>(provider);
		moment = new MomentEvaluator(1);
				
		/* bddTTRF deallocates the bdd parameter -> copy is needed */
		mttf = moment.evaluate(bddTTRF.convert(bdd.copy(), transformer));	
	}	
	
	/**
	 * Calculates one of the BAGT importance measure variants.
	 * 
	 * Note: This function might have infinite run times for some variants
	 * 		 using non coherent systems. This is due to the {@link MomentEvaluator}
	 * 		 having an infinite run time for the restricted reliability functions
	 * 		 in those cases.
	 * 
	 * @param variant
	 * 			  The variant of BAGT to be calculated.
	 *         
	 * @return results
	 * 			  Map of components and their respective BAGT importance.
	 * 
	 */
	public Map<T, Double> calculate(Variant variant) {
		Map<T, Double> results = new HashMap<>();
		BDD<T> restrictedBdd;
		double bagt_im;
	
		for (T var: bdd.getVariables()) {			
			if (variant == Variant.MINUS || variant == Variant.MINUS_NORMALIZED) {
				restrictedBdd = bdd.restrict(provider.get(var).not());	/* Fix var to 0 ie. Component never works */
			}
			else {
				restrictedBdd = bdd.restrict(provider.get(var));		/* Fix var to 1 ie. Component always works */
			}

			ReliabilityFunction restrictedReliability = bddTTRF.convert(restrictedBdd, transformer);
			
			/* This will cause infinite run time for some cases (eg. BAGT+ of TCNC component 1 ) */
			double restrictedMttf = moment.evaluate(restrictedReliability);
						
			if (variant == Variant.MINUS_NORMALIZED || variant == Variant.PLUS_NORMALIZED) {
				/* Equation 19 of [BAGT16] */
				bagt_im = Math.abs(mttf - restrictedMttf) / mttf;
			}
			else {
				/* Equation 18 of [BAGT16] */
				bagt_im = Math.abs(mttf - restrictedMttf);
			}
			
			results.put(var, bagt_im);
		}		
	
	return results;
		
	}
}
