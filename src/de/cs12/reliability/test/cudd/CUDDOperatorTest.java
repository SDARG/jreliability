package de.cs12.reliability.test.cudd;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDOperatorTest;

/**
 * The {@code CUDDOperatorTest} is a unit test operator class for the {@code
 * CUDDO} class.
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
		this.factory = new JBDDProviderFactory(Type.CUDD);
	}

}
