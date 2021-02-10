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

import org.jreliability.bdd.BDD;


/**
 * The {@link CriticalityBDDs} class is a wrapper class to encapsulate the failure
 * and repair criticality {@link BDD}s of a component.
 * 
 * @author oehmen
 * 
 * @param <T>
 *            The type of the variables of the {@link BDD}
 */
public class CriticalityBDDs <T> {
	protected final BDD<T> failureCriticalityBdd;
	protected final BDD<T> repairCriticalityBdd;
	
	public CriticalityBDDs(BDD<T> failureCriticalityBdd, BDD<T> repairCriticalityBdd) {
		this.failureCriticalityBdd = failureCriticalityBdd;
		this.repairCriticalityBdd = repairCriticalityBdd;
	}
	
	public BDD<T> getFailureCriticalityBDD() {
		return failureCriticalityBdd;
	}
	
	public BDD<T> getRepairCriticalityBdd() {
		return repairCriticalityBdd;
	}
}
