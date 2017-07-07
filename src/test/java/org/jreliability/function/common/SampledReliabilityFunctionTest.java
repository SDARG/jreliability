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
package org.jreliability.function.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link SampledReliabilityFunctionTest} to test the
 * {@link SampledReliabilityFunction}.
 * 
 * @author reimann, glass
 *
 */
public class SampledReliabilityFunctionTest {
	SampledReliabilityFunction f;
	List<Double> samples = Arrays.asList(1.5, 0.5, 1.0);

	@Before
	public void init() {
		f = new SampledReliabilityFunction(samples);
	}

	@Test
	public void testGetY() {
		Assert.assertEquals(0.33333333333, f.getY(1.0), 0.0001);
	}

	@Test
	public void testGetYAtZero() {
		Assert.assertEquals(1.0, f.getY(0.0), 0.0001);
	}
	
	@Test
	public void testGetYAtYLargerThanSample() {
		Assert.assertEquals(0.0, f.getY(1.0E5), 0.0001);
	}
	
	@Test
	public void testGetSamples() {
		Collections.sort(samples);
		Assert.assertEquals(samples, f.getSamples());
	}
	
}
