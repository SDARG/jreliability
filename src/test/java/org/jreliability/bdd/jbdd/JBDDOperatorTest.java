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

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.jreliability.bdd.AbstractBDDOperatorTest;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.javabdd.JBDD;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@link JBDDOperatorTest} is a unit test operator class for the
 * {@link JBDD} class.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class JBDDOperatorTest extends AbstractBDDOperatorTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.test.AbstractBDDTest#init()
	 */
	@Override
	@BeforeEach
	public void init() {
		this.factory = new JBDDProviderFactory();
		initProvider();
	}

	/**
	 * Tests the {@link allSat} method.
	 * 
	 */
	@Test
	public void testAllSat() {
		BDD<String> bdd = provider.get("a");
		Iterator<BDD<String>> it = bdd.allsat();
		BDD<String> ref = provider.get("a");

		Assertions.assertTrue(it.hasNext());
		Assertions.assertEquals(ref, it.next());
		Assertions.assertFalse(it.hasNext());
	}

	/**
	 * Tests the {@link allSat} method.
	 * 
	 */
	@Test
	public void testAllSatIteratorNoNext() {
		Assertions.assertThrows(AssertionError.class, () -> {
			BDD<String> bdd = provider.get("a");
			Iterator<BDD<String>> it = bdd.allsat();

			Assertions.assertTrue(it.hasNext());
			it.next();
			Assertions.assertFalse(it.hasNext());
			it.next();
		});
	}

	/**
	 * Tests the {@link allSat} method with a negated variable.
	 * 
	 */
	@Test
	public void testAllSatIteratorNegatedVar() {
		BDD<String> bdd = provider.get("a").not();
		Iterator<BDD<String>> it = bdd.allsat();

		BDD<String> ref = provider.get("a").not();

		Assertions.assertTrue(it.hasNext());
		Assertions.assertEquals(ref, it.next());
		Assertions.assertFalse(it.hasNext());
	}

	/**
	 * Tests the {@link remove} method.
	 * 
	 */
	@Test
	public void testAllSatIteratorRemove() {
		BDD<String> bdd = provider.get("a").not();
		Iterator<BDD<String>> it = bdd.allsat();

		Assertions.assertTrue(it.hasNext());
		it.next();
		assertThrows(UnsupportedOperationException.class, () -> it.remove());
	}
}
