package de.cs12.reliability.test.jdd;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDProviderTest;

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
		this.factory = new JBDDProviderFactory(Type.JDD);
	}

}
