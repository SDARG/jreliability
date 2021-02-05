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

import java.util.Map;


/**
 * The {@link TimeDependentImportanceMeasure} is used to assign a importance
 * measure value to each variable T of the system at a specific time t.
 * 
 * @author oehmen
 * 
 * @param <T>
 *            The type of the variables of the system
 */
public interface TimeDependentImportanceMeasure<T> extends ImportanceMeasure {
	
	
	/**
	 * Assign a importance value to each variable T of the system at a specific time t.
	 * 
	 * @param time
	 *            Time t at which the importance value of the variables is calculated.
	 *            
	 * @return results
	 * 			  Map of variables and their respective importance values at time t.
	 */
	public Map<T, Double> calculate(double time);
}
