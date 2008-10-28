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

import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.Distribution;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code DistributionAspect} represents the {@code Distribution} of a
 * {@code ReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public class DistributionAspect extends AbstractAspect {

	/**
	 * Constructs a {@code DistributionAspect}.
	 * 
	 */
	public DistributionAspect() {
		super("Distribution", "time t", "distribution function F(t)");
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
		Distribution distribution = new Distribution(reliabilityFunction);
		Double y = distribution.getY(x);
		if (y.isNaN()) {
			return null;
		} else {
			return y;
		}
	}

}
