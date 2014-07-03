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
 * The {@code LognormalReliabilityFunction} represents the
 * {@code ReliabilityFunction} of the {@code Lognormal reliabilityFunction}
 * <p>
 * {@code R(x) = 1 - F(x) = 0.5 - 0.5 * (Math.log(x) - mu) / (rho * Math.sqrt(2))}
 * <br />
 * with {@code mu, rho > 0}.
 * <p>
 * The {@code rho} and {@code mu} parameter represent the standard deviation and
 * mean of the variable's natural logarithm.
 * 
 * @author glass, khosravi
 * 
 */
public class LognormalReliabilityFunction implements ReliabilityFunction {

	/**
	 * The used mean of the natural logarithms of the times-to-failure.
	 */
	protected final double mu;

	/**
	 * The used standard deviation of the natural logarithms of the
	 * times-to-failure.
	 */
	protected final double rho;

	/**
	 * Constructs a {@code LognormalReliabilityFunction} with a given {@code mu}
	 * and {@code rho}.
	 * 
	 * @param mu
	 *            the mean of the variable's natural logarithm
	 * @param rho
	 *            the standard deviation of the variable's natural logarithm
	 */
	public LognormalReliabilityFunction(double mu, double rho) {
		this.mu = mu;
		this.rho = rho;
		if (!(rho > 0)) {
			throw new IllegalArgumentException(
					"LognormalReliabilityFunction: Mu and Rho should be greater 0.");
		}
	}

	/**
	 * 
	 * The estimated log-normal reliability function (the estimation uncertainty
	 * is 3E-7 for uniformly distributed random variables).
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 * 
	 * @param
	 * @return {@code R(x)}
	 */
	public double getY(double x) {
		double inTerm = (Math.log(x) - mu) / (rho * Math.sqrt(2));
		double y = 0.5 - 0.5 * erf(inTerm);

		return y;
	}

	/**
	 * Estimated Error Function (from the Formula 7.1.26 in
	 * "Handbook of Mathematical Functions" by M. Abramowitz and I. A. Stegun.)
	 * 
	 * @param x
	 * @return erf(x)
	 */
	private double erf(double x) {
		// constants
		final double a1 = 0.254829592;
		final double a2 = -0.284496736;
		final double a3 = 1.421413741;
		final double a4 = -1.453152027;
		final double a5 = 1.061405429;
		final double p = 0.3275911;

		double t = 1.0 / (1.0 + p * Math.abs(x));
		double y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t
				* Math.exp(-Math.abs(x) * Math.abs(x));

		if (x > 0)
			return y;
		return -y;
	}

}
