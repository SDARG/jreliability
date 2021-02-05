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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.DensityFunction;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.UnreliabilityFunction;


/**
 * The {@link BarlowProschan} class calculates the time-independent importance for 
 * coherent systems proposed by Barlow and Proschan in [BP75].
 * Uses the implementation of the BP importance in [Nat09].
 * 
 * [BP75]  ( https://doi.org/10.1016/0304-4149(75)90013-7 )
 * [Nat09] ( https://doi.org/10.1007/s11009-008-9079-1 )
 * 
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class BarlowProschan <T> implements ImportanceMeasure {
	protected final BDD<T> bdd;
	protected final Transformer<T, ReliabilityFunction> transformer;
	protected final BDDProvider<T> provider;
	protected final BDDTTRF<T> bddTTRF;
	protected final MomentEvaluator moment;
	
	/**
	 * Returns a {@link BarlowProschan} calculator for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public BarlowProschan(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		this.bdd = bdd;
		this.transformer = transformer;
		provider = bdd.getProvider();
		bddTTRF = new BDDTTRF<>(provider);
		moment = new MomentEvaluator(1);
	}

	/**
	 * Calculates the Barlow Proschan importances for all components.
	 * It is only defined for coherent systems, ie. for systems where its reliability is
	 * with component x working is always higher, than when it is not working.
	 * As it integrates over the difference in the reliability, the difference must not
	 * be negative ie. the system must be coherent.
	 * 
	 * Uses equation 2 of [Nat09] to create the BP function, to be integrated.
	 *         
	 * @return results
	 * 			  Map of components and their respective Barlow Proschan importance
	 * 
	 * @throws ArithmeticException
	 * 			  In case a non-coherent system is tried to be evaluated.
	 * 
	 */
	public Map<T, Double> calculate() throws ArithmeticException {
		Map<T, Double> results = new HashMap<>();

		for (T var: bdd.getVariables()) {
			
			BDD<T> restrictedBdd_1 = bdd.restrict(provider.get(var));			/* Fix var to 1 ie. Component always works */
			BDD<T> restrictedBdd_0 = bdd.restrict(provider.get(var).not());		/* Fix var to 0 ie. Component never works */

			ReliabilityFunction restrictedReliability_1 = bddTTRF.convert(restrictedBdd_1, transformer);
			ReliabilityFunction restrictedReliability_0 = bddTTRF.convert(restrictedBdd_0, transformer);
			
			DensityFunction failureDensity = new DensityFunction(new UnreliabilityFunction(transformer.transform(var)));
			
			/* Equation 2 of [Nat09] */
			ReliabilityFunction bp_function = new ReliabilityFunction() {
				public double getY(double x) {
					double result = (restrictedReliability_1.getY(x) - restrictedReliability_0.getY(x)) * failureDensity.getY(x); 	
					if (result < 0) {
						throw new ArithmeticException("Cannot evaluate first moment of negative function.");
					}
					return result;
				}				
				public List<Double> getY(List<Double> xs) {
					List<Double> results = new ArrayList<Double>();
					for (Double x : xs) {
						results.add(getY(x));
					}
					return results;
				}
			};
			
			double bp_im = moment.evaluate(bp_function);
			
			results.put(var, bp_im);
		}
		
		return results;
	}
}
