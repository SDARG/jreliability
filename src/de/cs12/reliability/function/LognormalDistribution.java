package de.cs12.reliability.function;

/**
 * The {@code LognormalDistribution} represents the lognormal distribution
 * <p>
 * {@code f(x) = (1 / (rho * sqrt(2 * pi))) * e^(-0.5 * ((ln(T) - mu) / rho)^2)}.
 * 
 * @author glass
 * 
 */
public class LognormalDistribution extends AbstractDistribution {

	/**
	 * The used mean of the natural logarithms of the times-to-failure.
	 */
	protected final double mu;

	/**
	 * The used standard deviation of the natural logarithms of the
	 * times-to-failure.
	 */
	protected final double rho;

	/**
	 * Constructs a {@code LognormalDistribution} with a given {@code mu} and
	 * {@code rho}.
	 * 
	 * @param mu
	 *            the mu value
	 * @param rho
	 *            the rho value
	 */
	public LognormalDistribution(double mu, double rho) {
		this.mu = mu;
		this.rho = rho;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		double preTerm = 1 / (rho * Math.sqrt(2 * Math.PI));
		double exponentTerm = -0.5 * Math.pow(((Math.log(x) - mu) / rho), 2);
		double y = preTerm * Math.exp(exponentTerm);
		return y;
	}

}
