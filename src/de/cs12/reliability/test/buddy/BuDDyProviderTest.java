package de.cs12.reliability.test.buddy;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDProviderTest;

/**
 * The {@code BuDDyProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@code BuDDy}.
 * 
 * @author lukasiewycz
 * 
 */
public class BuDDyProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new JBDDProviderFactory(Type.BUDDY);
	}

}
