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
package org.jreliability.function.common;

import java.util.Set;

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.ReliabilityFunctionSet;

/**
 * The {@code SumReliabilityFunction} determines the sum of the specified set of {@link ReliabilityFunction}s.
 * 
 * @author glass
 *
 */
public class SumReliabilityFunction extends ReliabilityFunctionSet {

	/**
	 * Constructs a {@code SumReliabilityFunction}.
	 * 
	 */
	public SumReliabilityFunction() {
		super();
	}

	/**
	 * Constructs a {@code SumReliabilityFunction} with a given set of {@code ReliabilityFunction}s.
	 * 
	 * @param functions
	 *            the reliability functions
	 */
	public SumReliabilityFunction(Set<ReliabilityFunction> functions) {
		super(functions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		if (functions.size() == 0) {
			throw new IllegalStateException("Trying to evaluate empty SumReliabilityFunction.");
		}

		double y = 0.0;

		for (ReliabilityFunction function : functions) {
			y += function.getY(x);
		}

		return y;
	}

}
