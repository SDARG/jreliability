package org.jreliability.function;

import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link ParallelReliabilityFunctionTest} to test the
 * {@link ParallelReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class ParallelReliabilityFunctionTest {

	@Test(expected = RuntimeException.class)
	public void testNoFunctionsSpeficied() {
		ParallelReliabilityFunction function = new ParallelReliabilityFunction();
		function.getY(5);
	}

	@Test
	public void testGetY() {
		ParallelReliabilityFunction function = new ParallelReliabilityFunction();
		ExponentialReliabilityFunction f1 = new ExponentialReliabilityFunction(0.005);
		ExponentialReliabilityFunction f2 = new ExponentialReliabilityFunction(0.004);
		function.add(f1);
		function.add(f2);
		Assert.assertEquals(0.99268355, function.getY(20), 0.00001);
	}

}
