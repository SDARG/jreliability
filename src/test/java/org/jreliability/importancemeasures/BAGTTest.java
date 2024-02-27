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

package org.jreliability.importancemeasures;

import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.testsystems.TCNCSystem;
import org.jreliability.testsystems.TINCSystem;
import org.jreliability.testsystems.TMR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BAGTTest {
	protected final double TEST_DELTA = 0.000001;

	private BAGT<String> setupCoherentTestSystem() {
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), new ExponentialReliabilityFunction(0.02),

				new ExponentialReliabilityFunction(0.03));
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		return new BAGT<>(bdd, system.getTransformer());
	}

	@Test
	public void testCoherentSystemPlus() {
		BAGT<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS);

		Assertions.assertEquals(18.33320971180894, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(63.333959017207775, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(71.66672472239378, results.get("component3"), TEST_DELTA);
	}

	@Test
	public void testCoherentSystemPlusNormalized() {
		BAGT<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS_NORMALIZED);

		Assertions.assertEquals(0.40740296235326423, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(1.4074154458916484, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(1.5925872453884808, results.get("component3"), TEST_DELTA);
	}

	@Test
	public void testCoherentSystemMinus() {
		BAGT<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS);

		Assertions.assertEquals(25.00660198183078, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(20.005272656749337, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(11.670674336727288, results.get("component3"), TEST_DELTA);
	}

	@Test
	public void testCoherentSystemMinusNormalized() {
		BAGT<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS_NORMALIZED);

		Assertions.assertEquals(0.5556999503051907, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(0.44455976182908424, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(0.25934723772784307, results.get("component3"), TEST_DELTA);
	}

	private BAGT<String> setupNonCoherentTestSystem() {
		TCNCSystem system = new TCNCSystem();

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());

		return new BAGT<>(bdd, system.getTransformer());
	}

	@Test
	public void testNonCoherentSystemMinus() {
		BAGT<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS);

		Assertions.assertEquals(74.99620399721364, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(19.974502571796336, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(4.989869989110801, results.get("component3"), TEST_DELTA);
	}

	@Test
	public void testNonCoherentSystemMinusNormalized() {
		BAGT<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS_NORMALIZED);

		Assertions.assertEquals(0.7895185824205213, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(0.21028052240651565, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(0.05253059315391258, results.get("component3"), TEST_DELTA);
	}

	/* TODO: Fix BAGT infinite run time to enable complete testing */
//	@Test
//	public void testNonCoherentSystemPlus() {
//		BAGT<String> im = setupNonCoherentTestSystem();
//		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS);
//		
//		Assertions.assertEquals(Double.POSITIVE_INFINITY, results.get("component1"), TEST_DELTA);
//		Assertions.assertEquals(13.46, results.get("component2"), TEST_DELTA);
//		Assertions.assertEquals(21.08, results.get("component3"), TEST_DELTA);
//	}
//	@Test
//	public void testNonCoherentSystemPlusNormalized() {
//		BAGT<String> im = setupNonCoherentTestSystem();
//		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS_NORMALIZED);
//		
//		Assertions.assertEquals(Double.POSITIVE_INFINITY, results.get("component1"), TEST_DELTA);
//		Assertions.assertEquals(?, results.get("component2"), TEST_DELTA);
//		Assertions.assertEquals(?, results.get("component3"), TEST_DELTA);
//	}

	private BAGT<String> setupTimeInconsistentNonCoherentTestSystem() {
		TINCSystem system = new TINCSystem();

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());

		return new BAGT<>(bdd, system.getTransformer());
	}

	@Test
	public void testTimeInconsistentNonCoherentSystemMinus() {
		BAGT<String> im = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS);

		Assertions.assertEquals(6.846974401650439, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(14.276804475931169, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(0.5717604268902505, results.get("component3"), TEST_DELTA);
		Assertions.assertEquals(14.276804475931169, results.get("component4"), TEST_DELTA);
		Assertions.assertEquals(0.45841530680635856, results.get("component5"), TEST_DELTA);
	}

	@Test
	public void testTimeInconsistentNonCoherentSystemMinusNormalized() {
		BAGT<String> im = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS_NORMALIZED);

		Assertions.assertEquals(0.3078814972787515, results.get("component1"), TEST_DELTA);
		Assertions.assertEquals(0.641971720727645, results.get("component2"), TEST_DELTA);
		Assertions.assertEquals(0.025709816626928825, results.get("component3"), TEST_DELTA);
		Assertions.assertEquals(0.641971720727645, results.get("component4"), TEST_DELTA);
		Assertions.assertEquals(0.02061313256860128, results.get("component5"), TEST_DELTA);
	}

	/* TODO: Fix BAGT infinite run time to enable complete testing */
//	@Test
//	public void testTimeInconsistentNonCoherentSystemPlus() {
//		BAGT<String> im = setupTimeInconsistentNonCoherentTestSystem();
//		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS);
//		
//		results = im.calculate(BAGT.Variant.PLUS);
//		Assertions.assertEquals(Double.POSITIVE_INFINITY, results.get("component1"), TEST_DELTA);
//		Assertions.assertEquals(6, results.get("component2"), TEST_DELTA);
//		Assertions.assertEquals(2.5, results.get("component3"), TEST_DELTA);
//		Assertions.assertEquals(6, results.get("component4"), TEST_DELTA);
//		Assertions.assertEquals(Double.POSITIVE_INFINITY, results.get("component5"), TEST_DELTA);
//	}
//	
//	@Test
//	public void testTimeInconsistentNonCoherentSystemPlusNormalized() {
//		BAGT<String> im = setupTimeInconsistentNonCoherentTestSystem();
//		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS_NORMALIZED);
//	
//		results = im.calculate(BAGT.Variant.PLUS);
//		Assertions.assertEquals(?, results.get("component1"), TEST_DELTA);
//		Assertions.assertEquals(?, results.get("component2"), TEST_DELTA);
//		Assertions.assertEquals(?, results.get("component3"), TEST_DELTA);
//		Assertions.assertEquals(?, results.get("component4"), TEST_DELTA);
//		Assertions.assertEquals(?, results.get("component5"), TEST_DELTA);
//	}
}
