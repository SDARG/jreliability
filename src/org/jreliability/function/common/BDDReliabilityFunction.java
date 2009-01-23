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
package org.jreliability.function.common;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDTopEvent;
import org.jreliability.common.Transformer;
import org.jreliability.function.FunctionTransformer;
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
	 * The used {@code FunctionTransformer} to get the {@code Function} of each
	 * element of the {@code BDD}.
	 */
	protected final FunctionTransformer<T> transformer;

	/**
	 * The calculator for the top event.
	 */
	protected final BDDTopEvent<T> bDDTopEvent;

	/**
	 * Constructs a {@code BDDReliabilityFunction} with a given {@code BDD} and
	 * {@code FunctionTransformer}.
	 * 
	 * @param bdd
	 *            the bdd representing the reliabilityFunction
	 * 
	 * @param transformer
	 *            the transformer to transform bdd elements to
	 *            reliabilityFunction
	 */
	public BDDReliabilityFunction(BDD<T> bdd, FunctionTransformer<T> transformer) {
		super();
		this.transformer = transformer;
		this.bDDTopEvent = new BDDTopEvent<T>(bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(final double x) {

		final Transformer<T, Double> t = new Transformer<T, Double>() {
			public Double transform(T a) {
				return transformer.transform(a).getY(x);
			}
		};

		return bDDTopEvent.calculate(t);
		// return BDDs.calculateTop(bdd, t);
	}

}
