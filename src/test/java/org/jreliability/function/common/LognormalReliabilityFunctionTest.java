package org.jreliability.function.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LognormalReliabilityFunctionTest {
	LognormalReliabilityFunction f;

	@Before
	public void init() {
		f = new LognormalReliabilityFunction(0.0, 0.5);
	}

	@Test
	public void testGetY() {
		Assert.assertEquals(0.5, f.getY(1.0), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeRho() {
		new LognormalReliabilityFunction(0.0, -0.5);
	}

}
