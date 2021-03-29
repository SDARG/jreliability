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

import static org.junit.Assert.assertEquals;

import org.jreliability.function.ReliabilityFunction;
import org.junit.Test;

public class TINCSystemTest {
	protected final double TEST_DELTA = 0.000001;

	@Test
	public void testTINCSystem() {
		TINCSystem testSystem = new TINCSystem();
		
		ReliabilityFunction systemReliability = testSystem.get();
		
		assertEquals(0.99601972, systemReliability.getY(0.1), TEST_DELTA);
		assertEquals(0.96543420, systemReliability.getY(0.9), TEST_DELTA);
		assertEquals(0.73542404, systemReliability.getY(8), TEST_DELTA);
		assertEquals(0.33379704, systemReliability.getY(25), TEST_DELTA);
		assertEquals(0.00731465, systemReliability.getY(100), TEST_DELTA);
		assertEquals(0.00000374, systemReliability.getY(250), TEST_DELTA);
	}
}
