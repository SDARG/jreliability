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

package org.jreliability.bdd;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDDConstraint.Literal;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link AbstractBDDOperatorTest} is the base class for tests of the
 * operators for the {@link BDD}.
 * 
 * @author lukasiewycz, reimann
 * 
 */
public abstract class AbstractBDDOperatorTest extends AbstractBDDTest {

	/**
	 * The used {@link BDDProvider}.
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
	 * Tests the {@link and} method on {@link 100} variables.
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
	 * Tests the {@link and} method on two variables.
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
	 * Tests the {@link andWith} method on two variables.
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
	 * Tests the {@link andWith} method with a simple type.
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
	 * Tests the {@link andWith} method with a collection.
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
	 * Tests the {@link nodeCount} method.
	 * 
	 */
	@Test
	public void testNodeCount() {
		BDD<String> a = provider.get("a");

		a.andWith("b");

		Assert.assertEquals(2, a.nodeCount());
	}

	/**
	 * Tests the {@link and} method on two variables and returning the result to
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
	 * Tests the {@link or} method on two variables.
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
	 * Tests the {@link orWith} method on two variables.
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
	 * Tests the {@link orWith} method with a collection.
	 * 
	 */
	@Test
	public void testOrWithCollection() {
		BDD<String> a1 = provider.get("a");
		BDD<String> a2 = provider.get("a");
		BDD<String> b = provider.get("b");
		a1.orWith(b);
		a2.orWith(Collections.singleton("b"));

		Assert.assertEquals(a1, a2);
	}

	/**
	 * Tests the {@link orWith} method with an object.
	 * 
	 */
	@Test
	public void testOrWithObject() {
		BDD<String> a1 = provider.get("a");
		BDD<String> a2 = provider.get("a");
		BDD<String> b = provider.get("b");
		a1.orWith(b);
		a2.orWith("b");

		Assert.assertEquals(a1, a2);
	}

	/**
	 * Tests the {@link or} method on two variables and returning the result to
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
		Set<String> variables = new HashSet<String>();
		variables.add("a");
		variables.add("b");
		variables.add("c");
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> bdd = provider.get("a");
		bdd = bdd.or(provider.get("b"));
		bdd = bdd.or(provider.get("c"));
		Assert.assertEquals(BDDs.getVariables(bdd), variables);
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
		Assert.assertEquals(0.7, BDDs.calculateTop(bdd, t), 0.00001);
	}

	/**
	 * Tests the {@link BDDs#getNodes(BDD)} method.
	 */
	@Test
	public void testCalculateTopNot() {
		BDDProvider<String> provider = factory.getProvider();

		String var = "a";
		BDD<String> bdd = provider.get(var).not();
		Transformer<String, Double> t = new Transformer<String, Double>() {

			@Override
			public Double transform(String input) {
				return 0.7;
			}
		};
		Assert.assertEquals(0.3, BDDs.calculateTop(bdd, t), 0.00001);
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
		Assert.assertEquals(1.0, BDDs.calculateTop(bdd, t), 0.00001);
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
		Assert.assertEquals(0.49, BDDs.calculateTop(bdd, t), 0.00001);
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
		Assert.assertEquals(0.21, BDDs.calculateTop(bdd, t), 0.00001);
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
		Assert.assertEquals(0.0, BDDs.calculateTop(bdd, t), 0.00001);
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
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.GREATEREQUAL, 2,
				provider);

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
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.GREATER, 2, provider);

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
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.LESS, 1, provider);

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
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.LESSEQUAL, 0,
				provider);

		BDD<String> ref1 = a.not().and(b.not()).and(c.not());
		Assert.assertEquals(test, ref1);
	}

	/**
	 * Tests the
	 * {@link BDDs#getBDD(java.util.List, java.util.List, org.jreliability.booleanfunction.common.LinearTerm.Comparator, int)}
	 * method with = operator.
	 */
	@Test
	public void testGetBDDEqual() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> c = provider.get("c");
		BDD<String> test = BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b, c), Comparator.EQUAL, 1, provider);

		BDD<String> ref1 = a.and(b.not()).and(c.not());
		BDD<String> ref2 = a.not().and(b).and(c.not());
		BDD<String> ref3 = a.not().and(b.not()).and(c);
		ref1.orWith(ref2);
		ref1.orWith(ref3);
		Assert.assertEquals(test, ref1);
	}

	/**
	 * Tests the
	 * {@link BDDs#getBDD(java.util.List, java.util.List, org.jreliability.booleanfunction.common.LinearTerm.Comparator, int)}
	 * method with not the same number of variables and coefficients.
	 */
	@Test(expected = AssertionError.class)
	public void testGetBDDWrongNumberOfArgs() {
		BDDProvider<String> provider = factory.getProvider();

		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDDs.getBDD(Arrays.asList(1, 1, 1), Arrays.asList(a, b), Comparator.EQUAL, 1, provider);
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
	 * Tests the {@link exist} method.
	 * 
	 */
	@Test
	public void testExist() {
		String var = "b";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var);
		a.andWith(b);

		BDD<String> result = a.exist(var);
		Assert.assertEquals(result, provider.get("a"));
	}

	/**
	 * Tests the {@link forAll} method.
	 * 
	 */
	@Test
	public void testForAll() {
		String var = "b";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var);
		a.andWith(b);

		BDD<String> result = a.forAll(var);
		Assert.assertEquals(result, provider.zero());
	}

	/**
	 * Tests the {@link restrict} method.
	 * 
	 */
	@Test
	public void testRestrict() {
		String var = "b";
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get(var);
		a.andWith(b);

		BDD<String> result = a.restrict(provider.get(var));
		Assert.assertEquals(provider.get("a"), result);
	}

	/**
	 * Tests the {@link imp} method.
	 * 
	 */
	@Test
	public void testImp() {
		String var1 = "a";
		String var2 = "b";
		BDD<String> ref = provider.get(var1);
		BDD<String> b = provider.get(var2);
		ref.andWith(b);
		ref.orWith(provider.get(var1).not());

		BDD<String> result = provider.get(var1).imp(provider.get(var2));

		Assert.assertEquals(ref, result);
	}

	/**
	 * Tests the {@link impWith} method.
	 * 
	 */
	@Test
	public void testImpWith() {
		String var1 = "a";
		String var2 = "b";
		BDD<String> ref = provider.get(var1);
		BDD<String> b = provider.get(var2);
		ref.andWith(b);
		ref.orWith(provider.get(var1).not());

		BDD<String> result = provider.get(var1);
		result.impWith(var2);

		Assert.assertEquals(ref, result);
	}

	/**
	 * Tests the {@link impWith} method with a BDD.
	 * 
	 */
	@Test
	public void testImpWithBdd() {
		String var1 = "a";
		String var2 = "b";
		BDD<String> ref = provider.get(var1);
		BDD<String> b = provider.get(var2);
		ref.andWith(b);
		ref.orWith(provider.get(var1).not());

		BDD<String> result = provider.get(var1);
		result.impWith(provider.get(var2));

		Assert.assertEquals(ref, result);
	}

	/**
	 * Tests the {@link xor} method.
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
	 * Tests the {@link xorWith} method.
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
	 * Tests the {@link xorWith} method.
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
	 * Tests the {@link getProvider} method.
	 * 
	 */
	@Test
	public void testGetProvider() {
		BDD<String> a = provider.get("a");

		Assert.assertEquals(provider, a.getProvider());
	}

	/**
	 * Tests the {@link replace} method.
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
	 * Tests the {@link replaceWith} method.
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

	/**
	 * Tests the {@link sat} method.
	 * 
	 */
	@Test
	public void testSat() {
		BDD<String> result = provider.get("a");
		result.sat();

		BDD<String> ref = provider.get("a");
		Assert.assertEquals(ref, result);
	}

	/**
	 * Tests the {@link getVariables} method.
	 * 
	 */
	@Test
	public void testGetVariablesSat() {
		BDD<String> result = provider.get("a");
		Set<String> vars = result.getVariables();

		Assert.assertEquals(1, vars.size());
		Assert.assertEquals("a", vars.iterator().next());
	}

	/**
	 * Tests the {@link toString} method.
	 * 
	 */
	@Test
	public void testToString() {
		BDD<String> bdd = provider.get("a");
		String result = bdd.toString();

		Assert.assertEquals("<0:1>", result);
	}

	/**
	 * Test the {@link trim} method. {code 2a &lte; 1} is trimmed to {code 1a
	 * &lte; 1}.
	 */
	@Test
	public void testTrim() {
		BDD<String> bdd = provider.get("a");
		BDDConstraint<String> c = new BDDConstraint<>(1, Arrays.asList(new Literal<>(2, bdd)));
		List<Literal<String>> lhs = c.getLhs();

		Assert.assertEquals(1, lhs.size());
		Iterator<Literal<String>> iter = lhs.iterator();
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(1, iter.next().getCoefficient());
		Assert.assertFalse(iter.hasNext());
	}

	/**
	 * Test the {@link checkAndAddVariable} method. {code a + a &lte; 2} is
	 * converted to {code a &lte; 1}.
	 */
	@Test
	public void testCheckAndAddVariable() {
		BDD<String> bdd = provider.get("a");
		BDDConstraint<String> c = new BDDConstraint<>(2, Arrays.asList(new Literal<>(1, bdd), new Literal<>(1, bdd)));
		List<Literal<String>> lhs = c.getLhs();

		Assert.assertEquals(1, lhs.size());
		Iterator<Literal<String>> iter = lhs.iterator();
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(1, iter.next().getCoefficient());
		Assert.assertFalse(iter.hasNext());
	}
}
