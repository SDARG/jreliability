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
 * The {@code ExponentialDistribution} represents the exponential distribution.
 * <p>
 * {@code f(x) = e^-(alpha * x)}.
 * 
 * @author glass
 * 
 */
public class ExponentialDistribution extends AbstractDistribution {

	/**
	 * The used failure-rate {@code lambda}.
	 */
	protected final double alpha;

	/**
	 * Constructs a {@code ExponentialDistribution} with a given {@code alpha}
	 * 
	 * @param alpha
	 *            the alpha value
	 */
	public ExponentialDistribution(double alpha) {
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		return Math.exp(-(alpha * x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Distribution#getX(double)
	 */
	@Override
	public double getX(double y) {
		double x = -(Math.log(y) / alpha);
		return x;
	}

}
