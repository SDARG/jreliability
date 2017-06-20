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

import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTermTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link LiteralTermTest} test the {@link LinearTerm}.
 * 
 * @author reimann
 *
 */
public class ExponentialReliabilityFunctionTest {

	@Test
	public void testGetAlpha() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.7);
		Assert.assertEquals(f.getAlpha(), 0.7, 0.000000001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAlpha() {
		new ExponentialReliabilityFunction(-0.7);
	}

	@Test
	public void testGetY() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.005);
		Assert.assertEquals(0.9048, f.getY(20), 0.0001);
	}
}
