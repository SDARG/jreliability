/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.function.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.SequentialFunction;

/**
 * The {@link SampledReliabilityFunction} approximates a
 * {@link ReliabilityFunction} from a set of Samples.
 * 
 * @author glass, lukasiewycz
 * 
 */
public class SampledReliabilityFunction extends SequentialFunction implements ReliabilityFunction {

	/**
	 * Sorted list of all samples.
	 */
	protected List<Double> sortedSamples = new ArrayList<>();

	/**
	 * Array from the x values (as positions) to the y values.
	 */
	protected final double[] yarray;

	/**
	 * The x-stepsize.
	 */
	protected final double step;

	/**
	 * Constructs a {@link SampledReliabilityFunction}.
	 * 
	 * @param samples
	 *            the collection of samples
	 */
	public SampledReliabilityFunction(Collection<Double> samples) {

		this.sortedSamples.addAll(samples);
		Collections.sort(sortedSamples);

		int n = sortedSamples.size();

		int size = (int) Math.sqrt(n); // Math.pow(n, 1.0 / Math.sqrt(3));

		yarray = new double[size + 2];

		double maxValue = sortedSamples.get(n - 1);

		step = maxValue / size;

		int k = 0;

		for (int i = 0; i < size + 1; i++) {
			double x = step * i;
			while (k < n && sortedSamples.get(k) <= x) {
				k++;
			}
			double y = ((double) (n - k)) / n;
			yarray[i] = y;
		}
	}

	/**
	 * Returns all samples in an ordered {@link List}.
	 * 
	 * @return all samples
	 */
	public List<Double> getSamples() {
		return sortedSamples;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		double pos = x / step;

		int posa = (int) Math.floor(pos);
		int posb = (int) Math.ceil(pos);

		final double y;

		if (posb > yarray.length - 1) {
			y = 0.0;
		} else {
			double xa = step * posa;
			double xb = step * posb;
			double ya = yarray[posa];
			double yb = yarray[posb];

			double xoff = x - xa;

			double yoff = (xa == xb) ? 0.0 : ((ya - yb) / (xb - xa)) * xoff;

			y = ya - yoff;
		}
		return y;

	}
}
