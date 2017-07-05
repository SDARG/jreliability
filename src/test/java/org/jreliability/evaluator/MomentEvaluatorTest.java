/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.evaluator;

import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link MomentEvaluatorTest} to test the {@link MomentEvaluator}.
 * 
 * @author glass
 *
 */
public class MomentEvaluatorTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAlpha() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.01);
		MomentEvaluator evaluator = new MomentEvaluator(0, 1.0E-8);
		evaluator.evaluate(f);
	}

	@Test
	public void testFirstMomentEvaluate() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.01);
		MomentEvaluator evaluator = new MomentEvaluator(1);
		double integral = evaluator.evaluate(f);
		Assert.assertEquals(integral, 100.0, 0.1);
	}

	@Test
	public void testThirdMomentEvaluate() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.01);
		MomentEvaluator evaluator = new MomentEvaluator(3, 1.0E-8);
		double integral = evaluator.evaluate(f);
		Assert.assertEquals(integral, 6.0E6, 1.0E-3);
	}

}
