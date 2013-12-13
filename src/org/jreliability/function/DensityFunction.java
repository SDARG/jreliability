/**
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.function;

import org.jreliability.cra.ReliabilityMeasure;

/**
 * The {@code DensityFunction} determines the density {@code f(x)} of a
 * {@code ReliabilityFunction} {@code R(x)}.
 * 
 * @author glass
 * 
 */
public class DensityFunction implements ReliabilityMeasure {

	/**
	 * The {@code ReliabilityFunction} for which the {@code DensityFunction} is
	 * to determine.
	 */
	protected final ReliabilityFunction reliabilityFunction;

	/**
	 * Constructs a {@code DensityFunction} with a given
	 * {@code ReliabilityFunction}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 */
	public DensityFunction(ReliabilityFunction reliabilityFunction) {
		this.reliabilityFunction = reliabilityFunction;
	}

	/**
	 * Returns the {@code y} value for {@code y = f(x)}.
	 * 
	 * @param x
	 *            the x value
	 * @return the y for y = f(x)
	 */
	public double getY(double x) {
		double deltaT = 0.00000001;
		double y = reliabilityFunction.getY(x);
		double yPrime = reliabilityFunction.getY(x + deltaT);
		double density = (y - yPrime) / deltaT;
		return density;
	}

}
