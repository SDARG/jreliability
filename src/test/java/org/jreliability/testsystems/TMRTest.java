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
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.Test;

public class TMRTest {
	final double TEST_DELTA = 0.000001;

	@Test
	public void testTMR() {
		ReliabilityFunction rel1 = new ExponentialReliabilityFunction(0.1);
		ReliabilityFunction rel2 = new ExponentialReliabilityFunction(0.2);
		ReliabilityFunction rel3 = new ExponentialReliabilityFunction(0.3);
				
		TMR testSystem = new TMR(rel1, rel2, rel3);
		
		ReliabilityFunction systemReliability = testSystem.get();
		
		assertEquals(0.99893533, systemReliability.getY(0.1), TEST_DELTA);
		assertEquals(0.99101917, systemReliability.getY(0.3), TEST_DELTA);
		assertEquals(0.93318746, systemReliability.getY(0.9), TEST_DELTA);
		assertEquals(0.64789645, systemReliability.getY(2.7), TEST_DELTA);
		assertEquals(0.13333630, systemReliability.getY(8), TEST_DELTA);
		assertEquals(0.00060159, systemReliability.getY(25), TEST_DELTA);
	}
}
