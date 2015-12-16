package org.jreliability.function;


public class ConstantReliabilityFunction implements ReliabilityFunction {

	protected final double failure_probability;

	public ConstantReliabilityFunction(double failure_probability) {
		this.failure_probability = failure_probability;
	}

	@Override
	public double getY(double x) {
		return 1.0 - failure_probability;
	}

}
