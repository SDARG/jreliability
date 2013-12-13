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
package org.jreliability.bdd;

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
public class BDDTTRF<T> implements CompositionalReliabilityNode<BDD<T>, BDDReliabilityFunction<T>> {

	public final Adapter<T, ReliabilityFunction> functionTransformer;

	public BDDTTRF(Adapter<T, ReliabilityFunction> functionTransformer) {
		this.functionTransformer = functionTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(org.jreliability.
	 * booleanfunction.Term, org.apache.commons.collections15.Transformer)
	 */
	@Override
	public BDDReliabilityFunction<T> convert(BDD<T> bdd) {
		BDDReliabilityFunction<T> function = new BDDReliabilityFunction<T>(bdd, functionTransformer);
		bdd.free();
		return function;
	}
}
