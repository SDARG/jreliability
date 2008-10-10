package de.cs12.reliability.evaluator;

import de.cs12.reliability.common.Samples;
import de.cs12.reliability.function.Function;

/**
 * The {@code SamplingEvaluator} creates functions of the function {@code y =
 * f(x)} as pairs of {@code x} and {@code f(x) = y}.
 * 
 * @author glass
 * 
 */
public class SamplingEvaluator implements Evaluator {

	/**
	 * Constructs a {@code SamplingEvaluator}.
	 * 
	 */
	public SamplingEvaluator() {
		super();
	}

	/**
	 * Returns the functions of the {@code Function} from {@code low} to
	 * {@code high} using the given {@code step} width.
	 * 
	 * @param function
	 *            the function
	 * @param low
	 *            the low value
	 * @param high
	 *            the high value
	 * @param step
	 *            the step width
	 * @return the functions
	 */
	public Samples evaluate(Function function, double low, double high,
			double step) {
		Samples samples = new Samples();
		for (double time = low; time < high; time += step) {
			double r = function.getY(time);
			samples.put(time, r);
		}
		return samples;

	}

	/**
	 * Returns {@code number} functions of the {@code Function} from {@code 0} to
	 * an auto-calculated upper bound.
	 * 
	 * @param function
	 *            the function
	 * @param number
	 *            the number of functions
	 * @return the functions
	 */
	public Samples evaluate(Function function, int number) {
		IntegralEvaluator evaluator = new IntegralEvaluator();
		double high = evaluator.getUpperBound(function);
		double step = high / (double) number;
		return evaluate(function, 0.0, high, step);
	}

}
