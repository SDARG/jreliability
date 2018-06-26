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
package org.jreliability.function;

import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link UnreliabilityFunctionTest} to test the
 * {@link UnreliabilityFunction}.
 * 
 * @author glass
 *
 */
public class UnreliabilityFunctionTest {

	@Test
	public void testGetY() {
		ExponentialReliabilityFunction function = new ExponentialReliabilityFunction(0.005);
		UnreliabilityFunction distribution = new UnreliabilityFunction(function);
		/* Distribution is defined as 1 - Function */
		Assert.assertEquals((1 - distribution.getY(20)), function.getY(20), 1.0E-5);
	}

}
