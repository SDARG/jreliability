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

package org.jreliability.gui.aspect;

import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link ReliabilityFunctionAspect} plots the {@link ReliabilityFunction}
 * itself.
 * 
 * @author glass
 * 
 */
public class ReliabilityFunctionAspect extends AbstractAspect {

	/**
	 * Constructs a {@link UnreliabilityFunctionAspect}.
	 * 
	 */
	public ReliabilityFunctionAspect() {
		super("Reliability Function", "time t", "reliability function R(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getUpper(org.jreliability.function.
	 * Function)
	 */
	@Override
	public double getUpper(ReliabilityFunction reliabilityFunction) {
		MomentEvaluator evaluator = new MomentEvaluator(1);
		return evaluator.getUpperBound(reliabilityFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getY(double,
	 * org.jreliability.function.Function)
	 */
	@Override
	public Double getY(double x, ReliabilityFunction reliabilityFunction) {
		Double y = reliabilityFunction.getY(x);
		if (y.isNaN()) {
			return null;
		}
		return y;
	}

}
