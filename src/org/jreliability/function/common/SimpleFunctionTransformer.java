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

import java.util.HashMap;
import java.util.Map;

import org.jreliability.function.FunctionTransformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code SimpleFunctionTransformer} is a basic implementation of a {@code
 * FunctionTransformer}. Note: This transformer has to be initialized with all
 * elements {@code T} and their corresponding {@code ReliabilityFunctions}
 * before using it.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of variable
 */
public class SimpleFunctionTransformer<T> implements FunctionTransformer<T> {

	/**
	 * The element {@code T} and its {@code ReliabilityFunction}.
	 */
	protected Map<T, ReliabilityFunction> reliabilityFunctions = new HashMap<T, ReliabilityFunction>();

	/**
	 * Constructs a {@code SimpleFunctionTransformer} with given elements
	 * {@code T} and corresponding {@code ReliabilityFunctions}.
	 * 
	 * @param reliabilityFunctions
	 *            elements and their reliability functions
	 */
	public SimpleFunctionTransformer(
			Map<T, ReliabilityFunction> reliabilityFunctions) {
		super();
		this.reliabilityFunctions = reliabilityFunctions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.common.Transformer#transform(java.lang.Object)
	 */
	public ReliabilityFunction transform(T a) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions.get(a);
		return reliabilityFunction;
	}

	/**
	 * Sets the {@code ReliabilityFunction} of an element {@code T}.
	 * 
	 * @param element
	 *            the element
	 * @param reliabilityFunction
	 *            the reliabilty function of the element
	 */
	public void set(T element, ReliabilityFunction reliabilityFunction) {
		reliabilityFunctions.put(element, reliabilityFunction);
	}

}
