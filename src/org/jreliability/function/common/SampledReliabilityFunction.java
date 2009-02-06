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
package org.jreliability.function.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code SampledReliabilityFunction}
 * 
 * @author glass
 * 
 */
public class SampledReliabilityFunction implements ReliabilityFunction {

	protected boolean initialized = false;
	protected Map<Double, Double> xToY = new HashMap<Double, Double>();
	protected SortedSet<Double> samples = new TreeSet<Double>();

	public SampledReliabilityFunction(List<Double> samples) {
		this.samples.addAll(samples);
		double sum = 0;
		for(double sample : samples) {
			sum += sample;
		}
		System.out.println((sum) / samples.size());
		initialize();
	}

	public double getY(double x) {
		SortedSet<Double> headSet = samples.headSet(x);
		SortedSet<Double> tailSet = samples.tailSet(x);
		if (headSet.isEmpty()) {
			return 1.0;
		}
		double headX = headSet.last();
		double headY = xToY.get(headX);
		if (!tailSet.isEmpty()) {
			double tailX = tailSet.first();
			double tailY = xToY.get(tailX);
			
			double base = tailY;

			headY = headY - base;

			double y = (headY * (tailX - x)) / (tailX - headX);
			y += base;
			return y;
		} else {
			return 0.0;
		}
	}

	protected void initialize() {
		samples.add(0.0);
		samples.add(samples.last() * 8);

		double size = samples.size();

		double i = 0;
		for (double x : samples) {
			double y = 1 - (i / (size - 1.0));
			xToY.put(x, y);
			i++;
		}
	}

}
