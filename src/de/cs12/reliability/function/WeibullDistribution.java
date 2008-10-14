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

/**
 * The {@code WeibullDistribution} represents the Weibull distribution with
 * <p>
 * {@code f(x) = e^-(alpha * (x^beta))}.
 * 
 * @author glass
 * 
 */
public class WeibullDistribution extends AbstractDistribution {

	/**
	 * The used failure-rate {@code lambda}.
	 */
	protected final double alpha;

	/**
	 * The used shape of the {@code WeibullDistribution}.
	 */
	protected final double beta;

	/**
	 * Constructs a {@code WeibullDistribution} with a given {@code alpha} and
	 * {code beta}.
	 * 
	 * @param alpha
	 *            the alpha value
	 * @param beta
	 *            the beta value
	 */
	public WeibullDistribution(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		return Math.exp(-(alpha * Math.pow(x, beta)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.AbstractDistribution#getX(double)
	 */
	@Override
	public double getX(double y) {
		double exponential = -(Math.log(y) / alpha);
		double x = Math.pow(exponential, (1 / beta));
		return x;
	}
}
