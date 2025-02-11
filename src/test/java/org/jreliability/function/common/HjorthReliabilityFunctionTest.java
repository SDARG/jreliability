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
 * The {@link HjorthReliabilityFunctionTest} test the
 * {@link HjorthReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class HjorthReliabilityFunctionTest {

	@Test
	public void testIllegalBeta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new HjorthReliabilityFunction(-0.7, 2, 0.1);
		});
	}

	@Test
	public void testIllegalDelta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new HjorthReliabilityFunction(0.7, -2, 0.1);
		});
	}

	@Test
	public void testIllegalTheta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new HjorthReliabilityFunction(0.7, 2, -0.1);
		});
	}

	@Test
	public void testIllegalNullBeta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new HjorthReliabilityFunction(0.0, 2, 0.1);
		});
	}

	@Test
	public void testIllegalNullDelta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new HjorthReliabilityFunction(0.7, 0.0, 0.1);
		});
	}

	@Test
	public void testIllegalNullTheta() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new HjorthReliabilityFunction(0.7, 2, 0);
		});
	}

	@Test
	public void testGetY() {
		HjorthReliabilityFunction f = new HjorthReliabilityFunction(1, 2, 0.3);
		Assertions.assertEquals(0.0131730, f.getY(2), 0.000001);
	}

}
