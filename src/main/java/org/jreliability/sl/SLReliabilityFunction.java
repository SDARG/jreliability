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
package org.jreliability.sl;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.SequentialFunction;
import org.jreliability.function.UnreliabilityFunction;

/**
 * The {@link SLReliabilityFunction} represents the {@link ReliabilityFunction}
 * using stochastic logic as implemented by {@link SL}.
 * 
 * @author glass, jlee
 * 
 * @param <T> the type of variable
 */
public class SLReliabilityFunction<T> extends SequentialFunction implements ReliabilityFunction {

	/**
	 * The {@link SL} representing the {@link UnreliabilityFunction}.
	 */
	protected final SL<T> stochasticLogic;

	/**
	 * The used {@link Transformer} to get the {@link ReliabilityFunction} of each
	 * element of the {@link SL}.
	 */
	protected final Transformer<T, ReliabilityFunction> functionTransformer;

	/**
	 * Constructs a {@link SLReliabilityFunction} with a given {@link SL} and
	 * {@link Transformer}.
	 * 
	 * @param stochasticLogic     the stochastic logic representing the
	 *                            reliabilityFunction
	 * @param functionTransformer the functionTransformer to transform stochastic
	 *                            logic elements to reliabilityFunction
	 */
	public SLReliabilityFunction(SL<T> stochasticLogic, Transformer<T, ReliabilityFunction> functionTransformer) {
		this.stochasticLogic = stochasticLogic;
		this.functionTransformer = functionTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(final double x) {

		final Transformer<T, Double> t = new Transformer<T, Double>() {
			/**
			 * Default {@link Transformer}.
			 * 
			 * @param a parameter a
			 * @return the double value of a
			 */
			@Override
			public Double transform(T a) {
				return functionTransformer.transform(a).getY(x);
			}
		};

		return stochasticLogic.getProbabiliy(t);
	}

}
