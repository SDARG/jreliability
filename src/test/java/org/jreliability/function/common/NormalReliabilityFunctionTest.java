package org.jreliability.function.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NormalReliabilityFunctionTest {
	NormalReliabilityFunction f;

	@Before
	public void init() {
		f = new NormalReliabilityFunction(0.0, 1.0);
	}

	@Test
	public void testGetY() {
		Assert.assertEquals(0.6700, 1 - f.getY(0.44), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeRho() {
		f = new NormalReliabilityFunction(0.0, -1.0);
		Assert.assertEquals(0.5, f.getY(0.0), 0.1);
	}

	@Test
	public void testGetYAtMu() {
		Assert.assertEquals(0.5000, f.getY(0.0), 0.0001);
	}
}
