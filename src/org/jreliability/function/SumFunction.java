package org.jreliability.function;

import java.util.Set;

import org.jreliability.function.ReliabilityFunction;

public class SumFunction implements ReliabilityFunction {

	private Set<ReliabilityFunction> functions;

	public SumFunction(Set<ReliabilityFunction> functions) {
		this.functions = functions;
	}

	public Set<ReliabilityFunction> getFunctions() {
		return functions;
	}

	@Override
	public double getY(double x) {
		double y = 0.0;

		for (ReliabilityFunction function : functions) {
			y += function.getY(x);
		}

		return y;
	}

}
