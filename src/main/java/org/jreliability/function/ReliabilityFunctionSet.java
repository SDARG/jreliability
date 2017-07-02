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
 * The {@code ReliabilityFunctionSet} is the basic class for functions defined
 * over a set of {@link ReliabilityFunction}s.
 * 
 * @author glass
 *
 */
public abstract class ReliabilityFunctionSet implements ReliabilityFunction {

	/**
	 * The set of {@code ReliabilityFunctions}.
	 */
	protected Set<ReliabilityFunction> functions;

	/**
	 * Constructs a {@code ReliabilityFunctionSet}.
	 */
	public ReliabilityFunctionSet() {
		this(new HashSet<ReliabilityFunction>());
	}

	/**
	 * Constructs a {@code ParallelReliabilityFunction} with a given set of
	 * {@code ReliabilityFunctions}.
	 * 
	 * @param functions
	 *            the reliability functions
	 */
	public ReliabilityFunctionSet(Set<ReliabilityFunction> functions) {
		this.functions = functions;
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
