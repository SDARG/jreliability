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

package org.jreliability.bdd.jbdd;

import org.jreliability.bdd.AbstractBDDProviderTest;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.javabdd.JBDD;
import org.jreliability.bdd.javabdd.JBDDProvider;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sf.javabdd.JFactory;

/**
 * 
 * The {@link JBDDProviderTest} is the {@link AbstractBDDProviderTest} for the
 * {@link JBDD}.
 * 
 * @author lukasiewycz
 * 
 */
public class JBDDProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.test.AbstractBDDProviderTest#init()
	 */
	@Override
	@BeforeEach
	public void init() {
		// Type.JAVABDD should be the standard BDD Factory
		this.factory = new JBDDProviderFactory();
		JBDDProvider<Object> provider = (JBDDProvider<Object>) factory.getProvider();
		Assertions.assertTrue(provider.getFactory() instanceof JFactory,
				"JavaBDD should be the standard JBDDProviderFactory.");
	}

	@Test
	public void testVariableGrowthRate() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			BDDProvider<String> provider = new JBDDProvider<>(Type.JAVABDD, 10, Integer.MAX_VALUE / 10, 20000);
			for (int i = 0; i < 200; i++) {
				@SuppressWarnings("unused")
				BDD<String> a = provider.get("" + i);
			}
		});
	}

	@Test
	public void testVariableNotFound() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			BDDProvider<String> provider = new JBDDProvider<>(Type.JAVABDD, 10, Integer.MAX_VALUE, 20000);
			for (int i = 0; i < 20; i++) {
				@SuppressWarnings("unused")
				BDD<String> a = provider.get("" + i);
			}
		});
	}

}
