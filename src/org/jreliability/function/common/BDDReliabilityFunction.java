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
import org.jreliability.bdd.BDDs;
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
	 * The BDD representing the {@code Distribution}.
	 */
	protected final BDD<T> bdd;

	/**
	 * The used {@code FunctionTransformer} to get the {@code Function} of each
	 * element of the {@code BDD}.
	 */
	protected final FunctionTransformer<T> transformer;

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
		this.bdd = bdd;
		this.transformer = transformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(final double x) {
		
		final Transformer<T, Double> t = new Transformer<T, Double>() {
			@Override
			public Double transform(T a) {
				return transformer.transform(a).getY(x);
			}
		};
		
		return BDDs.calculateTop(bdd, t);
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
	 * Returns the {@code Transformer}.
	 * 
	 * @return the transformer
	 */
	public FunctionTransformer<T> getTransformer() {
		return transformer;
	}

}
