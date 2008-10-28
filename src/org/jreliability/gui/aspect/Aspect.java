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
package org.jreliability.gui.aspect;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code Aspect} allows to determine the {@code y-value} for the {@code
 * ReliabilityFunction} {@code y = R(x)} under the current {@code Aspect}.
 * 
 * @author glass
 * 
 */
public interface Aspect {

	/**
	 * Returns the {@code y-value} for the {@code ReliabilityFunction} {@code y
	 * = R(x)} under the current {@code Aspect}.
	 * 
	 * @param x
	 *            the x-value
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * 
	 * @return the y-value for the reliabilityFunction y = R(x) under the
	 *         current aspect
	 */
	public Double getY(double x, ReliabilityFunction reliabilityFunction);

	/**
	 * Returns the name of the {@code Aspect}.
	 * 
	 * @return the name of the aspect
	 */
	public String getName();

	/**
	 * Returns the label for the {@code x-axis}.
	 * 
	 * @return the label for the x-axis
	 */
	public String getXAxis();

	/**
	 * Returns the label for the {@code x-axis}.
	 * 
	 * @return the label for the y-axis
	 */
	public String getYAxis();

	/**
	 * Returns the lower bound of the {@code ReliabilityFunction} under the
	 * current {@code Aspect}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @return the lower bound of the reliability Function
	 */
	public double getLower(ReliabilityFunction reliabilityFunction);

	/**
	 * Returns the upper bound of the {@code ReliabilityFunction} under the
	 * current {@code Aspect}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @return the upper bound of the reliabilityFunction
	 */
	public double getUpper(ReliabilityFunction reliabilityFunction);
}
