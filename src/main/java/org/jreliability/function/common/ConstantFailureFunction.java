package org.jreliability.function.common;

/**
 * The {@link ConstantFailureFunction} returns a constant failure probability for which it must hold that <br>
 * {@code 0 =< failure probability =< 1}.
 * 
 * @author glass
 *
 */
public class ConstantFailureFunction extends ConstantReliabilityFunction {

	public ConstantFailureFunction(double failureProbability) {
		super(1 - failureProbability);
	}

	@Override
	public double getY(double x) {
		return 1 - super.getY(x);
	}

}
