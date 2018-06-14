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

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link BDDTTRFTest} tests the {@link BDDReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public class BDDReliabilityFunctionTest {

	/**
	 * The used {@link BDDProvider}.
	 */
	protected BDDProvider<String> provider;

	class TestTransformer implements Transformer<String, ReliabilityFunction> {

		protected ReliabilityFunction function = new ExponentialReliabilityFunction(0.005);

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jreliability.common.Transformer#transform(java.lang.Object)
		 */
		public ReliabilityFunction transform(String a) {
			return function;
		}

	}

	/**
	 * Initialize the specific factory.
	 */
	@Before
	public void init() {
		BDDProviderFactory factory = new JBDDProviderFactory(Type.JAVABDD);
		provider = factory.getProvider();
	}

	@Test
	public void testGetY() {
		String var1 = "sensor1";
		String var2 = "sensor2";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		ANDTerm and = new ANDTerm();
		and.add(s1, s2);

		BDDTTRF<String> ttrf = new BDDTTRF<String>(provider);
		BDD<String> bdd = ttrf.convertToBDD(and);

		BDDReliabilityFunction<String> function = new BDDReliabilityFunction<String>(bdd, new TestTransformer());

		/* http://www.wolframalpha.com/input/?i=(e%5E(-0.005*10))%5E2 */
		Assert.assertEquals(0.9048374, function.getY(10), 1.0E-5);
	}

	@Test
	public void testGetBDD() {
		String var1 = "sensor1";
		Term s1 = new LiteralTerm<>(var1);
		ANDTerm and = new ANDTerm();
		and.add(s1);

		BDDTTRF<String> ttrf = new BDDTTRF<>(provider);
		BDD<String> bdd = ttrf.convertToBDD(and);

		BDDReliabilityFunction<String> function = new BDDReliabilityFunction<String>(bdd, new TestTransformer());

		Assert.assertEquals(bdd, function.getBdd());

	}

	@Test
	public void testGetTransformer() {
		Transformer<String, ReliabilityFunction> transformer = new TestTransformer();
		BDDReliabilityFunction<String> function = new BDDReliabilityFunction<String>(provider.one(), transformer);

		Assert.assertEquals(transformer, function.getTransformer());
	}

}
