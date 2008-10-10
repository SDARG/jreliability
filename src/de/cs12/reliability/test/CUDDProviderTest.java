package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.CUDD;
import de.cs12.reliability.javabdd.CUDDProviderFactory;

/**
 * 
 * The {@code CUDDProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@link CUDD}.
 * 
 * @author lukasiewycz
 * 
 */
public class CUDDProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new CUDDProviderFactory();
	}

}
