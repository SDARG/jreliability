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

public class BridgeSystemTest {
	final double TEST_DELTA = 0.000001;

	@Test
	public void testBridgeSystem() {

		ReliabilityFunction rel1 = new ExponentialReliabilityFunction(0.1);
		ReliabilityFunction rel2 = new ExponentialReliabilityFunction(0.2);
		ReliabilityFunction rel3 = new ExponentialReliabilityFunction(0.3);
		ReliabilityFunction rel4 = new ExponentialReliabilityFunction(0.4);
		ReliabilityFunction rel5 = new ExponentialReliabilityFunction(0.5);
				
		BridgeSystem testSystem = new BridgeSystem(rel1, rel2, rel3, rel4, rel5);
		
		ReliabilityFunction systemReliability = testSystem.get();
		
		System.out.println(systemReliability.getY(0.1));
		System.out.println(systemReliability.getY(0.3));
		System.out.println(systemReliability.getY(0.9));
		System.out.println(systemReliability.getY(2.7));
		System.out.println(systemReliability.getY(8));
		System.out.println(systemReliability.getY(25));
		
		assertEquals(0.99897497, systemReliability.getY(0.1), TEST_DELTA);
		assertEquals(0.99055298, systemReliability.getY(0.3), TEST_DELTA);
		assertEquals(0.91912372, systemReliability.getY(0.9), TEST_DELTA);
		assertEquals(0.55972244, systemReliability.getY(2.7), TEST_DELTA);
		assertEquals(0.09472739, systemReliability.getY(8), TEST_DELTA);
		assertEquals(0.00055310, systemReliability.getY(25), TEST_DELTA);
	}
}
