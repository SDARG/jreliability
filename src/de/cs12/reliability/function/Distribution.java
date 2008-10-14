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
package de.cs12.reliability.function;

/**
 * The {@code Distribution} is an interface to represent functions that are also
 * distributions in the mathematical sense.
 * 
 * @author glass
 * 
 */
public interface Distribution extends Function {

	/**
	 * Returns an {@code x} value for {@code y = f(x)}.
	 * 
	 * @param y
	 *            the y value
	 * @return an x value for the given y value
	 */
	public double getX(double y);

}
