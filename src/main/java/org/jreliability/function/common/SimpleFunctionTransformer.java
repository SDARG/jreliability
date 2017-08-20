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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link SimpleFunctionTransformer} is a basic implementation of a function {@link Transformer}.
 * <p>
 * Note: This functionTransformer has to be initialized with all elements {@code T} and their corresponding
 * {@link ReliabilityFunction}s before using it.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of variable
 */
public class SimpleFunctionTransformer<T> implements Transformer<T, ReliabilityFunction> {

	/**
	 * The element {@code T} and its {@link ReliabilityFunction}.
	 */
	protected final Map<T, ReliabilityFunction> reliabilityFunctions;

	/**
	 * Constructs an empty {@link SimpleFunctionTransformer}.
	 * 
	 */
	public SimpleFunctionTransformer() {
		this(new HashMap<T, ReliabilityFunction>());
	}

	/**
	 * Constructs a {@link SimpleFunctionTransformer} with given elements {@code T} and corresponding
	 * {@link ReliabilityFunction}s.
	 * 
	 * @param reliabilityFunctions
	 *            elements and their reliability functions
	 */
	public SimpleFunctionTransformer(Map<T, ReliabilityFunction> reliabilityFunctions) {
		super();
		this.reliabilityFunctions = reliabilityFunctions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.common.Transformer#transform(java.lang.Object)
	 */
	@Override
	public ReliabilityFunction transform(T a) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions.get(a);
		return reliabilityFunction;
	}

	/**
	 * Sets the {@link ReliabilityFunction} of an element {@code T}.
	 * 
	 * @param element
	 *            the element
	 * @param reliabilityFunction
	 *            the reliability function of the element
	 */
	public void set(T element, ReliabilityFunction reliabilityFunction) {
		reliabilityFunctions.put(element, reliabilityFunction);
	}

}
