package de.cs12.reliability.test;

import org.junit.Before;

import de.cs12.reliability.bdd.BDDProviderFactory;

/**
 * The {@code AbstractBDDTest} is the base class for all tests on {@code BDD}s.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class AbstractBDDTest {

	protected BDDProviderFactory factory;

	/**
	 * Initialize the specific factory.
	 */
	@Before
	public abstract void init();
	
}
