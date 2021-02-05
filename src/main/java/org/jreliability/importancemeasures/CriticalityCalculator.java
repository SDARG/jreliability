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
 * The {@link CriticalityCalculator} is a utility class used to calculate the {@link CriticalityValues}
 * and {@link CriticalityBDDs} of the components of a system.
 * Uses [ABGT17] in order to calculate the {@link CriticalityBDDs} and the corresponding {@link CriticalityValues}
 * via boolean expression.
 * 
 * [ABGT17] ( https://doi.org/10.1016/j.ress.2016.12.013 )
 * 
 * @author oehmen
 *
 * @param <T>
 *            The type of the variables of the {@link BDD} of the system
 */
public class CriticalityCalculator<T> {
	protected final BDD<T> bdd;
	protected final Transformer<T, ReliabilityFunction> transformer;
	protected final BDDProvider<T> provider;
	protected final BDDTTRF<T> bddTTRF;
	
	/**
	 * Returns a {@link CriticalityCalculator} for a specific system with its {@link BDD}
	 * and {@link ReliabilityFunction} {@link Transformer}. 
	 * 
	 * @param bdd
	 * 			The {@link BDD} representing the system structure function
	 * 
	 * @param transformer
	 * 			The {@link Transformer} used to get the {@link ReliabilityFunction} of
	 * 			variables present in the {@link BDD} 
	 */
	public CriticalityCalculator(BDD<T> bdd, Transformer<T, ReliabilityFunction> transformer) {
		this.bdd = bdd;
		this.transformer = transformer;
		provider = bdd.getProvider();
		bddTTRF = new BDDTTRF<>(provider);
	}
	
	/**
	 * Calculates the {@link CriticalityValues} for all components at the specified time.
	 * 
	 * Uses equations 5 and 6 of [ABGT17] to calculate the {@link CriticalityValues} via 
	 * the {@link CriticalityBDDs} instead of the partial derivatives.  
	 * 
	 * @param time
	 *            Time t at which the {@link CriticalityValues} of all components is calculated.
	 *            Must be greater than 0, otherwise an IllegalArgumentException is thrown.
	 *            
	 * @return results
	 * 			  Map of components and their respective {@link CriticalityValues} values at time t.
	 */
	public Map<T, CriticalityValues> getCriticalityValues(double time) {
		Map<T, CriticalityValues> criticalities = new HashMap<>();		

		if (time <= 0) {
			throw new IllegalArgumentException("Criticality not defined for time <= 0.");
		}
		
		for (T var: bdd.getVariables()) {			
			CriticalityBDDs<T> critBdds = getCriticalityBDDs(var);
			
			/* Equations 5 & 6 of [ABGT17] */
			double failureCriticality = bddTTRF.convert(critBdds.getFailureCriticalityBDD(), transformer).getY(time);	
			double repairCriticality = bddTTRF.convert(critBdds.getRepairCriticalityBdd(), transformer).getY(time);
			
			criticalities.put(var, new CriticalityValues(failureCriticality, repairCriticality));
		}	
		
		return criticalities;
	}
	
	/**
	 * Calculates the failure and repair {@link CriticalityBDDs} of the given variable (component).
	 * Uses Proposition 1 of [ABGT17].
	 * 
	 * @param var
	 * 			Variable for wich the failure and repair {@link CriticalityBDDs} are calculated.
	 * 			
	 * @return CriticalityBDDs
	 * 			Wrapper for the failure criticality {@link BDD} and the repair criticality {@link BDD}.
	 */
	public CriticalityBDDs<T> getCriticalityBDDs(T var) {
		BDD<T> restrictedBdd_1 = bdd.restrict(provider.get(var));			/* Fix var to 1 ie. Component always works */
		BDD<T> restrictedBdd_0 = bdd.restrict(provider.get(var).not());		/* Fix var to 0 ie. Component never works */
		
		BDD<T> failureCriticalityBdd = restrictedBdd_1.and(restrictedBdd_0.not()); 	/* Prop. 1.1 of [ABGT17] */
		BDD<T> repairCriticalityBdd = restrictedBdd_1.not().and(restrictedBdd_0);	/* Prop. 1.2 of [ABGT17] */
		
		return new CriticalityBDDs<>(failureCriticalityBdd, repairCriticalityBdd);
	}	
}
