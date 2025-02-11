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
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SeriesParallelSystemTest {
	protected final double TEST_DELTA = 0.000001;

	@Test
	public void testSeriesParallelSystem() {

		ReliabilityFunction rel1 = new ExponentialReliabilityFunction(0.1);
		ReliabilityFunction rel2 = new ExponentialReliabilityFunction(0.2);
		ReliabilityFunction rel3 = new ExponentialReliabilityFunction(0.3);

		SeriesParallelSystem testSystem = new SeriesParallelSystem(rel1, rel2, rel3);

		ReliabilityFunction systemReliability = testSystem.get();

		Assertions.assertEquals(0.97025433, systemReliability.getY(0.1), TEST_DELTA);
		Assertions.assertEquals(0.91235820, systemReliability.getY(0.3), TEST_DELTA);
		Assertions.assertEquals(0.75255622, systemReliability.getY(0.9), TEST_DELTA);
		Assertions.assertEquals(0.40093708, systemReliability.getY(2.7), TEST_DELTA);
		Assertions.assertEquals(0.05084809, systemReliability.getY(8), TEST_DELTA);
		Assertions.assertEquals(0.00004882, systemReliability.getY(25), TEST_DELTA);
	}
}
