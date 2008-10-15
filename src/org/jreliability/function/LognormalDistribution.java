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
package org.jreliability.function;

/**
 * The {@code LognormalDistribution} represents the lognormal distribution
 * <p>
 * {@code f(x) = (1 / (rho * sqrt(2 * pi))) * e^(-0.5 * ((ln(T) - mu) / rho)^2)}.
 * 
 * @author glass
 * 
 */
public class LognormalDistribution extends AbstractDistribution {

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
	 * Constructs a {@code LognormalDistribution} with a given {@code mu} and
	 * {@code rho}.
	 * 
	 * @param mu
	 *            the mu value
	 * @param rho
	 *            the rho value
	 */
	public LognormalDistribution(double mu, double rho) {
		this.mu = mu;
		this.rho = rho;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		double preTerm = 1 / (rho * Math.sqrt(2 * Math.PI));
		double exponentTerm = -0.5 * Math.pow(((Math.log(x) - mu) / rho), 2);
		double y = preTerm * Math.exp(exponentTerm);
		return y;
	}

}
