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

package org.jreliability.function;

import java.util.HashSet;
import java.util.Set;

import org.jreliability.function.common.ParallelReliabilityFunction;

/**
 * The {@link ReliabilityFunctionSet} is the basic class for functions defined
 * over a set of {@link ReliabilityFunction}s.
 * 
 * @author glass
 *
 */
public abstract class ReliabilityFunctionSet implements ReliabilityFunction {

	/**
	 * The set of {@link ReliabilityFunction}s.
	 */
	protected Set<ReliabilityFunction> functions;

	/**
	 * Constructs a {@link ReliabilityFunctionSet}.
	 */
	public ReliabilityFunctionSet() {
		this(new HashSet<ReliabilityFunction>());
	}

	/**
	 * Constructs a {@link ParallelReliabilityFunction} with a given set of
	 * {@link ReliabilityFunction}s.
	 * 
	 * @param functions
	 *            the reliability functions
	 */
	public ReliabilityFunctionSet(Set<ReliabilityFunction> functions) {
		this.functions = functions;
	}

	/**
	 * Returns the set of {@link ReliabilityFunction}s.
	 * 
	 * @return the reliability functions
	 */
	public Set<ReliabilityFunction> getFunctions() {
		return functions;
	}

	/**
	 * Adds a {@link ReliabilityFunction}.
	 * 
	 * @param function
	 *            the function to set
	 */
	public void add(ReliabilityFunction function) {
		functions.add(function);
	}

}
