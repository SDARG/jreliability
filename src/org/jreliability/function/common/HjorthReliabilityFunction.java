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
 * The {@code HjorthReliabilityFunction} represents the {@code
 * ReliabilityFunction} of the {@code Hjorth reliabilityFunction}
 * <p>
 * {@code R(x) = 1 - F(x) = (e^-(delta * x^2)) / (1 + beta * x)^(theta / beta)}<br />
 * with {@code beta, delta, theta > 0}.
 * <p>
 * While the parameter {@code beta} scales the reliabilityFunction, the
 * parameters {@code delta} and {@code theta} determine the {@code shape} of the
 * reliabilityFunction. This function is especially interesting since
 * bathtub-shaped failue rates can be modeled.
 * 
 * @author glass
 * 
 */
public class HjorthReliabilityFunction implements ReliabilityFunction {

	/**
	 * The {@code beta} parameter scales the {@code HjorthReliabilityFunction}.
	 */
	protected final double beta;

	/**
	 * The {@code delta} parameter shapes the {@code HjorthReliabilityFunction}.
	 */
	protected final double delta;

	/**
	 * The {@code theta} parameter shapes the {@code HjorthReliabilityFunction}.
	 */
	protected final double theta;

	/**
	 * Constructs a {@code HjorthReliabilityFunction} with a given {@code beta},
	 * {@code delta}, and {@code theta}.
	 * 
	 * @param beta
	 *            the scale value
	 * @param delta
	 *            the first shape value
	 * @param theta
	 *            the second shape value
	 */
	public HjorthReliabilityFunction(double beta, double delta, double theta) {
		this.beta = beta;
		this.delta = delta;
		this.theta = theta;
		if (!(beta > 0) || !(delta > 0) || !(theta > 0)) {
			throw new IllegalArgumentException("HjorthReliabilityFunction: Beta, Delta, and Theta should be greater 0.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		double numerator = Math.exp(-(delta * Math.pow(x, 2)) / 2);
		double base = (1 + (beta * x));
		double exponent = theta / beta;
		double denominator = Math.pow(base, exponent);
		double y = numerator / denominator;
		return y;
	}
}