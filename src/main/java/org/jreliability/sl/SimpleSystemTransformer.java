package org.jreliability.sl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;

public class SimpleSystemTransformer implements Transformer<SimpleComponent, ReliabilityFunction> {

	Map<SimpleComponent, ReliabilityFunction> reliabilityFunctions = new HashMap<>();
	
	public SimpleSystemTransformer(SimpleSystem simpleSystem) {
		initialize(simpleSystem);
	}
	
	private void initialize(SimpleSystem simpleSystem) {
		for (SimpleComponent component : simpleSystem.getComponents()) {
			ReliabilityFunction reliabilityFunction = new WeibullReliabilityFunction(0.5, 2);
			reliabilityFunctions.put(component, reliabilityFunction);
		}
	}

	@Override
	public ReliabilityFunction transform(SimpleComponent element) {
		ReliabilityFunction reliabilityFunction = reliabilityFunctions.get(element);
		return reliabilityFunction;
	}
}
