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

/**
 * The {@link WeibullFailureFunction} represents the 2-parameter Weibull
 * reliability function
 * <p>
 * {@code F(x) = 1 - e^-((x / nu)^beta))} = 1 - e^-((alpha * x)^beta))}<br>
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
public class WeibullFailureFunction extends WeibullReliabilityFunction {

	public WeibullFailureFunction(double alpha, double beta) {
		super(alpha, beta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		double y = 1.0 - Math.exp(-(Math.pow((alpha * x), beta)));
		return y;
	}
}
