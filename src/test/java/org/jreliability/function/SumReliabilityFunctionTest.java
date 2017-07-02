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
package org.jreliability.function;

import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.SerialReliabilityFunction;
import org.jreliability.function.common.SumReliabilityFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link SumReliabilityFunctionTest} to test the
 * {@link SumReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class SumReliabilityFunctionTest {

	@Test(expected = RuntimeException.class)
	public void testNoFunctionsSpeficied() {
		SumReliabilityFunction function = new SumReliabilityFunction();
		function.getY(5);
	}

	@Test
	public void testGetY() {
		SumReliabilityFunction function = new SumReliabilityFunction();
		ExponentialReliabilityFunction f1 = new ExponentialReliabilityFunction(0.005);
		ExponentialReliabilityFunction f2 = new ExponentialReliabilityFunction(0.004);
		function.add(f1);
		function.add(f2);
		Assert.assertEquals(1.827954, function.getY(20), 0.00001);
	}
	
	@Test
	public void testGetFunctions() {
		SumReliabilityFunction function = new SumReliabilityFunction();
		ExponentialReliabilityFunction f1 = new ExponentialReliabilityFunction(0.005);
		ExponentialReliabilityFunction f2 = new ExponentialReliabilityFunction(0.004);
		function.add(f1);
		function.add(f2);
		Assert.assertEquals(function.getFunctions().size(),2);
	}

}
