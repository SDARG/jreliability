package de.cs12.reliability.gui;

import de.cs12.reliability.common.Samples;
import de.cs12.reliability.evaluator.SamplingEvaluator;
import de.cs12.reliability.function.Function;

/**
 * The {@code DistributionAspect} represents the distribution of a
 * {@code Function}.
 * 
 * @author glass
 * 
 */
public class DistributionAspect extends AbstractAspect {

	/**
	 * Constructs a {@code DistributionAspect}.
	 * 
	 */
	public DistributionAspect() {
		super("Distribution", "time t", "distribution function R(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getSamples(de.cs12.reliability.function.Function)
	 */
	@Override
	public Samples getSamples(Function function) {
		SamplingEvaluator evaluator = new SamplingEvaluator();
		Samples samples = evaluator.evaluate(function, numberOfSamples);
		return samples;

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
		Samples samples = evaluator.evaluate(function, low, high, step);
		return samples;
	}

}
