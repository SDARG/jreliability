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

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.testsystems.TCNCSystem;
import org.jreliability.testsystems.TINCSystem;
import org.jreliability.testsystems.TMR;
import org.junit.Test;

public class RRWTest {
	protected final double TEST_DELTA = 0.000001;
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtTime0() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		RRW<String> im = setupCoherentTestSystem();		
		im.calculate(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtNegativeTime() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		RRW<String> im = setupCoherentTestSystem();		
		im.calculate(-1);
	}	
	
	
	private RRW<String> setupCoherentTestSystem() {
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
			  		  		 new ExponentialReliabilityFunction(0.02), 
			  		  		 new ExponentialReliabilityFunction(0.03));
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new RRW<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testCoherentSystemAtTime1() {
		RRW<String> im = setupCoherentTestSystem();	
		Map<String, Double> results = im.calculate(1);
		
		assertEquals(1.8192718131942236, results.get("component1"), TEST_DELTA);
		assertEquals(3.620441569390924, results.get("component2"), TEST_DELTA);
		assertEquals(5.403689387069474, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime50() {
		RRW<String> im = setupCoherentTestSystem();	
		Map<String, Double> results = im.calculate(50);
		
		assertEquals(1.3420010416827755, results.get("component1"), TEST_DELTA);
		assertEquals(2.15596581882967, results.get("component2"), TEST_DELTA);
		assertEquals(2.6496604120954226, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime100() {
		RRW<String> im = setupCoherentTestSystem();	
		Map<String, Double> results = im.calculate(100);
		
		assertEquals(1.1320584167477115, results.get("component1"), TEST_DELTA);
		assertEquals(1.5485194344742874, results.get("component2"), TEST_DELTA);
		assertEquals(1.7017268809452302, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime200() {
		RRW<String> im = setupCoherentTestSystem();	
		Map<String, Double> results = im.calculate(200);
		
		assertEquals(1.0182809766484426, results.get("component1"), TEST_DELTA);
		assertEquals(1.1560903210376143, results.get("component2"), TEST_DELTA);
		assertEquals(1.1747407876931748, results.get("component3"), TEST_DELTA);
	}
	
	
	private RRW<String> setupNonCoherentTestSystem() {
		TCNCSystem system = new TCNCSystem();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new RRW<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testNonCoherentSystemAtTime1() {
		RRW<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(1);
		
		assertEquals(1.0153034026771757, results.get("component1"), TEST_DELTA);
		assertEquals(66.34494459137942, results.get("component2"), TEST_DELTA);
		assertEquals(0.9852966523246695, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime50() {
		RRW<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(50);
		
		assertEquals(3.1672098501754404, results.get("component1"), TEST_DELTA);
		assertEquals(1.4614227828094486, results.get("component2"), TEST_DELTA);
		assertEquals(0.7067000410933301, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime100() {
		RRW<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(100);
		
		assertEquals(14.952645495456053, results.get("component1"), TEST_DELTA);
		assertEquals(1.0716709960362478, results.get("component2"), TEST_DELTA);
		assertEquals(0.7444483835627034, results.get("component3"), TEST_DELTA);
	}
	
	
	private RRW<String> setupTimeInconsistentNonCoherentTestSystem() {
		TINCSystem system = new TINCSystem();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new RRW<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime0d1() {
		RRW<String> im = setupTimeInconsistentNonCoherentTestSystem();	
		Map<String, Double> results = im.calculate(0.1);
		
		assertEquals(1.0000558861265578, results.get("component1"), TEST_DELTA);
		assertEquals(1.9978905736146773, results.get("component2"), TEST_DELTA);
		assertEquals(0.9970602142227959, results.get("component3"), TEST_DELTA);
		assertEquals(1.9978905736146773, results.get("component4"), TEST_DELTA);
		assertEquals(1.0000558861265578, results.get("component5"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime25() {
		RRW<String> im = setupTimeInconsistentNonCoherentTestSystem();	
		Map<String, Double> results = im.calculate(25);
		
		assertEquals(2.231143501487292, results.get("component1"), TEST_DELTA);
		assertEquals(1.2036793762612144, results.get("component2"), TEST_DELTA);
		assertEquals(1.0539175644680665, results.get("component3"), TEST_DELTA);
		assertEquals(1.2036793762612144, results.get("component4"), TEST_DELTA);
		assertEquals(2.231143501487292, results.get("component5"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime49() {
		RRW<String> im = setupTimeInconsistentNonCoherentTestSystem();	
		Map<String, Double> results = im.calculate(49);
		
		assertEquals(4.559659118721887, results.get("component1"), TEST_DELTA);
		assertEquals(1.063659107744614, results.get("component2"), TEST_DELTA);
		assertEquals(1.0483818351581868, results.get("component3"), TEST_DELTA);
		assertEquals(1.063659107744614, results.get("component4"), TEST_DELTA);
		assertEquals(4.559659118721887, results.get("component5"), TEST_DELTA);
	}
}
