/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.test.buddy;

import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.jreliability.test.AbstractBDDOperatorTest;

/**
 * The {@code BuDDyOperatorTest} is a unit test operator class for the {@code
 * BuDDy} class.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class BuDDyOperatorTest extends AbstractBDDOperatorTest {

	/**
	 * Constructs a {@code BuDDyOperatorTest} .
	 * 
	 */
	public BuDDyOperatorTest() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.test.AbstractBDDTest#init()
	 */
	@Override
	public void init() {
		this.factory = new JBDDProviderFactory(Type.BUDDY);
	}

}
