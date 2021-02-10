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

import static org.junit.Assert.*;

import org.jreliability.function.ReliabilityFunction;
import org.junit.Test;

public class TC_NC_SystemTest {
	final double TEST_DELTA = 0.000001;

	@Test
	public void testTC_NC_System() {
		TC_NC_System testSystem = new TC_NC_System();
		
		ReliabilityFunction systemReliability = testSystem.get();
		
		assertEquals(0.99800498, systemReliability.getY(0.1), TEST_DELTA);
		assertEquals(0.98239756, systemReliability.getY(0.9), TEST_DELTA);
		assertEquals(0.86728735, systemReliability.getY(8), TEST_DELTA);
		assertEquals(0.69742613, systemReliability.getY(25), TEST_DELTA);
		assertEquals(0.35630174, systemReliability.getY(100), TEST_DELTA);
		assertEquals(0.04978123, systemReliability.getY(300), TEST_DELTA);
	}
}
