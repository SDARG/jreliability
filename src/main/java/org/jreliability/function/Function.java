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
package org.jreliability.function;

import java.util.List;

/**
 * The {@link Function} represents a mathematical function {@code y = f(x)}.
 * 
 * @author glass
 * 
 */
public interface Function {

	/**
	 * Returns the {@code y} value for {@code y = f(x)}.
	 * 
	 * @param x
	 *            the x value
	 * @return the y for y = f(x)
	 */
	public double getY(double x);
	
	/**
	 * Returns a list of {@code y} values for a given list of {@code x} value.
	 * 
	 * @param x
	 *            the list of values
	 * @return the list of y values for each xØÏ
	 */
	public List<Double> getY(List<Double> xs);

}
