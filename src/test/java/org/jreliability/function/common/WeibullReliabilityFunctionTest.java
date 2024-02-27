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

package org.jreliability.function.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link WeibullReliabilityFunctionTest} test the
 * {@link WeibullReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class WeibullReliabilityFunctionTest {

	@Test
	public void testIllegalAlpha() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new WeibullReliabilityFunction(-0.7, 2);
		});
	}

	@Test
	public void testIllegalBeta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new WeibullReliabilityFunction(0.7, -2);
		});
	}

	@Test
	public void testIllegalNullAlpha() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new WeibullReliabilityFunction(0.0, 2);
		});
	}

	@Test
	public void testIllegalNullBeta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new WeibullReliabilityFunction(0.7, 0.0);
		});
	}

	@Test
	public void testGetY() {
		WeibullReliabilityFunction f = new WeibullReliabilityFunction(0.01, 2);
		Assertions.assertEquals(0.99750312, f.getY(5), 0.0001);
	}
}
