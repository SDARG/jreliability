/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd.jbdd;

import org.jreliability.bdd.AbstractBDDProviderTest;
import org.jreliability.bdd.javabdd.JBDD;
import org.jreliability.bdd.javabdd.JBDDProvider;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.junit.Assert;

import net.sf.javabdd.JFactory;

/**
 * 
 * The {@code JBDDProviderTest} is the {@code AbstractBDDProviderTest} for the {@link JBDD}.
 * 
 * @author lukasiewycz
 * 
 */
public class JBDDProviderTest extends AbstractBDDProviderTest {

	/**
	 * Constructs a {@code JBDDProviderTest}.
	 * 
	 */
	public JBDDProviderTest() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.test.AbstractBDDProviderTest#init()
	 */
	@Override
	public void init() {
		// Type.JAVABDD should be the standard BDD Factory
		this.factory = new JBDDProviderFactory();
		JBDDProvider<Object> provider = (JBDDProvider) factory.getProvider();
		Assert.assertTrue("JavaBDD should be the standard JBDDProviderFactory.", provider.getFactory() instanceof JFactory);
	}

}
