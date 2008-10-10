package de.cs12.reliability.test.buddy;

import de.cs12.reliability.javabdd.JBDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;
import de.cs12.reliability.test.AbstractBDDOperatorTest;

/**
 * The {@code BuDDyOperatorTest} is a unit test operator class for the {@link BuDDy} class.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class BuDDyOperatorTest extends AbstractBDDOperatorTest {


	/* (non-Javadoc)
	 * @see de.cs12.reliability.test.AbstractBDDTest#init()
	 */
	public void init() {
		this.factory = new JBDDProviderFactory(Type.BUDDY);
	}


}
