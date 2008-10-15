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
 * The {@code Transformer} transforms object {@code A} into object {@code B}.
 * 
 * @author glass
 * 
 * @param <A>
 *            the object a
 * @param <B>
 *            the object b
 */
public interface Transformer<A, B> {

	/**
	 * Returns object {@code B} for a given object {@code A}.
	 * 
	 * @param a
	 *            the object {@code A}
	 * @return object B for a given object A
	 */
	public B transform(A a);

}
