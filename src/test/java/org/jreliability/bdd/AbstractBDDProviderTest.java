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

package org.jreliability.bdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link AbstractBDDProviderTest} is the abstract class for all tests of
 * the {@link BDDProvider}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class AbstractBDDProviderTest extends AbstractBDDTest {

	/**
	 * Tests the {@link get} method of the provider.
	 */
	@Test
	public void testGet() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		Assertions.assertNotEquals(a, b);
		BDD<String> aPrime = provider.get("a");
		Assertions.assertEquals(a, aPrime);
	}

	/**
	 * Tests the {@link getProvider} method with no arguments.
	 */
	@Test
	public void testGetProviderEmpty() {
		Assertions.assertDoesNotThrow(() -> {
			BDDProvider<String> provider = factory.getProvider();

			for (int i = 0; i < 200; i++) {
				@SuppressWarnings("unused")
				BDD<String> a = provider.get("" + i);
			}
		});
	}

}
