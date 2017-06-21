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
package org.jreliability.function.common;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code NormalReliabilityFunction} represents the
 * {@code ReliabilityFunction} of the {@code Normal reliabilityFunction}
 * <p>
 * {@code R(x) = 1 - F(x) = 0.5 - 0.5 * (x - mu) / (rho * Math.sqrt(2))}<br>
 * with {@code mu, rho > 0}.
 * <p>
 * The {@code rho} and {@code mu} parameter represent the standard deviation and
 * mean of the variable's natural logarithm.
 * 
 * @author khosravi
 * 
 */
public class NormalReliabilityFunction implements ReliabilityFunction {

	/**
	 * The used mean of the natural logarithms of the times-to-failure.
	 */
	protected final double mu;

	/**
	 * The used standard deviation of the natural logarithms of the
	 * times-to-failure.
	 */
	protected final double rho;

	LognormalReliabilityFunction lognormalReliabilityFunction;

	/**
	 * Constructs a {@code LognormalReliabilityFunction} with a given {@code mu}
	 * and {@code rho}.
	 * 
	 * @param mu
	 *            the mean of the variable's natural logarithm
	 * @param rho
	 *            the standard deviation of the variable's natural logarithm
	 */
	public NormalReliabilityFunction(double mu, double rho) {
		this.mu = mu;
		this.rho = rho;
		lognormalReliabilityFunction = new LognormalReliabilityFunction(mu, rho);
		if (rho <= 0.0) {
			throw new IllegalArgumentException("LognormalReliabilityFunction: Mu and Rho should be greater 0.");
		}
	}

	/**
	 * 
	 * The estimated log-normal reliability function (the estimation uncertainty
	 * is 3E-7 for uniformly distributed random variables).
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 * 
	 * @param x
	 *            the x value
	 * @return {@code R(x)}
	 */
	@Override
	public double getY(double x) {
		if (x == mu) {
			x += 0.000000000000001;
		}
		return this.lognormalReliabilityFunction.getY(Math.exp(x));
	}
}
