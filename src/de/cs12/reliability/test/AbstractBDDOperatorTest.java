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

		for (int i = 0; i < 2000; i++) {
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
