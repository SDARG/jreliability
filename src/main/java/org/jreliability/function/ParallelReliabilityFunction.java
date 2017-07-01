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

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code ParallelReliabilityFunction} corresponds to a
 * {@code parallel-structure} of elements in a system as known from
 * {@code Serial-Parallel systems}. Basically, the
 * {@code ParallelReliabilityFunction} holds a set of
 * {@code ReliabilityFunctions} and multiplies their {@code (1-y)}-values
 * and calculates the counter probability to derive the {@code y}-value of the
 * whole {@code parallel-structure}.
 * 
 * @author glass
 * 
 */
public class ParallelReliabilityFunction implements ReliabilityFunction {

	/**
	 * The set of {@code ReliabilityFunctions}.
	 */
	protected Set<ReliabilityFunction> functions;

	/**
	 * Constructs a {@code  ParallelReliabilityFunction}.
	 * 
	 */
	public ParallelReliabilityFunction() {
		this(new HashSet<ReliabilityFunction>());
	}

	/**
	 * Constructs a {@code ParallelReliabilityFunction} with a given set of
	 * {@code ReliabilityFunctions}.
	 * 
	 * @param functions
	 *            the reliability functions
	 */
	public ParallelReliabilityFunction(Set<ReliabilityFunction> functions) {
		super();
		this.functions = functions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		if (functions.size() == 0) {
			throw new RuntimeException("Trying to evaluate empty ParallellReliabilityFunction.");
		}
		double y = 1;
		for (ReliabilityFunction function : functions) {
			System.out.println("GetY: "+function.getY(x));
			y *= (1 - function.getY(x));
			System.out.println(y);
		}
		System.out.println(y);
		y = (1 - y);
		return y;

	}

	/**
	 * Returns the set of {@code ReliabilityFunctions}.
	 * 
	 * @return the reliability functions
	 */
	public Set<ReliabilityFunction> getFunctions() {
		return functions;
	}

	/**
	 * Adds a {@code ReliabilityFunction}.
	 * 
	 * @param function
	 *            the function to set
	 */
	public void add(ReliabilityFunction function) {
		functions.add(function);
	}

}