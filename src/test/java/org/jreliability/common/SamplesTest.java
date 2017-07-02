/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link SamplesTest} to test the {@link Samples}.
 * 
 * @author glass
 *
 */
public class SamplesTest {

	@Test
	public void testConstructor() {
		Samples samples = new Samples();
		samples.put(47.0, 11.0);
		Assert.assertTrue(samples.size() == 1);
	}

}
