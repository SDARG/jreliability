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
import org.jreliability.function.ReliabilityFunction;


/** 
 * The {@link Vaurio} class calculates the Vaurio Importance for non-coherent systems 
 * proposed by [Vau16].
 * Uses [ABGT17] for an easy representation.
 * 
 * [Vau16] ( https://doi.org/10.1016/j.ress.2015.11.007 )
 * [ABGT17] ( https://doi.org/10.1016/j.ress.2016.12.013 )
 *
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class Vaurio<T> implements TimeDependentImportanceMeasure<T> {
	protected final CriticalityCalculator<T> critCalc;
	
	/**
	 * Returns a {@link Vaurio} calculator for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public Vaurio(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		critCalc = new CriticalityCalculator<>(bdd, transformer);
	}	
	
	/**
	 * Calculates the Vaurio importances for all components at the specified time.
	 * 
	 * Uses equation 8 of [ABGT17] to calculate the importance from the failure 
	 * and repair {@link CriticalityValues} of the components.
	 *   
	 * @param time
	 *            Time t at which the Vaurio importance of all components is calculated.
	 *            Must be greater than 0, otherwise an IllegalArgumentException is thrown.
	 *            
	 * @return results
	 * 			  Map of components and their respective Vaurio importance
	 * 			  at time t.
	 */
	public Map<T, Double> calculate(double time) {
		Map<T, Double> results = new HashMap<>();
		
		/* Throws IllegalArgumentException for time <= 0 */
		Map<T, CriticalityValues> criticalities = critCalc.getCriticalityValues(time);
		
		criticalities.forEach( (component, crit) -> {
			/* Equation 8 of [ABGT17] */
			double vaurio_im = crit.getFailureCriticality() - crit.getRepairCriticality();	
			results.put(component, vaurio_im);
		});
		
		return results;
	}
}