/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.bdd.jdd;

import org.jreliability.bdd.AbstractBDDOperatorTest;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.junit.Test;

import net.sf.javabdd.BDDException;

/**
 * The {@link JDDOperatorTest} is a unit test operator class for the {@link JDD}
 * class.
 * 
 * @author lukasiewycz
 * 
 */
public class JDDOperatorTest extends AbstractBDDOperatorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.test.AbstractBDDTest#init()
	 */
	@Override
	public void init() {
		this.factory = new JBDDProviderFactory(Type.JDD);
	}

	/**
	 * Tests the {@link allSat} method.
	 * 
	 */
	@Test(expected = BDDException.class)
	public void testAllSat() {
		BDD<String> bdd = provider.get("a");
		bdd.allsat();
	}
}
