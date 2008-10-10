package de.cs12.reliability.gui;

import java.util.Map.Entry;

import de.cs12.reliability.common.Samples;
import de.cs12.reliability.evaluator.SamplingEvaluator;
import de.cs12.reliability.function.Function;

/**
 * The {@code FailureRateAspect}
 * 
 * @author glass
 * 
 */
public class FailureRateAspect extends AbstractAspect {

	/**
	 * Constructs a {@code FailureRateAspect}.
	 */
	public FailureRateAspect() {
		super("Failure Rate", "time t", "failure rate lambda(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getSamples(de.cs12.reliability.function.Function)
	 */
	@Override
	public Samples getSamples(Function function) {
		SamplingEvaluator evaluator = new SamplingEvaluator();
		Samples distributionSamples = evaluator.evaluate(function,
				numberOfSamples);
		Samples densitySamples = determineDensity(function, distributionSamples);
		return determineFailureRate(function, distributionSamples,
				densitySamples);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getSamples(de.cs12.reliability.function.Function,
	 *      double, double, double)
	 */
	@Override
	public Samples getSamples(Function function, double low, double high,
			double step) {
		SamplingEvaluator evaluator = new SamplingEvaluator();
		Samples distributionSamples = evaluator.evaluate(function, low, high,
				step);
		Samples densitySamples = determineDensity(function, distributionSamples);
		return determineFailureRate(function, distributionSamples,
				densitySamples);
	}

	/**
	 * Returns the {@code Samples} for the {@code FailureRateAspect} given a
	 * {@code Function} and the functions for its distribution and density.
	 * 
	 * @param function
	 *            the function
	 * @param distributionSamples
	 *            the functions of the distribution of the function
	 * @param densitySamples
	 *            the functions of the density of the function
	 * @return the functions of the failure rate aspect
	 */
	protected Samples determineFailureRate(Function function,
			Samples distributionSamples, Samples densitySamples) {

		Samples samples = new Samples();

		for (Entry<Double, Double> entry : distributionSamples.entrySet()) {
			double x = entry.getKey();
			double distribution = entry.getValue();
			double density = densitySamples.get(x);

			double lambda = density / distribution;
			samples.put(x, lambda);
		}

		return samples;
	}

	/**
	 * Returns the {@code Samples} for the {@code DensityAspect} given a
	 * {@code Function} and the functions for its distribution.
	 * 
	 * @param function
	 *            the function
	 * @param distributionSamples
	 *            the functions for the distribution of the function
	 * @return the functions for the density aspect
	 */
	protected Samples determineDensity(Function function,
			Samples distributionSamples) {
		double deltaT = 1.0 / 10000000.0;
		Samples samples = new Samples();

		for (Entry<Double, Double> entry : distributionSamples.entrySet()) {
			double x = entry.getKey();
			double y = entry.getValue();
			double yPrime = function.getY(x + deltaT);

			double density = (y - yPrime) / deltaT;
			samples.put(x, density);
		}

		return samples;

	}

}
