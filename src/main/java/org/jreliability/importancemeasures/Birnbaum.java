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
 * The {@link Birnbaum} class calculates the Birnbaum Importance for coherent systems.
 * This is done via boolean expression in the {@link CriticalityCalculator} using [ABGT17].
 * As a result, it is equivalent to the importance measure proposed by Zaitseva and 
 * Levashenko in [ZL13].
 * 
 * [ABGT17] ( https://doi.org/10.1016/j.ress.2016.12.013 )
 * [ZL13]   ( https://doi.org/10.1134/S000511791302001X )
 * 
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class Birnbaum<T> implements TimeDependentImportanceMeasure<T> {
	protected final CriticalityCalculator<T> critCalc;
	
	/**
	 * Returns a {@link Birnbaum} calculator for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public Birnbaum(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		critCalc = new CriticalityCalculator<>(bdd, transformer);
	}	

	/**
	 * Calculates the Birnbaum importances for all components at the specified time.
	 * 
	 * Uses equation 6 of [ABGT17] to calculate the importance from the failure criticality 
	 * of the component.
	 * 
	 * @param time
	 *            Time t at which the Birnbaum importance of all components is calculated.
	 *            Must be greater than 0, otherwise an IllegalArgumentException is thrown.
	 *            
	 * @return results
	 * 			  Map of components and their respective Birnbaum importance
	 * 			  at time t.
	 */
	public Map<T, Double> calculate(double time) {		
		Map<T, Double> results = new HashMap<>();
		
		/* Throws IllegalArgumentException for time <= 0 */
		Map<T, CriticalityValues> criticalities = critCalc.getCriticalityValues(time);
		
		criticalities.forEach( (component, crit) -> {
			/* Equation 6 of [ABGT17] */
			double birnbaum_im = crit.getFailureCriticality();
			results.put(component, birnbaum_im);
		});
		
		return results;		
	}
}
