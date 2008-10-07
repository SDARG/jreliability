package de.cs12.reliability.evaluator;

import java.util.Map;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.common.Sample;
import de.cs12.reliability.common.Samples;
import de.cs12.reliability.distribution.Distribution;

/**
 * The {@code SamplingEvaluator} creates samples of the function {@code y =
 * f(x)} as pairs of {@code x} and {@code f(x) = y}.
 * 
 * @author glass
 * @param <T>
 *            the type of the variables
 * 
 */
public class SamplingEvaluator<T> extends AbstractEvaluator<T> {

	/**
	 * Constructs a {@code SamplingEvaluator} with a {@code BDD}.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	public SamplingEvaluator(BDD<T> bdd) {
		super(bdd);
	}

	/**
	 * Returns the samples of the {@code BDD} from {@code low} to {@code high}
	 * using the given {@code step} width.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @param low
	 *            the low value
	 * @param high
	 *            the high value
	 * @param step
	 *            the step width
	 * @return the samples
	 */
	public Samples getValues(Map<T, Distribution> distributions, double low,
			double high, double step) {
		Samples samples = new Samples();
		double deltaT = 1.0 / 10000.0;
		for (double time = low; time < high; time += step) {
			double r = calculateTop(distributions, time);
			double nextR = calculateTop(distributions, time + deltaT);
			double f = (r - nextR) / deltaT;
			double lambda = f / r;
			Sample sample = new Sample(time, r, f, lambda);
			samples.add(sample);
		}
		return samples;

	}

	/**
	 * Returns {@code number} samples of the {@code BDD} from {@code 0} to an
	 * auto-calculated upper bound.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @param number
	 *            the number of samples
	 * @return the samples
	 */
	public Samples getValues(Map<T, Distribution> distributions, int number) {
		IntegralEvaluator<T> evaluator = new IntegralEvaluator<T>(bdd);
		double high = evaluator.getUpperBound(distributions);
		double step = high / (double) number;
		return getValues(distributions, 0.0, high, step);
	}

}
