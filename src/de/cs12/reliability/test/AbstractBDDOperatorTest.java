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
package de.cs12.reliability.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code AbstractBDDOperatorTest} is the base class for tests of the
 * operators for the {@code BDD}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class AbstractBDDOperatorTest extends AbstractBDDTest {

	/**
	 * The used {@code BDDProvider}.
	 */
	protected BDDProvider<String> provider;

	/**
	 * Initialize the provider.
	 */
	@Before
	public void initProvider() {
		provider = factory.getProvider();
	}

	/**
	 * 
	 * Tests the {@code and} method on {@code 100} variables.
	 * 
	 */
	@Test
	public void testAndBig() {
		BDD<String> and = provider.one();

		for (int i = 0; i < 200; i++) {
			String var = "v" + i;
			and.andWith(provider.get(var));
		}

		Assert.assertFalse(and.isOne());
		Assert.assertFalse(and.isZero());
	}

	/**
	 * 
	 * Tests the {@code and} method on two variables.
	 * 
	 */
	@Test
	public void testAndTwoVariables() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		BDD<String> and = a.and(b);

		Assert.assertFalse(and.isOne());
		Assert.assertFalse(and.isZero());

		and.restrictWith(a);
		and.restrictWith(b);

		Assert.assertTrue(and.isOne());
	}

	/**
	 * Tests the {@code andWith} method on two variables.
	 * 
	 */
	@Test
	public void testAndWithTwoVariables() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		a.andWith(b);

		Assert.assertFalse(a.isOne());
		Assert.assertFalse(a.isZero());

		a.restrictWith(provider.get("a"));
		a.restrictWith(provider.get("b"));

		Assert.assertTrue(a.isOne());
	}

	/**
	 * Tests the {@code and} method on two variables and returning the result to
	 * the same object.
	 * 
	 */
	@Test
	public void testAndSameTwoVariables() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		a = a.and(b);

		Assert.assertFalse(a.isOne());
		Assert.assertFalse(a.isZero());

		a.restrictWith(provider.get("a"));
		a.restrictWith(provider.get("b"));

		Assert.assertTrue(a.isOne());
	}

	/**
	 * Tests the {@code or} method on two variables.
	 * 
	 */
	@Test
	public void testOrTwoVariables() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		BDD<String> or = a.or(b);

		Assert.assertFalse(or.isOne());
		Assert.assertFalse(or.isZero());

		or.restrictWith(a);

		Assert.assertTrue(or.isOne());
	}

	/**
	 * Tests the {@code orWith} method on two variables.
	 * 
	 */
	@Test
	public void testOrWithTwoVariables() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		a.orWith(b);

		Assert.assertFalse(a.isOne());
		Assert.assertFalse(a.isZero());

		a.restrictWith(provider.get("b"));

		Assert.assertTrue(a.isOne());
	}

	/**
	 * Tests the {@code or} method on two variables and returning the result to
	 * the same object.
	 * 
	 */
	@Test
	public void testOrSameTwoVariables() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		a = a.or(b);

		Assert.assertFalse(a.isOne());
		Assert.assertFalse(a.isZero());

		a.restrictWith(provider.get("a"));

		Assert.assertTrue(a.isOne());
	}

}
