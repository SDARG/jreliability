package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.JDD;
import de.cs12.reliability.javabdd.JDDProviderFactory;

/**
 * The {@code JDDOperatorTest} is a unit test operator class for the {@link JDD}
 * class.
 * 
 * @author lukasiewycz
 * 
 */
public class JDDOperatorTest extends AbstractBDDOperatorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDTest#init()
	 */
	public void init() {
		this.factory = new JDDProviderFactory();
	}

}
