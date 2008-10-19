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
package org.jreliability.evaluator;

import org.jreliability.function.InverseFunction;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code InverseEvaluator} calculates the {@code x} value in {@code y =
 * R(x)} for a given {@code y} of the {@code ReliabilityFunction} {@code R(t)}.
 * This is typically used to derive measures like, e.g., the Mission Time (MT).
 * 
 * @author glass
 */
public class InverseEvaluator implements Evaluator {

	/**
	 * Constructs an {@code InverseEvaluator}.
	 * 
	 */
	public InverseEvaluator() {
		super();
	}

	/**
	 * Returns the {@code x} value for a given {@code y} value in {@code y =
	 * R(x)} of a given {@code ReliabilityFunction}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @param y
	 *            the y value
	 * @return the x value for a y value and a given reliabilityFunction
	 */
	public double evaluate(ReliabilityFunction reliabilityFunction, double y) {
		InverseFunction inverse = new InverseFunction(reliabilityFunction);
		double x = inverse.getY(y);
		return x;
	}

}
