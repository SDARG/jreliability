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
 * The {@link WeibullReliabilityFunctionTest} test the
 * {@link WeibullReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class WeibullReliabilityFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAlpha() {
		new WeibullReliabilityFunction(-0.7,2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalBeta() {
		new WeibullReliabilityFunction(0.7,-2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNullAlpha() {
		new WeibullReliabilityFunction(0.0,2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNullBeta() {
		new WeibullReliabilityFunction(0.7,0.0);
	}
	
	@Test
	public void testGetY() {
		WeibullReliabilityFunction f = new WeibullReliabilityFunction(0.01,2);
		Assert.assertEquals(0.99750312, f.getY(5), 0.0001);
	}
}
