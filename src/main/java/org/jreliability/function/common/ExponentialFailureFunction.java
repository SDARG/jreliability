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

/**
 * The {@link ExponentialFailureFunction} represents the exponential behavior of
 * cumulative failure probability<br>
 * {@code F(x) = 1 - e^-(alpha * x)}<br>
 * of a failure caused with a fixed rate/probability of occurrence
 * {@code alpha > 0}.
 * 
 * @author khosravi
 */
public class ExponentialFailureFunction extends ExponentialReliabilityFunction {

	public ExponentialFailureFunction(double alpha) {
		super(alpha);
	}

	/**
	 * @param x
	 *            represents time t at which the failure probability is
	 *            acquired.
	 * @return y failure probability at time t
	 */
	@Override
	public double getY(double x) {
		double y = 1.0 - Math.exp(-(alpha * x));
		return y;
	}

}
