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
import org.jreliability.function.ReliabilityFunction;


/** 
 * The {@link RAW} class calculates the RAW values for coherent and non-coherent systems.
 * Uses [Ali17] to calculate them.
 *
 * [Ali17] ( https://nbn-resolving.org/urn:nbn:de:bvb:29-opus4-87185 )
 *
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class RAW<T> implements TimeDependentImportanceMeasure<T> {
	
	protected final BDD<T> bdd;
	protected final Transformer<T, ReliabilityFunction> transformer;
	protected final BDDProvider<T> provider;
	protected final BDDTTRF<T> bddTTRF;
	
	/**
	 * Returns a {@link RAW} calculator for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public RAW(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		this.bdd = bdd;
		this.transformer = transformer;
		provider = bdd.getProvider();
		bddTTRF = new BDDTTRF<>(provider);
	}
	
	/**
	 * Calculates the {@link RAW} values for all components at the specified time.
	 * 
	 * Uses equation 4.7 of [Ali17] to calculate the RAW values from the conditional
	 * unreliability of the system if a component fails and the nominal system unreliability.
	 * 
	 * Note: [BAGT16] (https://doi.org/10.1016/j.ejor.2016.03.054) use a slightly different notation. 
	 *   	 They use U_i^+ instead of U_i^- because they use failure probabilities, while this
	 *   	 framework generally uses success probabilities for the components, as does [Ali17].
	 *   	 In [BAGT16] U_i^+ corresponds to the presence of a failure in a component.
	 *   	 In [Ali17] U_i^- corresponds to the absence of a correctly working component.
	 *   	 They are equivalent.
	 *   
	 * @param time
	 *            Time t at which the RAW values of all components is calculated.
	 *            Must be greater than 0, otherwise an IllegalArgumentException is thrown.
	 *            
	 * @return results
	 * 			  Map of components and their respective RAW values at time t.
	 */
	public Map<T, Double> calculate(double time) {
		Map<T, Double> results = new HashMap<>();
		BDD<T> restrictedBdd;
		ReliabilityFunction restrictedSystemReliabilityFunction;
		
		if (time <= 0) {
			throw new IllegalArgumentException("Importance measure not defined for time <= 0.");
		}
		
		/* bddTTRF.convert() deallocates bdd -> use a copy */
		ReliabilityFunction systemReliabilityFunction = bddTTRF.convert(bdd.copy(), transformer);	
		double systemUnreliability = 1 - systemReliabilityFunction.getY(time);
				
		for (T var: bdd.getVariables()) {
			restrictedBdd = bdd.restrict(provider.get(var).not());	/* Fix var to 0 ie. Component never works */
			restrictedSystemReliabilityFunction = bddTTRF.convert(restrictedBdd, transformer);
			
			/* System Unreliability at time t with var i not working */
			double u_i_minus = 1 - restrictedSystemReliabilityFunction.getY(time);	

			/* Equation 4.7 of [Ali17] */
			double raw = u_i_minus / systemUnreliability;
			
			results.put(var, raw);
		}	
		
		return results;
	}
}
