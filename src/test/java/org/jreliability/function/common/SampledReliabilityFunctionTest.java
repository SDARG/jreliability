package org.jreliability.function.common;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link SampledReliabilityFunctionTest} to test the
 * {@link SampledReliabilityFunction}.
 * 
 * @author reimann
 *
 */
public class SampledReliabilityFunctionTest {
	SampledReliabilityFunction f;
	List<Double> samples = Arrays.asList(0.0, 1.0);

	@Before
	public void init() {
		f = new SampledReliabilityFunction(samples);
	}

	@Test
	public void testGetY() {
		Assert.assertEquals(0.25, f.getY(0.5), 0.0001);
	}

	@Test
	public void testGetSamples() {
		Assert.assertEquals(samples, f.getSamples());
	}
}
