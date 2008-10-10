package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.CAL;
import de.cs12.reliability.javabdd.CALProviderFactory;

/**
 * The {@code CALOperatorTest} is a unit test operator class for the {@link CAL}
 * class.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class CALOperatorTest extends AbstractBDDOperatorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDTest#init()
	 */
	public void init() {
		this.factory = new CALProviderFactory();
	}

}
