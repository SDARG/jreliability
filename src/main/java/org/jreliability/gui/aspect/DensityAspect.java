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
import org.jreliability.function.DensityFunction;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link DensityAspect} represents the density of a {@link
 * ReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public class DensityAspect extends AbstractAspect {

	/**
	 * Constructs a {@link DensityAspect}.
	 */
	public DensityAspect() {
		super("DensityFunction", "time t", "density function f(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.jreliability.gui.Aspect#getUpper(org.jreliability.function.
	 * ReliabilityFunction)
	 */
	public double getUpper(ReliabilityFunction reliabilityFunction) {
		MomentEvaluator evaluator = new MomentEvaluator(1);
		return evaluator.getUpperBound(reliabilityFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getY(double,
	 * org.jreliability.function.ReliabilityFunction)
	 */
	public Double getY(double x, ReliabilityFunction reliabilityFunction) {
		DensityFunction density = new DensityFunction(reliabilityFunction);
		Double y = density.getY(x);
		if (y.isNaN()) {
			return null;
		}
		return y;
	}

}
