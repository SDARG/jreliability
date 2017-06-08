package org.jreliability.tutorial.boiler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;

/**
 * The {@code BoilerTransformer} provides a {@code ReliabilityFunction} for each
 * {@code BoilerComponent} in the {@code Boiler}. In this implementation, each
 * component is assigned a {@code WeibulllReliabilityFunction} with a scale of
 * {@code 0.5} and a shape of {@code 2}.
 * 
 * @author glass
 * 
 */
public class BoilerTransformer implements Transformer<Object, ReliabilityFunction> {

	/**
	 * The used {@code ReliabilityFunction} for each component of the {@code
	 * Boiler}.
	 */
	Map<BoilerComponent, ReliabilityFunction> reliabilityFunctions = new HashMap<BoilerComponent, ReliabilityFunction>();

	/**
	 * Constructs a {@code BoilerTransformer} with a given {@code Boiler}.
	 * 
	 * @param boiler
	 *            the boiler
	 */
	public BoilerTransformer(Boiler boiler) {
		initialize(boiler);
	}

	/**
	 * Initializes the {@code ReliabilityFunction} for each component of the
	 * {@code Boiler}.
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

	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public ReliabilityFunction transform(Object element) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions.get(element);
		return reliabilityFunction;
	}


}
