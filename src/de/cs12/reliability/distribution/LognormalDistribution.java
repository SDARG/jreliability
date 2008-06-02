package de.cs12.reliability.distribution;

import de.cs12.reliability.Distribution;

/**
 * The {@code LognormalDistribution} represents the lognormal distribution
 * <p>
 * {@code f(x) = (1 / (rho * sqrt(2 * pi))) * e^(-0.5 * ((ln(T) - mu) / rho)^2)}.
 * 
 * @author glass
 * 
 */
public class LognormalDistribution implements Distribution {

	protected final double mu;
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
	 * @see de.cs12.reliability.bdd.reliability.Distribution#getY(double)
	 */
	public double getY(double x) {
		double preTerm = 1 / (rho * Math.sqrt(2 * Math.PI));
		double exponentTerm = -0.5 * Math.pow(((Math.log(x) - mu) / rho), 2);
		double y = preTerm * Math.exp(exponentTerm);
		return y;
	}

}
