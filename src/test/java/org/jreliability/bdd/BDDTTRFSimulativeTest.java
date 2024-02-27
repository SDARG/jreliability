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

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.AllPredicate;
import org.apache.commons.collections15.functors.EqualPredicate;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.SampledReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@link BDDTTRFTest} tests the {@link BDDTTRFSimulative}.
 * 
 * @author glass
 * 
 */
public class BDDTTRFSimulativeTest {

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
		@Override
		public ReliabilityFunction transform(String a) {
			return function;
		}

	}

	/**
	 * Initialize the specific factory.
	 */
	@BeforeEach
	public void init() {
		BDDProviderFactory factory = new JBDDProviderFactory(Type.JAVABDD);
		provider = factory.getProvider();
	}

	@Test
	public void testConvertStandardSamples() {
		String var1 = "sensor1";
		String var2 = "sensor2";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		ANDTerm and = new ANDTerm();
		and.add(s1, s2);

		BDDTTRFSimulative<String> ttrf = new BDDTTRFSimulative<>(provider);
		SampledReliabilityFunction function = (SampledReliabilityFunction) ttrf.convert(and, new TestTransformer());

		Assertions.assertEquals(5000, function.getSamples().size(), 1.0E-5);
	}

	@Test
	public void testConvertGivenSamples() {
		String var1 = "sensor1";
		String var2 = "sensor2";
		Term s1 = new LiteralTerm<>(var1);
		Term s2 = new LiteralTerm<>(var2);
		ORTerm or = new ORTerm();
		or.add(s1, s2);

		BDDTTRFSimulative<String> ttrf = new BDDTTRFSimulative<>(provider, 0.005);
		int samples = 4000;
		SampledReliabilityFunction function = (SampledReliabilityFunction) ttrf.convert(or, new TestTransformer(),
				samples);

		Assertions.assertEquals(samples, function.getSamples().size(), 1.0E-5);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testConvertOneBDD() {
		String var1 = "sensor1";
		Term s1 = new LiteralTerm<>(var1);
		ANDTerm and = new ANDTerm();
		and.add(s1);
		Predicate p1[] = { new EqualPredicate<>(var1) };
		Predicate<String> p = new AllPredicate<String>(p1);

		BDDTTRFSimulative<String> ttrf = new BDDTTRFSimulative<>(provider);
		int samples = 10;
		SampledReliabilityFunction function = (SampledReliabilityFunction) ttrf.convert(and, new TestTransformer(), p,
				samples);
		for (int i = 0; i < samples; i++) {
			Assertions.assertEquals(0.0, function.getSamples().get(i), 1.0E-5);
		}
	}

}
