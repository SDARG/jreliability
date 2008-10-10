package de.cs12.reliability.test.jdd;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDOperatorTest;

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
		this.factory = new JBDDProviderFactory(Type.JDD);
	}

}
