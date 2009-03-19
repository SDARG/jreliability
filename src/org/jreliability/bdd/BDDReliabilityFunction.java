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
package org.jreliability.bdd;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code BDDReliabilityFunction} represents the {@code ReliabilityFunction}
 * that is inherently included in a {@code BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of variable
 */
public class BDDReliabilityFunction<T> implements ReliabilityFunction {

	/**
	 * The BDD representing the {@code Distribution}.
	 */
	protected final BDD<T> bdd;

	/**
	 * The used {@code Transformer} to get the {@code Function} of each element
	 * of the {@code BDD}.
	 */
	protected final Transformer<T, ReliabilityFunction> functionTransformer;

	/**
	 * The calculator for the top event.
	 */
	protected final BDDTopEvent<T> topEvent;

	/**
	 * Constructs a {@code BDDReliabilityFunction} with a given {@code BDD} and
	 * {@code Transformer}.
	 * 
	 * @param bdd
	 *            the bdd representing the reliabilityFunction
	 * 
	 * @param functionTransformer
	 *            the functionTransformer to transform bdd elements to
	 *            reliabilityFunction
	 */
	public BDDReliabilityFunction(BDD<T> bdd, Transformer<T, ReliabilityFunction> functionTransformer) {
		super();
		this.bdd = bdd;
		this.functionTransformer = functionTransformer;
		this.topEvent = new BDDTopEvent<T>(bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(final double x) {

		final Transformer<T, Double> t = new Transformer<T, Double>() {
			public Double transform(T a) {
				return functionTransformer.transform(a).getY(x);
			}
		};

		return topEvent.calculate(t);
	}

	/**
	 * Returns the {@code BDD}.
	 * 
	 * @return the bdd
	 */
	public BDD<T> getBdd() {
		return bdd;
	}

	/**
	 * Returns the used {@code Transformer}.
	 * 
	 * @return the used functionTransformer
	 */
	public Transformer<T, ReliabilityFunction> getTransformer() {
		return functionTransformer;
	}

}
