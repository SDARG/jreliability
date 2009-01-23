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
package org.jreliability.booleanfunction;

/**
 * The {@code ExistsTransformer} returns whether a given object {@code T} is a
 * variable that should be excluded from the structure function using the
 * {@code exists-operator}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of variable
 * 
 */
public interface ExistsTransformer<T> {

	/**
	 * Returns {@code true} if the object {@code T} is an {@code exists}-variable.
	 * 
	 * @param t
	 *            the object t
	 * @return true of the object is an exists-variable
	 */
	public boolean transform(T t);

}
