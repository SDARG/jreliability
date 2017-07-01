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
 * The {@link ConstantFailureFunctionTest} test the
 * {@link ConstantFailureFunction}.
 * 
 * @author glass
 *
 */
public class ConstantFailureFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNegativeSuccessProbability() {
		new ConstantFailureFunction(-0.7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalPositiveSuccessProbability() {
		new ConstantFailureFunction(1.1);
	}
	
	@Test
	public void testGetY() {
		ConstantFailureFunction f = new ConstantFailureFunction(0.125);
		Assert.assertEquals(0.125, f.getY(20), 0.0001);
	}
}
