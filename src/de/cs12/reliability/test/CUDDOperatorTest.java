package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.CUDDProviderFactory;

/**
 * The {@code CUDDOperatorTest} is a unit test operator class for the
 * {@link CUDDO} class.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class CUDDOperatorTest extends AbstractBDDOperatorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDTest#init()
	 */
	public void init() {
		this.factory = new CUDDProviderFactory();
	}

}
