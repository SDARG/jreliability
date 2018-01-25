/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.function;

/**
 * The {@link InverseFunction} determines the inverse reliability {@code
 * R^-1(x)}. It calculates a {@code y} in {@code x = R(y)} for a given {@code x}
 * and the {@link ReliabilityFunction} {@code R(x)} via a bisection approach.
 * 
 * @author glass
 * 
 */
public class InverseFunction implements Function {

	/**
	 * The {@link ReliabilityFunction} for which the inverse is to determine.
	 */
	protected final ReliabilityFunction reliabilityFunction;

	/**
	 * The allowed error {@code epsilon} for embedded bisection method.
	 */
	protected final double epsilon;

	/**
	 * Constructs an {@link InverseFunction} with a given
	 * {@link ReliabilityFunction} and an error {@code epsilon} for the embedded
	 * bisection method.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * 
	 * @param epsilon
	 *            the error of the bisection method
	 */
	public InverseFunction(ReliabilityFunction reliabilityFunction, double epsilon) {
		this.reliabilityFunction = reliabilityFunction;
		this.epsilon = epsilon;
	}

	/**
	 * Constructs an {@link InverseFunction} with a given
	 * {@link ReliabilityFunction} and an acceptable error of 1.0E-5.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 */
	public InverseFunction(ReliabilityFunction reliabilityFunction) {
		this(reliabilityFunction, 1.0E-7);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
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
		double lastX = 0;
		do {
			y = high - ((high - low) / 2);
			double tmpX = reliabilityFunction.getY(y);
			if (tmpX < x) {
				high = y;
			} else {
				low = y;
			}
			if (lastX == tmpX) { // no progress by bisection possible
				diff = 0;
			} else {
				diff = Math.abs(x - tmpX);
			}
			lastX = tmpX;
		} while (diff > epsilon);

		return y;
	}

}
