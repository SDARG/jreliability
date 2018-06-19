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
 * The {@link UnreliabilityFunction} determines the
 * {@link UnreliabilityFunction} {@code F(x)} of a given
 * {@link ReliabilityFunction} {@code R(x)} as
 * <p>
 * {@code F(x) = 1 - R(x)}.
 * 
 * @author glass
 * 
 */
public class UnreliabilityFunction extends SequentialFunction {

	/**
	 * The {@link ReliabilityFunction} for which the {@link UnreliabilityFunction}
	 * is to determine.
	 */
	protected final ReliabilityFunction reliabilityFunction;

	/**
	 * Constructs a {@link UnreliabilityFunction} with a given
	 * {@link ReliabilityFunction}.
	 * 
	 * @param reliabilityFunction
	 *            the reliability reliabilityFunction
	 */
	public UnreliabilityFunction(ReliabilityFunction reliabilityFunction) {
		this.reliabilityFunction = reliabilityFunction;
	}

	/**
	 * Returns the {@code y} value for {@code y = F(x) = 1 - R(x)}.
	 * 
	 * @param x
	 *            the x value
	 * @return the y for y = F(x)
	 */
	@Override
	public double getY(double x) {
		double y = 1 - reliabilityFunction.getY(x);
		return y;
	}

}
