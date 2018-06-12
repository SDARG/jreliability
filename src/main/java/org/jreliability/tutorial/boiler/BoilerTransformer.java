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
package org.jreliability.tutorial.boiler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;

/**
 * The {@link BoilerTransformer} provides a {@link ReliabilityFunction} for each
 * {@link BoilerComponent} in the {@link Boiler}. In this implementation, each
 * component is assigned a {@link WeibullReliabilityFunction} with a scale of
 * {@code 0.5} and a shape of {@code 2}.
 * 
 * @author glass
 * 
 */
public class BoilerTransformer implements Transformer<BoilerComponent, ReliabilityFunction> {

	/**
	 * The used {@link ReliabilityFunction} for each component of the
	 * {@link Boiler}.
	 */
	protected Map<BoilerComponent, ReliabilityFunction> reliabilityFunctions = new HashMap<>();

	/**
	 * Constructs a {@link BoilerTransformer} with a given {@link Boiler}.
	 * 
	 * @param boiler
	 *            the boiler
	 */
	public BoilerTransformer(Boiler boiler) {
		initialize(boiler);
	}

	/**
	 * Initializes the {@link ReliabilityFunction} for each component of the
	 * {@link Boiler}.
	 * 
	 * @param boiler
	 *            the boiler
	 */
	private void initialize(Boiler boiler) {
		for (BoilerComponent component : boiler.getComponents()) {
			ReliabilityFunction reliabilityFunction = new WeibullReliabilityFunction(0.5, 2);
			reliabilityFunctions.put(component, reliabilityFunction);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public ReliabilityFunction transform(BoilerComponent element) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions.get(element);
		return reliabilityFunction;
	}

}
