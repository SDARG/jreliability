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
package org.jreliability.booleanfunction;

import org.jreliability.function.Phi;

/**
 * The {@code Term} represents a mathematical {@code Term} in a
 * {@code Boolean Function}. A {@code Term} can consist of other {@code Terms}.
 * In JReliability, each {@code Term} interrelates its embedded {@code Term}
 * elements via the same mathematical operator.
 * 
 * @author glass
 * 
 */
public interface Term extends Phi{

	/**
	 * Returns {@code false} if the whole {@code Term} is negated.
	 * 
	 * @return false if the whole term is negated
	 */
	public boolean sign();

}
