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
package org.jreliability.gui;

import org.jreliability.evaluator.IntegralEvaluator;
import org.jreliability.function.Function;


/**
 * The {@code FailureRateAspect}
 * 
 * @author glass
 * 
 */
public class FailureRateAspect extends AbstractAspect {

	/**
	 * Constructs a {@code FailureRateAspect}.
	 */
	public FailureRateAspect() {
		super("Failure Rate", "time t", "failure rate lambda(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jreliability.gui.Aspect#getUpper(org.jreliability.function.
	 * Function)
	 */
	@Override
	public double getUpper(Function function) {
		IntegralEvaluator evaluator = new IntegralEvaluator();
		return evaluator.getUpperBound(function);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getY(double,
	 * org.jreliability.function.Function)
	 */
	@Override
	public double getY(double x, Function function) {
		double deltaT = 0.00000001;
		double y = function.getY(x);
		double yPrime = function.getY(x + deltaT);
		double density = (y - yPrime) / deltaT;
		double lambda = density / y;
		return lambda;
	}

}
