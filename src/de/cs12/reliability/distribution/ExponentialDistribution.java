package de.cs12.reliability.distribution;

/**
 * The {@code ExponentialDistribution} represents the exponential distribution.
 * <p>
 * {@code f(x) = e^-(alpha * x)}.
 * 
 * @author glass
 * 
 */
public class ExponentialDistribution implements Inverse {

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
	 * @see
	 * de.cs12.reliability.evaluator.bdd.reliability.Distribution#getY(double)
	 */
	public double getY(double x) {
		return Math.exp(-(alpha * x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.distribution.Inverse#getX(double)
	 */
	@Override
	public double getX(double y) {
		double x = -(Math.log(y) / alpha);
		return x;
	}

}
