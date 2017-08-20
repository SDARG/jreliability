/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.common;

/**
 * The {@link Failure} represents the occurrence of a failure of an object and
 * delivers the time of the failure as the information.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variables
 */
public class Failure<T> extends Occurrence<T> {

	/**
	 * Constructs a {@link Failure} with a given object and a time.
	 * 
	 * @param t
	 *            the object
	 * @param time
	 *            the time
	 */
	public Failure(T t, double time) {
		super(t, time);
	}

}
