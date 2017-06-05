package org.jreliability.function;


public class ConstantFailureFunction extends ConstantReliabilityFunction {

	public ConstantFailureFunction(double failure_probability) {
		super(failure_probability);
	}

	@Override
	public double getY(double x) {
		return failure_probability;
	}

}
