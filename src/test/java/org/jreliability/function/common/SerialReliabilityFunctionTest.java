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
package org.jreliability.function.common;

import java.util.HashSet;
import java.util.Set;

import org.jreliability.function.ReliabilityFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link SerialReliabilityFunctionTest} to test the
 * {@link SerialReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class SerialReliabilityFunctionTest {

	@Test(expected = IllegalStateException.class)
	public void testNoFunctionsSpeficied() {
		SerialReliabilityFunction function = new SerialReliabilityFunction();
		function.getY(5);
	}

	@Test
	public void testGetY() {
		SerialReliabilityFunction function = new SerialReliabilityFunction();
		ExponentialReliabilityFunction f1 = new ExponentialReliabilityFunction(0.005);
		ExponentialReliabilityFunction f2 = new ExponentialReliabilityFunction(0.004);
		function.add(f1);
		function.add(f2);
		Assert.assertEquals(0.8352702, function.getY(20), 0.00001);
	}

	@Test
	public void testGetFunctions() {
		Set<ReliabilityFunction> functions = new HashSet<>();
		functions.add(new ExponentialReliabilityFunction(0.005));
		functions.add(new ExponentialReliabilityFunction(0.004));
		SerialReliabilityFunction function = new SerialReliabilityFunction(functions);
		Assert.assertEquals(function.getFunctions().size(), 2);
	}

}
