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
/**
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.function;

import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link FailureRateTest} to test the {@link FailureRate}.
 * 
 * @author glass
 *
 */
public class FailureRateTest {

	@Test
	public void testGetY() {
		/*
		 * FailureRate for ExponentialDistribution equals the lambda parameter
		 * and is constant"
		 */
		FailureRate failureRate = new FailureRate(new ExponentialReliabilityFunction(0.005));
		Assert.assertEquals(0.005, failureRate.getY(10), 1.0E-5);
	}

	@Test
	public void testGetYAtZero() {
		/*
		 * FailureRate for ExponentialDistribution equals the lambda parameter
		 * and is constant"
		 */
		FailureRate failureRate = new FailureRate(new ExponentialReliabilityFunction(0.1));
		Assert.assertEquals(Double.NaN, failureRate.getY(1.0E12), 1.0E-5);
	}

}
