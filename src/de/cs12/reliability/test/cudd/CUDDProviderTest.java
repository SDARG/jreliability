package de.cs12.reliability.test.cudd;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDProviderTest;

/**
 * 
 * The {@code CUDDProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@code CUDD}.
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
		this.factory = new JBDDProviderFactory(Type.CUDD);
	}

}
