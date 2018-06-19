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

/**
 * The {@link ConstantFailureFunction} returns a constant failure probability
 * for which it must hold that <br>
 * {@code 0 =< failure probability =< 1}.
 * 
 * @author glass
 *
 */
public class ConstantFailureFunction extends ConstantReliabilityFunction {

	public ConstantFailureFunction(double failureProbability) {
		super(1 - failureProbability);
	}

	@Override
	public double getY(double x) {
		return 1 - super.getY(x);
	}

}
