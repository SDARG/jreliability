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
 * The {@code InverseFunction} determines the {@code x} in {@code y = R(x)} for
 * a given {@code y} and the {@code ReliabilityFunction} {@code R(x)} via a
 * bisection approach.
 * 
 * @author glass
 * 
 */
public class InverseFunction {

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

	/**
	 * Returns an {@code x} value for a given {@code y} value in {@code y =
	 * R(x)}.
	 * 
	 * @param y
	 *            the y value
	 * @return an x value for the given y value
	 */
	public double getX(double y) {
		double epsilon = 1.0 / 10000;
		double x = 1;
		double tmpY = reliabilityFunction.getY(x);

		if (tmpY < (y - epsilon)) {
			return bisection(y, 0.0, x, epsilon);
		} else if (tmpY > (y + epsilon)) {
			return bisection(y, x, 2.0, epsilon);
		} else {
			return x;
		}
	}

	/**
	 * Performs a bisection approach to determine the {@code x} for {@code y =
	 * R(x)}.
	 * 
	 * @param y
	 *            the y value
	 * @param low
	 *            the current lower bound of the interval
	 * @param high
	 *            the current upper bound of the interval
	 * @param epsilon
	 *            the epsilon value
	 * @return x for y = R(x)
	 */
	protected double bisection(double y, double low, double high, double epsilon) {
		double x = high - ((high - low) / 2);
		double tmpY = reliabilityFunction.getY(x);

		if (tmpY < (y - epsilon)) {
			return bisection(y, low, x, epsilon);
		} else if (tmpY > (y + epsilon)) {
			return bisection(y, x, high, epsilon);
		} else {
			return x;
		}

	}

}
