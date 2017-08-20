package org.jreliability.tutorial.boiler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;

/**
 * The {@link BoilerTransformer} provides a {@link ReliabilityFunction} for each {@link BoilerComponent} in the
 * {@link Boiler}. In this implementation, each component is assigned a {@link WeibullReliabilityFunction} with a scale
 * of {@code 0.5} and a shape of {@code 2}.
 * 
 * @author glass
 * 
 */
public class BoilerTransformer implements Transformer<BoilerComponent, ReliabilityFunction> {

	/**
	 * The used {@link ReliabilityFunction} for each component of the {@link Boiler}.
	 */
	Map<BoilerComponent, ReliabilityFunction> reliabilityFunctions = new HashMap<>();

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
	 * Initializes the {@link ReliabilityFunction} for each component of the {@link Boiler}.
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
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public ReliabilityFunction transform(BoilerComponent element) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions.get(element);
		return reliabilityFunction;
	}

}
