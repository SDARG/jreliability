package org.jreliability.tutorial.boiler;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.function.FunctionTransformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;

/**
 * The {@code BoilerTransformer} provides a {@code ReliabilityFunction} for each
 * {@code BoilerElement} in the {@code Boiler}. In this implementation, each
 * element is assigned an {@code WeibulllReliabilityFunction} with a scale of
 * {@code 0.5} and a shape of {@code 2}.
 * 
 * @author glass
 * 
 */
public class BoilerTransformer implements FunctionTransformer<BoilerElement> {

	/**
	 * The used {@code ReliabilityFunction} for each element of the {@code
	 * Boiler}.
	 */
	Map<BoilerElement, ReliabilityFunction> reliabilityFunctions = new HashMap<BoilerElement, ReliabilityFunction>();

	public BoilerTransformer(Boiler boiler) {
		initialize(boiler);
	}

	/**
	 * Initializes the {@code ReliabilityFunction} for each element of the
	 * {@code Boiler}.
	 * 
	 * @param boiler
	 *            the boiler
	 */
	private void initialize(Boiler boiler) {
		for (BoilerElement element : boiler.getElements()) {
			ReliabilityFunction reliabilityFunction = new WeibullReliabilityFunction(
					0.5, 2);
			reliabilityFunctions.put(element, reliabilityFunction);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.common.Transformer#transform(java.lang.Object)
	 */
	@Override
	public ReliabilityFunction transform(BoilerElement element) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions
				.get(element);
		return reliabilityFunction;
	}

}
