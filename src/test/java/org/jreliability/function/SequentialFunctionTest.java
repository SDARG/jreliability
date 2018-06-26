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

import java.util.ArrayList;
import java.util.List;

import org.jreliability.function.common.ExponentialFailureFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link SequentialFunctionTest} test the sequential implementation of getY
 * for multiple x in the abstract {@link SequentialFunction}.
 * 
 * @author glass
 *
 */
public class SequentialFunctionTest {

	@Test
	public void testGetY() {
		ExponentialFailureFunction f = new ExponentialFailureFunction(0.005);
		List<Double> xs = new ArrayList<Double>();
		xs.add(10.0);
		xs.add(20.0);
		List<Double> ys = f.getY(xs);
		Assert.assertEquals(0.0487706, ys.get(0), 0.0001);
		Assert.assertEquals(0.0951626, ys.get(1), 0.0001);
	}

}
