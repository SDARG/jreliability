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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link LognormalReliabilityFunctionTest} to test the
 * {@link LognormalReliabilityFunction}.
 * 
 * @author reimann
 *
 */
public class LognormalReliabilityFunctionTest {
	protected LognormalReliabilityFunction f;

	@Before
	public void init() {
		f = new LognormalReliabilityFunction(0.0, 0.5);
	}

	@Test
	public void testGetY() {
		Assert.assertEquals(0.5, f.getY(1.0), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeMu() {
		f = new LognormalReliabilityFunction(-0.1, -1.0);
		Assert.assertEquals(0.5, f.getY(0.0), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeRho() {
		f = new LognormalReliabilityFunction(0.0, -1.0);
		Assert.assertEquals(0.5, f.getY(0.0), 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testZeroRho() {
		f = new LognormalReliabilityFunction(0.0, 0.0);
		Assert.assertEquals(0.5, f.getY(0.0), 0.1);
	}

}
