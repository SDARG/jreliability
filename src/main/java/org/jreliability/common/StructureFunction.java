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
package org.jreliability.common;

import java.util.Map;

/**
 * The {@link StructureFunction} {@code phi(T)} is a characteristic or indicator
 * function that determines whether the system provides correct service
 * ({@code phi(X) = true}) or has failed ({@code phi(X) = false}) given a set of
 * properly working and/or failed variables of type T.
 * 
 * @author glass
 * 
 * @param <T> the type of the variables
 * 
 */
public interface StructureFunction<T> {

	/**
	 * Corresponds to the structure function {@code phi} and returns whether the
	 * system works given a map of either working or failed variables
	 * 
	 * @param variables the map of working or failed variables
	 * @return true if the system still provides correct service and false in case
	 *         it failed
	 */
	public boolean isProvidingService(Map<T, Boolean> variables);

}
