package org.jreliability.function;

import org.jreliability.function.common.ExponentialReliabilityFunction;
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

	@Test(expected = RuntimeException.class)
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
		SerialReliabilityFunction function = new SerialReliabilityFunction();
		ExponentialReliabilityFunction f1 = new ExponentialReliabilityFunction(0.005);
		ExponentialReliabilityFunction f2 = new ExponentialReliabilityFunction(0.004);
		function.add(f1);
		function.add(f2);
		Assert.assertEquals(function.getFunctions().size(),2);
	}

}
