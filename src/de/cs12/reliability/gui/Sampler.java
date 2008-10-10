package de.cs12.reliability.gui;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import de.cs12.reliability.common.Samples;
import de.cs12.reliability.function.Function;

/**
 * The {@code Sampler} is used to generate the {@code Samples} of a set of
 * {@code Functions} under a given {@code Aspect}.
 * 
 * @author glass
 * 
 */
public class Sampler {

	/**
	 * Constructs a {@code Sampler}.
	 * 
	 */
	public Sampler() {
		super();
	}

	/**
	 * Returns the {@code Samples} (with a {@code number} of points each) of a
	 * set of {@code Functions} under a given {@code Aspect}
	 * 
	 * @param functions
	 *            the functions
	 * @param aspect
	 *            the aspect
	 * @param number
	 *            the number of points per samples
	 * @return the samples of a set of functions under a given aspect
	 */
	public SortedMap<String, Samples> getSamples(
			SortedMap<String, Function> functions, Aspect aspect, int number) {
		Double lower = null;
		Double upper = null;
		for (Entry<String, Function> entry : functions.entrySet()) {
			Function function = entry.getValue();
			double tmpLow = aspect.getLower(function);
			double tmpUp = aspect.getUpper(function);
			if (lower == null || lower > tmpLow) {
				lower = tmpLow;
			}
			if (upper == null || upper < tmpUp) {
				upper = tmpUp;
			}
		}

		SortedMap<String, Samples> samples = new TreeMap<String, Samples>();
		for (Entry<String, Function> entry : functions.entrySet()) {
			Function function = entry.getValue();
			Samples sample = getSamples(function, aspect, lower, upper, number);
			samples.put(entry.getKey(), sample);
		}

		return samples;
	}

	/**
	 * Returns {@code number} {@code Samples} of a {@code Function} under a
	 * given {@code Aspect}, ranging from the {@code lower} to the
	 * {@code upper} bound.
	 * 
	 * @param function
	 *            the function
	 * @param aspect
	 *            the aspect
	 * @param lower
	 *            the lower bound
	 * @param upper
	 *            the upper bound
	 * @param number
	 *            the number of points per samples
	 * @return the samples of a function under an aspect ranging from lower to
	 *         upper
	 */
	protected Samples getSamples(Function function, Aspect aspect,
			double lower, double upper, int number) {
		Samples samples = new Samples();

		double step = (upper - lower) / number;
		for (double x = lower; x < upper; x = x + step) {
			double y = aspect.getY(x, function);
			samples.put(x, y);
		}

		return samples;
	}

}
