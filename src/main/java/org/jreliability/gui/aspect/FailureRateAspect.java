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
/**
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.gui.aspect;

import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.FailureRate;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link FailureRateAspect} represents the {@link FailureRate} of a given
 * {@link ReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public class FailureRateAspect extends AbstractAspect {

	/**
	 * Constructs a {@link FailureRateAspect}.
	 */
	public FailureRateAspect() {
		super("Failure Rate", "time t", "failure rate lambda(t)");
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
		FailureRate lambda = new FailureRate(reliabilityFunction);
		Double y = lambda.getY(x);
		if (y.isNaN()) {
			return null;
		}
		return y;
	}

}
