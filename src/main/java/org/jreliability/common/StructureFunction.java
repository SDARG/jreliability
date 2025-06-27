/**
 * 
 */
package org.jreliability.common;

import java.util.Map;

/**
 * The {@link StructureFunction} {@code phi(T)} is a characteristic or indicater
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
