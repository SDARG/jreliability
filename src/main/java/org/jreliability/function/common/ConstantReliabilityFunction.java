package org.jreliability.function.common;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link ConstantReliabilityFunction} returns a constant success probability for which it must hold that <br>
 * {@code 0 =< failure probability =< 1}.
 * 
 * @author glass
 *
 */
public class ConstantReliabilityFunction implements ReliabilityFunction {

	protected final double successProbability;

	public ConstantReliabilityFunction(double successProbability) {
		this.successProbability = successProbability;
		if ((successProbability < 0) | (successProbability > 1)) {
			throw new IllegalArgumentException("ExponentialReliabilityFunction: Alpha should be greater 0.");
		}
	}

	@Override
	public double getY(double x) {
		return successProbability;
	}

}
