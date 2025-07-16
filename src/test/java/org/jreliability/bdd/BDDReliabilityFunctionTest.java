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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	@BeforeEach
	public void init() {
		BDDProviderFactory factory = new JBDDProviderFactory();
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
		Assertions.assertEquals(0.9048374, function.getY(10), 1.0E-5);
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

		Assertions.assertEquals(bdd, function.getBdd());

	}

	@Test
	public void testGetTransformer() {
		Transformer<String, ReliabilityFunction> transformer = new TestTransformer();
		BDDReliabilityFunction<String> function = new BDDReliabilityFunction<String>(provider.one(), transformer);

		Assertions.assertEquals(transformer, function.getTransformer());
	}

	@Test
	public void testIsProvidingService() {
		// Sensor 1 & 2 in parallel, sensor 3 in series
		String var1 = "sensor1";
		String var2 = "sensor2";
		String var3 = "sensor3";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		Term s3 = new LiteralTerm<>(var3);
		ORTerm or = new ORTerm();
		or.add(s1, s2);
		ANDTerm and = new ANDTerm();
		and.add(or, s3);

		BDDTTRF<String> ttrf = new BDDTTRF<String>(provider);
		BDD<String> bdd = ttrf.convertToBDD(and);

		BDDReliabilityFunction<String> function = new BDDReliabilityFunction<String>(bdd, new TestTransformer());

		Map<String, Boolean> failedComponents = new HashMap<String, Boolean>();

		// First sensor failure should return proper working system
		failedComponents.put(var1, false);
		Assertions.assertTrue(function.isProvidingService(failedComponents));

		// Second sensor failure should return failed system
		failedComponents.put(var2, false);
		Assertions.assertFalse(function.isProvidingService(failedComponents));

		// Second sensor back to life, system properly working
		failedComponents.put(var2, true);
		Assertions.assertTrue(function.isProvidingService(failedComponents));

		// Sensor 3 failure cannot be mitigated, system fails
		failedComponents.put(var3, false);
		Assertions.assertFalse(function.isProvidingService(failedComponents));
	}

}
