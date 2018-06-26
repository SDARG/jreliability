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

package org.jreliability.function.common;

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.SequentialFunction;

/**
 * The {@link WeibullReliabilityFunction} represents the 2-parameter Weibull
 * reliability function
 * <p>
 * {@code R(x) = 1 - F(x) = e^-((x / nu)^beta))} = e^-((alpha * x)^beta))}<br>
 * with {@code alpha, beta > 0}.
 * <p>
 * While the parameter {@code alpha = 1 / nu} scales the
 * {@link ReliabilityFunction} and, thus, somehow corresponds to the
 * failure-rate {@code lambda}, the {@code beta} parameter determines the shape
 * of the {@link ReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public class WeibullReliabilityFunction extends SequentialFunction implements ReliabilityFunction {

	/**
	 * The {@code alpha} parameter somehow resembles the failure-rate
	 * {@code lambda}.
	 */
	protected final double alpha;

	/**
	 * The used shape of the {@link WeibullReliabilityFunction}.
	 */
	protected final double beta;

	/**
	 * Constructs a {@link WeibullReliabilityFunction} with a given
	 * {@code alpha} and {@code beta}.
	 * 
	 * @param alpha
	 *            the scale value
	 * @param beta
	 *            the shape value
	 */
	public WeibullReliabilityFunction(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
		if (alpha <= 0.0 || beta <= 0.0) {
			throw new IllegalArgumentException("WeibullReliabilityFunction: Alpha and Beta should be greater 0.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		double y = Math.exp(-(Math.pow((alpha * x), beta)));
		return y;
	}
}
