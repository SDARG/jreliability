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

package org.jreliability.evaluator;

import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link IntegralEvaluatorTest} to test the {@link IntegralEvaluator}.
 * 
 * @author glass
 *
 */
public class IntegralEvaluatorTest {

	@Test
	public void testEvaluate() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.01);
		IntegralEvaluator evaluator = new IntegralEvaluator();
		double integral = evaluator.evaluate(f, 0, 2);
		Assertions.assertEquals(integral, 1.98013, 1.0E-5);
	}

	@Test
	public void testNegativeEvaluate() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.01);
		/*
		 * Note: The epsilon is in between two integration steps, not the absolute
		 * error!
		 */
		IntegralEvaluator evaluator = new IntegralEvaluator(1.0E-7);
		double integral = evaluator.evaluate(f, -2, 4);
		Assertions.assertEquals(integral, 5.94119, 1.0E-5);
	}

}
