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
 * The {@link InverseEvaluatorTest} to test the
 * {@link InverseEvaluator}.
 * 
 * @author glass
 *
 */
public class InverseEvaluatorTest {

	@Test
	public void testEvaluate() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.005);
		InverseEvaluator evaluator = new InverseEvaluator();
		Assert.assertEquals(evaluator.evaluate(f,0.876), 26.4778, 1.0E-4);
	}

}
