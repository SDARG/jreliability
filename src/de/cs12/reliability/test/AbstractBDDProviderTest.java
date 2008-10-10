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
