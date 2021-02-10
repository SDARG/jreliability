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
 * The {@link DensityFunction} determines the density {@code f(x)} of a
 * {@link Function} {@code F(x)}.
 * 
 * @author glass
 * 
 */
public class DensityFunction {

	/**
	 * The {@link Function} for which the {@link DensityFunction} is
	 * to determine.
	 */
	protected final Function function;

	/**
	 * Constructs a {@link DensityFunction} with a given
	 * {@link Function}.
	 * 
	 * @param function
	 *            the function
	 */
	public DensityFunction(Function function) {
		this.function = function;
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
		double y = function.getY(x);
		double yPrime = function.getY(x + deltaT);
		double density = Math.abs(y - yPrime) / deltaT;
		return density;
	}

}
