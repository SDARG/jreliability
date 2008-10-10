package de.cs12.reliability.test.jbdd;

import de.cs12.reliability.javabdd.JBDD;
import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDProviderTest;

/**
 * 
 * The {@code JBDDProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@link JBDD}.
 * 
 * @author lukasiewycz
 * 
 */
public class JBDDProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new JBDDProviderFactory(Type.JAVABDD);
	}

}