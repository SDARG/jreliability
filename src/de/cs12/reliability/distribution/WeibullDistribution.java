package de.cs12.reliability.distribution;

/**
 * The {@code WeibullDistribution} represents the Weibull distribution with
 * <p>
 * {@code f(x) = e^-(alpha * (x^beta))}.
 * 
 * @author glass
 * 
 */
public class WeibullDistribution implements Distribution {

	/**
	 * The used failure-rate {@code lambda}.
	 */
	protected final double alpha;

	/**
	 * The used shape of the {@code WeibullDistribution}.
	 */
	protected final double beta;

	/**
	 * Constructs a {@code WeibullDistribution} with a given {@code alpha} and
	 * {code beta}.
	 * 
	 * @param alpha
	 *            the alpha value
	 * @param beta
	 *            the beta value
	 */
	public WeibullDistribution(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.evaluator.bdd.reliability.Distribution#getY(double)
	 */
	public double getY(double x) {
		return Math.exp(-(alpha * Math.pow(x, beta)));
	}
}
