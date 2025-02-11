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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@link NormalReliabilityFunctionTest} to test the
 * {@link NormalReliabilityFunction}.
 * 
 * @author reimann
 *
 */
public class NormalReliabilityFunctionTest {
	protected NormalReliabilityFunction f;

	@BeforeEach
	public void init() {
		f = new NormalReliabilityFunction(0.0, 1.0);
	}

	@Test
	public void testGetY() {
		Assertions.assertEquals(0.6700, 1 - f.getY(0.44), 0.0001);
	}

	@Test
	public void testNegativeMu() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			f = new NormalReliabilityFunction(-0.1, -1.0);
			Assertions.assertEquals(0.5, f.getY(0.0), 0.1);
		});
	}

	@Test
	public void testNegativeRho() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			f = new NormalReliabilityFunction(0.0, -1.0);
			Assertions.assertEquals(0.5, f.getY(0.0), 0.1);
		});
	}

	@Test
	public void testZeroRho() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			f = new NormalReliabilityFunction(0.0, 0.0);
			Assertions.assertEquals(0.5, f.getY(0.0), 0.1);
		});
	}

	@Test
	public void testGetYAtMu() {
		Assertions.assertEquals(0.5000, f.getY(0.0), 0.0001);
	}
}
