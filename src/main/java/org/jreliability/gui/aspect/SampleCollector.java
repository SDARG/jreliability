/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.gui.aspect;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jreliability.common.Samples;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link SampleCollector} is used to generate the {@link Samples} of a set
 * of {@link ReliabilityFunction} under a given {@link Aspect}.
 * 
 * @author glass
 * 
 */
public class SampleCollector {

	/**
	 * Returns the {@link Samples} (with a {@code number} of points each) of a
	 * set of {@link ReliabilityFunction}s under a given {@link Aspect}.
	 * 
	 * @param reliabilityFunctions
	 *            the reliabilityFunctions
	 * @param aspect
	 *            the aspect
	 * @param number
	 *            the number of points per samples
	 * @return the samples of a set of reliabilityFunctions under a given aspect
	 */
	public Map<String, Samples> getSamples(Map<String, ReliabilityFunction> reliabilityFunctions, Aspect aspect,
			int number) {
		Double lower = null;
		Double upper = null;
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions.entrySet()) {
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

		SortedMap<String, Samples> samples = new TreeMap<>();
		for (Entry<String, ReliabilityFunction> entry : reliabilityFunctions.entrySet()) {
			ReliabilityFunction reliabilityFunction = entry.getValue();
			Samples sample = getSamples(reliabilityFunction, aspect, lower, upper, number);
			samples.put(entry.getKey(), sample);
		}

		return samples;
	}

	/**
	 * Returns {@code number} {@link Samples} of a {@link ReliabilityFunction}
	 * under a given {@link Aspect}, ranging from the {@code lower} to the
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
	protected Samples getSamples(ReliabilityFunction reliabilityFunction, Aspect aspect, double lower, double upper,
			int number) {
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
