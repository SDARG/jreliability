/**
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@code AbstractBDDOperatorTest} is the base class for tests of the
 * operators for the {@code BDD}.
 * 
 * @author lukasiewycz, reimann
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
	 * Tests the {@code andWith} method with a simple type.
	 * 
	 */
	@Test
	public void testAndWithString() {
		BDD<String> a = provider.get("a");

		a.andWith("b");

		Assert.assertFalse(a.isOne());
		Assert.assertFalse(a.isZero());

		a.restrictWith(provider.get("a"));
		a.restrictWith(provider.get("b"));

		Assert.assertTrue(a.isOne());
	}

	/**
	 * Tests the {@code andWith} method with a collection.
	 * 
	 */
	@Test
	public void testAndWithCollection() {
		BDD<String> a = provider.get("a");

		a.andWith(Collections.singleton("b"));

		Assert.assertFalse(a.isOne());
		Assert.assertFalse(a.isZero());

		a.restrictWith(provider.get("a"));
		a.restrictWith(provider.get("b"));

		Assert.assertTrue(a.isOne());
	}

	/**
	 * Tests the {@code nodeCount} method.
	 * 
	 */
	@Test
	public void testNodeCount() {
		BDD<String> a = provider.get("a");

		a.andWith("b");

		Assert.assertEquals(2, a.nodeCount());
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

	/**
	 * Tests the {@link BDDs#getVariables(BDD)} method.
	 */
	@Test
	public void testGetVariables() {
		BDDProvider<String> provider = factory.getProvider();

		String var = "a";
		BDD<String> bdd = provider.get(var);
		Assert.assertEquals(BDDs.getVariables(bdd), Collections.singleton(var));
	}

	/**
	 * Tests the {@link BDDs#getNodes(Object, BDD)} method.
	 */
	@Test
	public void testGetNodes() {
		BDDProvider<String> provider = factory.getProvider();

		String var = "a";
		BDD<String> bdd = provider.get(var);
		Assert.assertEquals(BDDs.getNodes(var, bdd), Collections.singleton(bdd));
	}

	/**
	 * Tests the {@link BDDs#getNodes(BDD)} method.
	 */
	@Test
	public void testCalculateTop() {
		BDDProvider<String> provider = factory.getProvider();

		String var = "a";
		BDD<String> bdd = provider.get(var);
		Transformer<String, Double> t = new Transformer<String, Double>() {

			@Override
			public Double transform(String input) {
				return 0.7;
			}
		};
		Assert.assertEquals(BDDs.calculateTop(bdd, t), 0.7, 0.00001);
	}

	/**
	 * Tests the {@link BDDs#getNodes(BDD)} method with a one terminal.
	 */
	@Test
	public void testCalculateTopOne() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> bdd = provider.one();
		Transformer<String, Double> t = new Transformer<String, Double>() {
			@Override
			public Double transform(String input) {
				return 0.7;
			}
		};
		Assert.assertEquals(BDDs.calculateTop(bdd, t), 1.0, 0.00001);
	}

	/**
	 * Tests the {@link BDDs#getNodes(BDD)} method with a one terminal.
	 */
	@Test
	public void testCalculate2Top() {
		BDDProvider<String> provider = factory.getProvider();

		String var = "a";
		String var2 = "b";
		BDD<String> bdd = provider.get(var).and(provider.get(var2));
		Transformer<String, Double> t = new Transformer<String, Double>() {

			@Override
			public Double transform(String input) {
				return 0.7;
			}
		};
		Assert.assertEquals(BDDs.calculateTop(bdd, t), 0.49, 0.00001);
	}

	/**
	 * Tests the {@link BDDs#getNodes(BDD)} method with a one terminal.
	 */
	@Test
	public void testCalculate2TopNot() {
		BDDProvider<String> provider = factory.getProvider();

		String var = "a";
		String var2 = "b";
		BDD<String> bdd = provider.get(var).not().and(provider.get(var2));
		Transformer<String, Double> t = new Transformer<String, Double>() {

			@Override
			public Double transform(String input) {
				return 0.7;
			}
		};
		Assert.assertEquals(BDDs.calculateTop(bdd, t), 0.21, 0.00001);
	}

	/**
	 * Tests the {@link BDDs#getNodes(BDD)} method with zero terminal.
	 */
	@Test
	public void testCalculateTopZero() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> bdd = provider.zero();
		Transformer<String, Double> t = new Transformer<String, Double>() {
			@Override
			public Double transform(String input) {
				return 0.7;
			}
		};
		Assert.assertEquals(BDDs.calculateTop(bdd, t), 0.0, 0.00001);
	}

	/**
	 * Tests the
	 * {@link BDDs#getBDD(java.util.List, java.util.List, org.jreliability.booleanfunction.common.LinearTerm.Comparator, int)}
	 * method with &ge; operator.
	 */
	@Test
	public void testGetBDDGreaterEqual() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> c = provider.get("c");
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.GREATEREQUAL, 2);

		BDD<String> ref1 = a.and(b);
		BDD<String> ref2 = b.and(c);
		BDD<String> ref3 = a.and(c);
		ref1.orWith(ref2);
		ref1.orWith(ref3);
		Assert.assertEquals(test, ref1);
	}

	/**
	 * Tests the
	 * {@link BDDs#getBDD(java.util.List, java.util.List, org.jreliability.booleanfunction.common.LinearTerm.Comparator, int)}
	 * method with &gt; operator.
	 */
	@Test
	public void testGetBDDGreater() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> c = provider.get("c");
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.GREATER, 2);

		BDD<String> ref1 = a.and(b).and(c);
		Assert.assertEquals(test, ref1);
	}

	/**
	 * Tests the
	 * {@link BDDs#getBDD(java.util.List, java.util.List, org.jreliability.booleanfunction.common.LinearTerm.Comparator, int)}
	 * method with &lt; operator.
	 */
	@Test
	public void testGetBDDLess() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> c = provider.get("c");
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.LESS, 1);

		BDD<String> ref1 = a.not().and(b.not()).and(c.not());
		Assert.assertEquals(test, ref1);
	}

	/**
	 * Tests the
	 * {@link BDDs#getBDD(java.util.List, java.util.List, org.jreliability.booleanfunction.common.LinearTerm.Comparator, int)}
	 * method with &lt; operator.
	 */
	@Test
	public void testGetBDDLessEqual() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> c = provider.get("c");
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.LESSEQUAL, 0);

		BDD<String> ref1 = a.not().and(b.not()).and(c.not());
		Assert.assertEquals(test, ref1);
	}

	/**
	 * Tests the {@link BDDs#toDot(BDD)} method.
	 */
	@Test
	public void testToDot() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> c = provider.get("c");

		BDD<String> ref1 = a.not().and(b.not()).and(c.not());
		String dot = BDDs.toDot(ref1);
		Assert.assertTrue(!dot.isEmpty());
	}

	/**
	 * Tests the {@code andWith} method on two variables.
	 * 
	 */
	@Test
	public void testExist() {
		String var = "b";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var);
		a.andWith(b);

		BDD<String> result = a.exist(var);
		String dot = BDDs.toDot(result);
		System.out.println(dot);
		Assert.assertEquals(result, provider.get("a"));
	}

	/**
	 * Tests the {@code xor} method.
	 * 
	 */
	@Test
	public void testXor() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> result = a.xor(b);

		BDD<String> ref = a.and(b.not());
		ref.orWith(b.and(a.not()));

		Assert.assertEquals(ref, result);
	}

	/**
	 * Tests the {@code xorWith} method.
	 * 
	 */
	@Test
	public void testXorWith() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");

		BDD<String> ref = a.and(b.not());
		ref.orWith(b.and(a.not()));

		a.xorWith(b);

		Assert.assertEquals(ref, a);
	}

	/**
	 * Tests the {@code xorWith} method.
	 * 
	 */
	@Test
	public void testXorWithCollection() {
		String var = "b";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var);

		BDD<String> ref = a.and(b.not());
		ref.orWith(b.and(a.not()));

		a.xorWith(var);

		Assert.assertEquals(ref, a);
	}

	/**
	 * Tests the {@code replace} method.
	 * 
	 */
	@Test
	public void testReplace() {
		String var1 = "b";
		String var2 = "c";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var1);
		BDD<String> c = provider.get(var2);

		BDD<String> ref = a.and(c);

		BDD<String> bdd = a.and(b);
		BDD<String> result = bdd.replace(var1, var2);

		Assert.assertEquals(ref, result);
	}

	/**
	 * Tests the {@code replaceWith} method.
	 * 
	 */
	@Test
	public void testReplaceWith() {
		String var1 = "b";
		String var2 = "c";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var1);
		BDD<String> c = provider.get(var2);

		BDD<String> ref = a.and(c);

		BDD<String> result = a.and(b);
		result.replaceWith(var1, var2);

		Assert.assertEquals(ref, result);
	}
}
