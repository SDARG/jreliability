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
package org.jreliability.bdd;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.AllPredicate;
import org.apache.commons.collections15.functors.EqualPredicate;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ConstantFailureFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@code BDDTTRFTest} tests the {@code BDDTTRF}.
 * 
 * @author reimann
 * 
 */
public class BDDTTRFTest {

	/**
	 * The used {@code BDDProvider}.
	 */
	protected BDDProvider<String> provider;

	/**
	 * Initialize the specific factory.
	 */
	@Before
	public void init() {
		BDDProviderFactory factory = new JBDDProviderFactory(Type.JAVABDD);
		provider = factory.getProvider();
	}

	@Test
	public void testConvertToBDDOrTerm() {
		String var1 = "sensor1";
		String var2 = "sensor2";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		ORTerm and = new ORTerm();
		and.add(s1, s2);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.convertToBDD(and);

		BDD<String> ref = provider.get(var1);
		BDD<String> b2 = provider.get(var2);
		ref.orWith(b2);

		Assert.assertEquals(result, ref);
	}

	@Test
	public void testConvertToBDDAndTerm() {
		String var1 = "sensor1";
		String var2 = "sensor2";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		ANDTerm and = new ANDTerm();
		and.add(s1, s2);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.convertToBDD(and);

		BDD<String> ref = provider.get(var1);
		BDD<String> b2 = provider.get(var2);
		ref.andWith(b2);

		Assert.assertEquals(result, ref);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testConvertToBDDExistsPredicate() {
		String var1 = "sensor1";
		String var2 = "sensor2";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		ANDTerm and = new ANDTerm();
		and.add(s1, s2);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		Predicate p2[] = { new EqualPredicate<>(var2) };
		Predicate<String> p = new AllPredicate<String>(p2);
		BDD<String> result = ttrf.convertToBDD(and, p);

		BDD<String> ref = provider.get(var1);

		Assert.assertEquals(result, ref);
	}

	@Test
	public void testConvertToBDDTrueTerm() {
		Term t = new TRUETerm();

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.convertToBDD(t);

		Assert.assertEquals(result, provider.one());
	}

	@Test
	public void testConvertToBDDFalseTerm() {
		Term t = new FALSETerm();

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.convertToBDD(t);

		Assert.assertEquals(result, provider.zero());
	}

	@Test
	public void testConvertToBDDNotTerm() {
		String var1 = "sensor1";
		Term s1 = new LiteralTerm<>(var1);
		NOTTerm and = new NOTTerm(s1);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.convertToBDD(and);

		BDD<String> ref = provider.get(var1).not();

		Assert.assertEquals(result, ref);
	}

	/**
	 * Tests the
	 * {@link BDDTTRF#convert(BDD, org.apache.commons.collections15.Transformer)}
	 */
	@Test
	public void testConvert() {
		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		ReliabilityFunction f = ttrf.convert(provider.one(), new Transformer<String, ReliabilityFunction>() {
			@Override
			public ReliabilityFunction transform(String input) {
				return new ConstantFailureFunction(0.5);
			}
		});
		Assert.assertEquals(f.getY(1.0), new ConstantFailureFunction(1.0).getY(1.0), 0.000001);
	}

	/**
	 * Tests the {@link BDDTTRF#convert(Term, Transformer)}
	 */
	@Test
	public void testConvertTerm() {
		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		ReliabilityFunction f = ttrf.convert(new LinearTerm(Comparator.LESSEQUAL, 1),
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assert.assertEquals(f.getY(1.0), new ConstantFailureFunction(1.0).getY(1.0), 0.000001);
	}

	@Test
	public void testTransformLinearGE() {
		Term literal = new LiteralTerm<>("a");
		LinearTerm t1 = new LinearTerm(Comparator.GREATEREQUAL, 1);
		t1.add(literal);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.transformLinear(t1);

		Assert.assertEquals(provider.get("a"), result);
	}

	@Test
	public void testTransformLinearEQ() {
		Term literal = new LiteralTerm<>("a");
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);
		t1.add(literal);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.transformLinear(t1);

		Assert.assertEquals(provider.get("a"), result);
	}

	@Test
	public void testTransformLinearConstantTrue() {
		LinearTerm t1 = new LinearTerm(Comparator.LESSEQUAL, 1);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.transformLinear(t1);

		Assert.assertEquals(provider.one(), result);
	}

	@Test
	public void testTransformLinearConstantFalse() {
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.transformLinear(t1);

		Assert.assertEquals(provider.zero(), result);
	}

	@Test
	public void testTransformWithLinear() {
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> result = ttrf.transform(t1);

		Assert.assertEquals(provider.zero(), result);
	}
	
	@Test
	public void testTransformLinearUnsupportedComparator() {

		for (Comparator comparator : Comparator.values()) {
			LinearTerm t = new LinearTerm(comparator, 1);
			BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
			BDD<String> result = ttrf.transformLinear(t);

			Assert.assertNotNull(comparator + " in LinearTerm not supported for BDD conversion.", result);

		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTransformWithUnknownTerm() {
		Term t = new Term() {
		};

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		ttrf.transform(t);
	}
}
