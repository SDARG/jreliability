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
		double epsilon = 1.0 / 10000;
		double y = 1;
		double tmpY = reliabilityFunction.getY(y);

		if (tmpY < (x - epsilon)) {
			return bisection(x, 0.0, y, epsilon);
		} else if (tmpY > (x + epsilon)) {
			return bisection(x, y, 2.0, epsilon);
		} else {
			return y;
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
