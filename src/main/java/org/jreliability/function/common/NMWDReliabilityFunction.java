/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.function.common;

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.SequentialFunction;

/**
 * The {@link NMWDReliabilityFunction} represents the NMWD
 * {@link ReliabilityFunction} based on the {@link WeibullReliabilityFunction}
 * with a third parameter:
 * <p>
 * {@code R(x) = 1 - F(x) = e^-(a * x^b * e^(lambda * x))}<br>
 * with {@code lambda, a > 0 and b >= 0}.
 * <p>
 * While the parameter {@code lambda} scales the reliabilityFunction, the
 * {@code a} and {@code b} parameters determine the shape of the
 * {@link ReliabilityFunction}. This function allows to model bathtub-shaped
 * failure rates that can directly be derived from given Weibull plots, cf. [1].
 * <p>
 * [1] Lai, C.D. and Xie, Min and Murthy, D. N. P.: A Modified Weibull
 * Distribution. In IEEE Transactions on Reliability, Vol. 52, No. 1, 2003.
 * 
 * @author glass
 * 
 */
public class NMWDReliabilityFunction extends SequentialFunction implements ReliabilityFunction {

	/**
	 * The {@code lambda} parameter somehow resembles the failure-rate
	 * {@code lambda}.
	 */
	protected final double lambda;

	/**
	 * The used shape of the {@link NMWDReliabilityFunction}.
	 */
	protected final double a;

	/**
	 * The used shape of the {@link NMWDReliabilityFunction}.
	 */
	protected final double b;

	/**
	 * Constructs a {@link NMWDReliabilityFunction} with a given {@code lambda},
	 * {@code a}, and {@code b}.
	 * 
	 * @param lambda
	 *            the scale value
	 * @param a
	 *            the first shape value
	 * @param b
	 *            the second shape value
	 */
	public NMWDReliabilityFunction(double lambda, double a, double b) {
		this.lambda = lambda;
		this.a = a;
		this.b = b;
		if (!(lambda > 0.0) || !(a > 0.0) || !(b >= 0.0)) {
			throw new IllegalArgumentException(
					"NMWDReliabilityFunction: Lambda and A must be greater, B be greater equal 0.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		double exponent = -a * Math.pow(x, b) * Math.exp(lambda * x);
		double y = Math.exp(exponent);
		return y;
	}
}
