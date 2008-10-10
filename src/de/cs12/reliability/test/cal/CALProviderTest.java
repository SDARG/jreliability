package de.cs12.reliability.test.cal;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDProviderTest;

/**
 * 
 * The {@code CALProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@link CAL}.
 * 
 * @author lukasiewycz
 * 
 */
public class CALProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new JBDDProviderFactory(Type.CAL);
	}

}
