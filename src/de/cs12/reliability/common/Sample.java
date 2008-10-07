package de.cs12.reliability.common;

/**
 * The {@code Sample} represents a single sample, typically derived by the
 * {@code SamplingEvaluator}. It consists of a time value and the values for the
 * distribution function, the density function and the failure rate.
 * 
 * @author glass
 * 
 */
public class Sample implements Comparable<Sample> {

	protected final double time, distribution, density, lambda;

	/**
	 * Constructs a {@code Sample} with given values for the {@code time},
	 * {@code distribution}, {@code density} and the {@code failure rate}.
	 * 
	 * @param time
	 *            the time value
	 * @param distribution
	 *            the distribution value
	 * @param density
	 *            the density value
	 * @param lambda
	 *            the failure rate
	 */
	public Sample(double time, double distribution, double density,
			double lambda) {
		this.time = time;
		this.distribution = distribution;
		this.density = density;
		this.lambda = lambda;
	}

	/**
	 * Returns the time value.
	 * 
	 * @return the time value
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Returns the distribution value.
	 * 
	 * @return the distribution
	 */
	public double getDistribution() {
		return distribution;
	}

	/**
	 * Returns the density value.
	 * 
	 * @return the density
	 */
	public double getDensity() {
		return density;
	}

	/**
	 * Returns the failure rate.
	 * 
	 * @return the lambda
	 */
	public double getLambda() {
		return lambda;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Sample arg0) {
		Double thisSample = this.getTime();
		Double thatSample = arg0.getTime();
		return thisSample.compareTo(thatSample);
	}

}
