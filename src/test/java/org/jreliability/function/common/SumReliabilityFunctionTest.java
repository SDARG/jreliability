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
package org.jreliability.function.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jreliability.function.ReliabilityFunction;
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

	@Test(expected = IllegalStateException.class)
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
		List<Double> xs = new ArrayList<Double>();
		xs.add(10.0);
		xs.add(20.0);
		List<Double> ys = function.getY(xs);
		Assert.assertEquals(1.912019, ys.get(0), 0.0001);
		Assert.assertEquals(1.827954, ys.get(1), 0.00001);
	}

	@Test
	public void testGetFunctions() {
		Set<ReliabilityFunction> functions = new HashSet<>();
		functions.add(new ExponentialReliabilityFunction(0.005));
		functions.add(new ExponentialReliabilityFunction(0.004));
		SumReliabilityFunction function = new SumReliabilityFunction(functions);
		Assert.assertEquals(function.getFunctions().size(), 2);
	}

}
