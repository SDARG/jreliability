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

package org.jreliability.testsystems;

import org.jreliability.function.ReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TCNCSystemTest {
	protected final double TEST_DELTA = 0.000001;

	@Test
	public void testTCNCSystem() {
		TCNCSystem testSystem = new TCNCSystem();

		ReliabilityFunction systemReliability = testSystem.get();

		Assertions.assertEquals(0.99800498, systemReliability.getY(0.1), TEST_DELTA);
		Assertions.assertEquals(0.98239756, systemReliability.getY(0.9), TEST_DELTA);
		Assertions.assertEquals(0.86728735, systemReliability.getY(8), TEST_DELTA);
		Assertions.assertEquals(0.69742613, systemReliability.getY(25), TEST_DELTA);
		Assertions.assertEquals(0.35630174, systemReliability.getY(100), TEST_DELTA);
		Assertions.assertEquals(0.04978123, systemReliability.getY(300), TEST_DELTA);
	}
}
