package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.JDD;
import de.cs12.reliability.javabdd.JDDProviderFactory;

/**
 * 
 * The {@code JDDProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@link JDD}.
 * 
 * @author lukasiewycz
 * 
 */
public class JDDProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new JDDProviderFactory();
	}

}
