package de.cs12.reliability.distribution;

import de.cs12.bdd.reliability.Distribution;

/**
 * The {@code ExponentialDistribution} represents the exponential distribution
 * <p>
 * {@code f(x) = e^-(alpha * x)}.
 * 
 * @author glass
 * 
 */
public class ExponentialDistribution implements Distribution {

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
	 * @see de.cs12.reliability.bdd.reliability.Distribution#getY(double)
	 */
	public double getY(double x) {
		return Math.exp(-(alpha * x));
	}

}
