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
 * The {@link NMWDReliabilityFunctionTest} test the
 * {@link NMWDReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class NMWDReliabilityFunctionTest {

	@Test
	public void testIllegalLambda() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new NMWDReliabilityFunction(0.0, 2, 0.8);
		});
	}

	@Test
	public void testIllegalA() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new NMWDReliabilityFunction(0.001, 0.0, 0.8);
		});
	}

	@Test
	public void testIllegalB() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new NMWDReliabilityFunction(0.001, 2, -0.5);
		});
	}

	@Test
	public void testGetY() {
		NMWDReliabilityFunction f = new NMWDReliabilityFunction(0.01512, 0.0876, 0.389);
		Assertions.assertEquals(0.8380313, f.getY(5), 1.0E-5);
	}
}
