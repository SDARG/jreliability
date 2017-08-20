package org.jreliability.function.common;

/**
 * The {@link ExponentialFailureFunction} represents the exponential behavior of cumulative failure probability<br>
 * {@code F(x) = 1 - e^-(alpha * x)}<br>
 * of a failure caused with a fixed rate/probability of occurrence {@code alpha > 0}.
 * 
 * @author khosravi
 */
public class ExponentialFailureFunction extends ExponentialReliabilityFunction {

	public ExponentialFailureFunction(double alpha) {
		super(alpha);
	}

	/**
	 * @param x
	 *            represents time t at which the failure probability is acquired.
	 * @return y failure probability at time t
	 */
	@Override
	public double getY(double x) {
		double y = 1.0 - Math.exp(-(alpha * x));
		return y;
	}

}
