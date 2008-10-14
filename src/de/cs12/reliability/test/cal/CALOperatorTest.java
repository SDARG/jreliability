package de.cs12.reliability.test.cal;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDOperatorTest;

/**
 * The {@code CALOperatorTest} is a unit test operator class for the {@code CAL}
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
		this.factory = new JBDDProviderFactory(Type.CAL);
	}

}
