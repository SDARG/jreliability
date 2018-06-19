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

package org.jreliability.bdd;

import java.util.List;

/**
 * The {@link BDDProvider} provides the actual {@link BDD}s for each component.
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public interface BDDProvider<T> {

	/**
	 * Register variables in the specified order.
	 * 
	 * @param variables
	 *            the variables to register
	 */
	public void add(List<T> variables);

	/**
	 * Register variables in the specified order.
	 * 
	 * @param variables
	 *            the variables to register
	 */
	public void add(T... variables);

	/**
	 * Returns the {@code false} or {@code 0} BDD.
	 * 
	 * @return the false or 0 BDD
	 */
	public BDD<T> zero();

	/**
	 * Returns the {@code true} or {@code 1} BDD.
	 * 
	 * @return the true or 1 BDD
	 */
	public BDD<T> one();

	/**
	 * Returns the {@link BDD} that represents the {@code variable}.
	 * 
	 * @param variable
	 *            the variable
	 * @return the BDD that represents the Object
	 */
	public BDD<T> get(T variable);

	/**
	 * Returns the {@code variable} that is represented by the {@link BDD}.
	 * 
	 * @param bdd
	 *            the bdd
	 * @return the variable represented by the BDD
	 */
	public T get(BDD<T> bdd);

}
