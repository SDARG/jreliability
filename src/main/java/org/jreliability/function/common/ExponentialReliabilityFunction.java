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
 * The {@link ExponentialReliabilityFunction} represents the exponential
 * {@link ReliabilityFunction}<br>
 * {@code R(x) = 1 - F(x) = e^-(alpha * x)}<br>
 * with {@code alpha > 0}.
 * <p>
 * Typical for this {@link ReliabilityFunction} is the constant failure-rate
 * {@code lambda} that equals the parameter {@code alpha}.
 * 
 * @author glass
 * 
 */
public class ExponentialReliabilityFunction implements ReliabilityFunction {

	/**
	 * The parameter {@code alpha} corresponds to the failure-rate
	 * {@code lambda}.
	 */
	protected final double alpha;

	/**
	 * Constructs an {@link ExponentialReliabilityFunction} with a given
	 * {@code alpha}.
	 * 
	 * @param alpha
	 *            the alpha value
	 */
	public ExponentialReliabilityFunction(double alpha) {
		this.alpha = alpha;
		if (alpha <= 0.0) {
			throw new IllegalArgumentException("ExponentialReliabilityFunction: Alpha should be greater 0.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		double y = Math.exp(-(alpha * x));
		return y;
	}

	/**
	 * The {@code alpha} parameter.
	 * 
	 * @return the alpha value
	 */
	public double getAlpha() {
		return alpha;
	}

}
