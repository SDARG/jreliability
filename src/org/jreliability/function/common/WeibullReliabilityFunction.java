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

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code WeibullReliabilityFunction} represents the {@code
 * ReliabilityFunction} of the {@code Weibull reliabilityFunction}
 * <p>
 * {@code R(x) = 1 - F(x) = e^-(alpha * (x^beta))}.
 * <p>
 * While the parameter {@code alpha} scales the reliabilityFunction and, thus, somehow
 * corresponds to the failure-rate, the {@code beta} parameter determines the
 * {@code shape} of the reliabilityFunction.
 * 
 * @author glass
 * 
 */
public class WeibullReliabilityFunction implements ReliabilityFunction {

	/**
	 * The {@code alpha} parameter somehow resembles the failure-rate {@code
	 * lambda}.
	 */
	protected final double alpha;

	/**
	 * The used shape of the {@code WeibullReliabilityFunction}.
	 */
	protected final double beta;

	/**
	 * Constructs a {@code WeibullReliabilityFunction} with a given {@code
	 * alpha} and {code beta}.
	 * 
	 * @param alpha
	 *            the scale value
	 * @param beta
	 *            the shape value
	 */
	public WeibullReliabilityFunction(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		double y = Math.exp(-(alpha * Math.pow(x, beta)));
		return y;
	}
}
