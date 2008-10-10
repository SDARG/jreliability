package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.JBDDProviderFactory;

/**
 * The {@code JBDDOperatorTest} is a unit test operator class for the
 * {@link JBDD} class.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class JBDDOperatorTest extends AbstractBDDOperatorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDTest#init()
	 */
	public void init() {
		this.factory = new JBDDProviderFactory();
	}

}
