/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.function.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link ConstantReliabilityFunctionTest} test the
 * {@link ConstantReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class ConstantReliabilityFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNegativeSuccessProbability() {
		new ConstantReliabilityFunction(-0.7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalPositiveSuccessProbability() {
		new ConstantReliabilityFunction(1.1);
	}
	
	@Test
	public void testGetY() {
		ConstantReliabilityFunction f = new ConstantReliabilityFunction(0.975);
		Assert.assertEquals(0.975, f.getY(20), 0.0001);
	}
}
