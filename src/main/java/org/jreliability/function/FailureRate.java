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

package org.jreliability.function;

/**
 * The {@link FailureRate} determines the failure rate {@code lambda} of a given
 * {@link ReliabilityFunction} and is defined as
 * <p>
 * {@code lambda(x) = f(x) / R(x)}.
 * 
 * @author glass
 * 
 */
public class FailureRate {

	/**
	 * The {@link ReliabilityFunction} for which the {@link FailureRate} is to
	 * determine.
	 */
	protected final ReliabilityFunction reliabilityFunction;

	/**
	 * The used {@link DensityFunction} to determine {@code f(x)}.
	 */
	protected final DensityFunction densityFunction;

	/**
	 * Constructs a {@link FailureRate} with a given {@link ReliabilityFunction}
	 * .
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 */
	public FailureRate(ReliabilityFunction reliabilityFunction) {
		this.reliabilityFunction = reliabilityFunction;
		this.densityFunction = new DensityFunction(reliabilityFunction);
	}

	/**
	 * Returns the failure rate {@code lambda} {@code lambda(x) = f(x) / R(x)}
	 * of the {@link ReliabilityFunction} at the {@code x}-value.
	 * 
	 * @param x
	 *            the x value
	 * @return the failure rate at value x
	 */
	public double getY(double x) {
		double density = densityFunction.getY(x);
		double reliability = reliabilityFunction.getY(x);
		double y = density / reliability;
		/*
		 * This case results from e.g. very large y-values with reliability
		 * being almost 0. There, the approximation via the DensityFunction
		 * might suffer from rounding the density to 0.
		 */
		if (density == 0 && reliability != 1) {
			return Double.NaN;
		}

		return y;
	}

}
