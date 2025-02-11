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
 * The {@link ExponentialFailureFunctionTest} test the
 * {@link ExponentialFailureFunction}.
 * 
 * @author glass
 *
 */
public class ExponentialFailureFunctionTest {

	@Test
	public void testGetAlpha() {
		ExponentialFailureFunction f = new ExponentialFailureFunction(0.7);
		Assertions.assertEquals(f.getAlpha(), 0.7, 0.000000001);
	}

	@Test
	public void testIllegalAlpha() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new ExponentialFailureFunction(-0.7);
		});
	}

	@Test
	public void testGetY() {
		ExponentialFailureFunction f = new ExponentialFailureFunction(0.005);
		Assertions.assertEquals(0.0951626, f.getY(20), 0.0001);
	}

}
