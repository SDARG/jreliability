/**
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd.crn;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDReliabilityFunction;
import org.jreliability.cra.Adapter;
import org.jreliability.cra.CompositionalReliabilityNode;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code BDDTTRF} transforms a {@code Boolean function} represented as a
 * {@code Term} into a {@code ReliabilityFunction} or, if needed, into a
 * {@code BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variables
 */
public class BDDtoReliabilityFunction<T> implements CompositionalReliabilityNode<BDDReliabilityFunction<T>> {

	private Adapter<T, ReliabilityFunction> functionTransformer;
	private BDD<T> bdd;

	@SuppressWarnings("unchecked")
	@Override
	public void set(Object input) {
		if (input instanceof BDD) {
			bdd = (BDD<T>) input;
		} else if (input instanceof Adapter) {
			functionTransformer = (Adapter<T, ReliabilityFunction>) input;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public BDDReliabilityFunction<T> get() {
		BDDReliabilityFunction<T> function = new BDDReliabilityFunction<T>(bdd, functionTransformer);
		bdd.free();
		return function;
	}

	@Override
	public void requires() {
		// TODO Auto-generated method stub

	}

	@Override
	public void provides() {
		// TODO Auto-generated method stub

	}
}
