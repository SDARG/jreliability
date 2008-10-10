package de.cs12.reliability.function;

/**
 * The {@code ExponentialDistribution} represents the exponential distribution.
 * <p>
 * {@code f(x) = e^-(alpha * x)}.
 * 
 * @author glass
 * 
 */
public class ExponentialDistribution extends AbstractDistribution {

	/**
	 * The used failure-rate {@code lambda}.
	 */
	protected final double alpha;

	/**
	 * Constructs a {@code ExponentialDistribution} with a given {@code alpha}
	 * 
	 * @param alpha
	 *            the alpha value
	 */
	public ExponentialDistribution(double alpha) {
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		return Math.exp(-(alpha * x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Distribution#getX(double)
	 */
	@Override
	public double getX(double y) {
		double x = -(Math.log(y) / alpha);
		return x;
	}

}
