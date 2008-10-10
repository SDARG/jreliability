package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.BuDDy;
import de.cs12.reliability.javabdd.BuDDyProviderFactory;

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
		this.factory = new BuDDyProviderFactory();
	}


}
