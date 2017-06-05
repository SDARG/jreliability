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
 * The {@code InverseFunction} determines the inverse reliability {@code
 * R^-1(x)}. It calculates a {@code y} in {@code x = R(y)} for a given {@code x}
 * and the {@code ReliabilityFunction} {@code R(x)} via a bisection approach.
 * 
 * @author glass
 * 
 */
public class InverseFunction implements Function {

	/**
	 * The {@code ReliabilityFunction} for which the inverse is to determine.
	 */
	protected final ReliabilityFunction reliabilityFunction;

	/**
	 * Constructs an {@code InverseFunction} with a given {@code
	 * ReliabilityFunction}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 */
	public InverseFunction(ReliabilityFunction reliabilityFunction) {
		this.reliabilityFunction = reliabilityFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		double epsilon = 1.0 / 100000;
		double y;
		double diff;
		double low = 0;
		double high = 2;

		// Fast calculation of the bounds
		while (reliabilityFunction.getY(high) > x) {
			low = high;
			high *= 2;
		}

		// Bisection
		do {
			y = high - ((high - low) / 2);
			double tmpX = reliabilityFunction.getY(y);
			if (tmpX < x) {
				high = y;
			} else {
				low = y;
			}
			diff = Math.abs(x - tmpX);
		} while (diff > epsilon);

		return y;
	}

}
