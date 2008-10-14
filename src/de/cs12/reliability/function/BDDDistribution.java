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
package de.cs12.reliability.function;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code BDDDistribution} represents the {@code Distribution} that is
 * inherently included in a {@code BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of variable
 */
public class BDDDistribution<T> extends AbstractDistribution {

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
	 * Constructs a {@code BDDDistribution} with a given {@code BDD} and {@code
	 * FunctionTransformer}.
	 * 
	 * @param bdd
	 *            the bdd representing the distribution
	 * 
	 * @param transformer
	 *            the transformer to transform bdd elements to function
	 */
	public BDDDistribution(BDD<T> bdd, FunctionTransformer<T> transformer) {
		super();
		this.bdd = bdd;
		this.transformer = transformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		return Top.calculateTop(bdd, transformer, x);
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
