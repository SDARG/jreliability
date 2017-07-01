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
 * The {@link HjorthReliabilityFunctionTest} test the
 * {@link HjorthReliabilityFunction}.
 * 
 * @author glass
 *
 */
public class HjorthReliabilityFunctionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalBeta() {
		new HjorthReliabilityFunction(-0.7, 2, 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalDelta() {
		new HjorthReliabilityFunction(0.7, -2, 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalTheta() {
		new HjorthReliabilityFunction(0.7, 2, -0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNullBeta() {
		new HjorthReliabilityFunction(0.0, 2, 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNullDelta() {
		new HjorthReliabilityFunction(0.7, 0.0, 0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalNullTheta() {
		new HjorthReliabilityFunction(0.7, 2, 0);
	}

	@Test
	public void testGetY() {
		HjorthReliabilityFunction f = new HjorthReliabilityFunction(1, 2, 0.3);
		Assert.assertEquals(0.0131730, f.getY(2), 0.000001);
	}

}
