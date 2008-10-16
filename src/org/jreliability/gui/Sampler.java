/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.gui;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.jreliability.common.Samples;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code Sampler} is used to generate the {@code Samples} of a set of
 * {@code ReliabilityFunction} under a given {@code Aspect}.
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
	 * set of {@code ReliabilityFunction} under a given {@code Aspect}
	 * 
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 * @param aspect
	 *            the aspect
	 * @param number
	 *            the number of points per samples
	 * @return the samples of a set of reliabilityFunctions under a given aspect
	 */
	public SortedMap<String, Samples> getSamples(
			SortedMap<String, ReliabilityFunction> reliabilityFunctions,
			Aspect aspect, int number) {
		Double lower = null;
		Double upper = null;
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions
				.entrySet()) {
			ReliabilityFunction reliabilityFunction = entry.getValue();
			double tmpLow = aspect.getLower(reliabilityFunction);
			double tmpUp = aspect.getUpper(reliabilityFunction);
			if (lower == null || lower > tmpLow) {
				lower = tmpLow;
			}
			if (upper == null || upper < tmpUp) {
				upper = tmpUp;
			}
		}

		SortedMap<String, Samples> samples = new TreeMap<String, Samples>();
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions
				.entrySet()) {
			ReliabilityFunction reliabilityFunction = entry.getValue();
			Samples sample = getSamples(reliabilityFunction, aspect, lower,
					upper, number);
			samples.put(entry.getKey(), sample);
		}

		return samples;
	}

	/**
	 * Returns {@code number} {@code Samples} of a {@code ReliabilityFunction}
	 * under a given {@code Aspect}, ranging from the {@code lower} to the
	 * {@code upper} bound.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @param aspect
	 *            the aspect
	 * @param lower
	 *            the lower bound
	 * @param upper
	 *            the upper bound
	 * @param number
	 *            the number of points per samples
	 * @return the samples of a reliabilityFunction under an aspect ranging from
	 *         lower to upper
	 */
	protected Samples getSamples(ReliabilityFunction reliabilityFunction,
			Aspect aspect, double lower, double upper, int number) {
		Samples samples = new Samples();

		double step = (upper - lower) / number;
		for (double x = lower; x < upper; x = x + step) {
			Double y = aspect.getY(x, reliabilityFunction);
			if (y == null) {
				break;
			}
			samples.put(x, y);
		}

		return samples;
	}

}
