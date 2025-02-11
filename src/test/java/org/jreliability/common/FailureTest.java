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
package org.jreliability.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link FailureTest} to test the {@link Failure}.
 * 
 * @author glass
 *
 */
public class FailureTest {

	@Test
	public void testGetObject() {
		String event = "DISASTER";
		double time = 20;

		Failure<String> failure = new Failure<String>(event, time);
		Assertions.assertEquals(failure.getObject(), event);
	}

	@Test
	public void testGetTime() {
		String event = "DISASTER";
		double time = 20;

		Failure<String> failure = new Failure<String>(event, time);
		Assertions.assertEquals(failure.getTime(), time, 1.0E-8);
	}

	@Test
	public void testCompareTo() {
		String event = "DISASTER";
		double time = 20;
		Failure<String> failure = new Failure<String>(event, time);

		String eventTwo = "FAILURE";
		double timeTwo = 10;
		Failure<String> failureTwo = new Failure<String>(eventTwo, timeTwo);

		String eventThree = "THE SAME DISASTER";
		double timeThree = 20;
		Failure<String> failureThree = new Failure<String>(eventThree, timeThree);

		String eventFour = "WHEN THE WORLD ENDS";
		double timeFour = 30;
		Failure<String> failureFour = new Failure<String>(eventFour, timeFour);

		Assertions.assertTrue(failure.compareTo(failureTwo) == 1, "Compare larger");
		Assertions.assertTrue(failure.compareTo(failureThree) == 0, "Compare equal");
		Assertions.assertTrue(failure.compareTo(failureFour) == -1, "Compare smaller");
	}

}
