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
 * The {@link ReliabilityFunction} represents a reliability or survival function
 * {@code R(x)} that is commonly defined as<br>
 * {@code R(x) = 1 - F(x)},<br>
 * with {@code F(x)} being a {@link Distribution} {@code F(x)}.
 * <p>
 * The reliability function gives at time {@code x} the probability that the
 * object of interest survives beyond {@code x}.
 * 
 * @author glass
 * 
 */
public interface ReliabilityFunction extends Function {

}
