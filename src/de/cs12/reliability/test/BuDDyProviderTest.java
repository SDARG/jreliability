package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.BuDDyProviderFactory;

/**
 * The {@code BuDDyProviderTest} is the {@code AbstractBDDProviderTest} for the {@link BuDDy}.
 * 
 * @author lukasiewycz
 * 
 */
public class BuDDyProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new BuDDyProviderFactory();
	}

}
