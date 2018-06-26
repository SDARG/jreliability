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
import org.jreliability.function.UnreliabilityFunction;

/**
 * The {@link UnreliabilityFunctionAspect} represents the
 * {@link UnreliabilityFunction} of a {@link ReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public class UnreliabilityFunctionAspect extends AbstractAspect {

	/**
	 * Constructs a {@link UnreliabilityFunctionAspect}.
	 * 
	 */
	public UnreliabilityFunctionAspect() {
		super("Unreliability Function", "time t", "unreliability function F(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getUpper(org.jreliability.function.
	 * Function)
	 */
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
	public Double getY(double x, ReliabilityFunction reliabilityFunction) {
		UnreliabilityFunction distribution = new UnreliabilityFunction(reliabilityFunction);
		Double y = distribution.getY(x);
		if (y.isNaN()) {
			return null;
		}
		return y;
	}

}
