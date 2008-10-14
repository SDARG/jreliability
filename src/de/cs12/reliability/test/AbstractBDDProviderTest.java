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
package de.cs12.reliability.test;

import org.junit.Test;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code AbstractBDDProviderTest} is the abstract class for all tests of
 * the {@line BDDProvider}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class AbstractBDDProviderTest extends AbstractBDDTest {

	/**
	 * Tests the {@code get} method of the provider.
	 */
	@Test
	public void testGet() {
		BDDProvider<String> provider = factory.getProvider();

		provider.get("a");
		provider.get("b");
	}

	/**
	 * Tests the {@code getProvider} method with no arguments.
	 */
	@Test
	public void testGetProviderEmpty() {
		BDDProvider<String> provider = factory.getProvider();

		for (int i = 0; i < 200; i++) {
			@SuppressWarnings("unused")
			BDD<String> a = provider.get("" + i);
		}
	}

	/**
	 * Tests the {@code get} method of the provider and if the number of
	 * variables is automatically increased.
	 */
	@Test
	public void testGetAutoIncreased() {
		BDDProvider<String> provider = factory.getProvider();

		provider.get("a");
		provider.get("b");
		provider.get("c");
	}

}
