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
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.function.ReliabilityFunction;


/**
 * The {@link ABGT} class calculates the ABGT importance for coherent and non-coherent systems
 * proposed by [ABGT17].
 * 
 * [ABGT17] ( https://doi.org/10.1016/j.ress.2016.12.013 )
 * 
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class ABGT<T> implements TimeDependentImportanceMeasure<T> {
	protected final BDD<T> bdd;
	protected final Transformer<T, ReliabilityFunction> transformer;
	protected final BDDTTRF<T> bddTTRF;
	protected final CriticalityCalculator<T> critCalc;
	
	/**
	 * Returns a {@link ABGT} calculator for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public ABGT(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		this.bdd = bdd;
		this.transformer = transformer;
		bddTTRF = new BDDTTRF<>(bdd.getProvider());
		
		critCalc = new CriticalityCalculator<>(bdd, transformer);
	}
	
	/**
	 * Calculates the ABGT importances for all components at the specified time.
	 * 
	 * Uses equation 15 and 23 of [ABGT17] to calculate the importance from the failure 
	 * and repair {@link CriticalityBDDs} of the components.
	 * 
	 * @param time
	 *            Time t at which the ABGT importance of all components is calculated.
	 *            Must be greater than 0, otherwise an IllegalArgumentException is thrown.
	 *            
	 * @return results
	 * 			  Map of components and their respective ABGT importance
	 * 			  at time t.
	 */
	public Map<T, Double> calculate(double time) {
		Map<T, Double> results = new HashMap<>();		

		if (time <= 0) {
			throw new IllegalArgumentException("Importance measure not defined for time <= 0.");
		}
		
		for (T var: bdd.getVariables()) {				
			CriticalityBDDs<T> critBdds = critCalc.getCriticalityBDDs(var);
			
			/* Equation 15 of [ABGT17] */
			BDD<T> abgtBdd = critBdds.getFailureCriticalityBDD().or(critBdds.getRepairCriticalityBdd());
			
			/* Equation 23 of [ABGT17] */
			double abgt_im = bddTTRF.convert(abgtBdd, transformer).getY(time);	
			
			results.put(var, abgt_im);
		}		
		
		return results;
	}
}
