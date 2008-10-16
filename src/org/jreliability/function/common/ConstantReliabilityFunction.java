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
 * The {@code ConstantReliabilityFunction} represents a {@code ReliabilityFunction} with a constant
 * value of
 * <p>
 * {@code R(x) = c}.
 * 
 * @author glass
 * 
 */
public class ConstantReliabilityFunction implements ReliabilityFunction {

	/**
	 * The used constant value.
	 */
	protected final double c;

	/**
	 * Constructs a {@code ConstantReliabilityFunction} with a constant value {@code c}.
	 * 
	 * @param c
	 *            the constant value
	 */
	public ConstantReliabilityFunction(double c) {
		this.c = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		return c;
	}

}
