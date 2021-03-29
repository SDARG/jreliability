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
import org.jreliability.testsystems.TC_NC_System;
import org.jreliability.testsystems.TI_NC_System;
import org.jreliability.testsystems.TMR;
import org.junit.Test;

public class RAWTest {
	protected final double TEST_DELTA = 0.000001;
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtTime0() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		RAW<String> im = setupCoherentTestSystem();		
		im.calculate(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtNegativeTime() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		RAW<String> im = setupCoherentTestSystem();		
		im.calculate(-1);
	}
	
	
	private RAW<String> setupCoherentTestSystem() {
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  	 new ExponentialReliabilityFunction(0.02), 
				  		  	 new ExponentialReliabilityFunction(0.03));
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new RAW<>(bdd, system.getTransformer());
	}

	@Test
	public void testCoherentSystemAtTime0d05() {
		RAW<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(0.05);
		
		assertEquals(909.4422672479282, results.get("component1"), TEST_DELTA);
		assertEquals(727.7356567645448, results.get("component2"), TEST_DELTA);
		assertEquals(545.9381702587863, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime0d6() {
		RAW<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(0.6);
		
		assertEquals(76.11024025109899, results.get("component1"), TEST_DELTA);
		assertEquals(61.0703066039854155, results.get("component2"), TEST_DELTA);
		assertEquals(45.93986209393344, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime2d2() {
		RAW<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(2.2);
		
		assertEquals(21.017649697609404, results.get("component1"), TEST_DELTA);
		assertEquals(16.997011342467804, results.get("component2"), TEST_DELTA);
		assertEquals(12.88693877432086, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime9d5() {
		RAW<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(9.5);
		
		assertEquals(5.1591503816722994, results.get("component1"), TEST_DELTA);
		assertEquals(4.31352042338028, results.get("component2"), TEST_DELTA);
		assertEquals(3.383615951584543, results.get("component3"), TEST_DELTA);
	}
	
	
	private RAW<String> setupNonCoherentTestSystem() {
		TC_NC_System system = new TC_NC_System();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new RAW<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testNonCoherentSystemAtTime1() {
		RAW<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(1);
		assertEquals(2.499750040410122, results.get("component1"), TEST_DELTA);
		assertEquals(49.7555409792071, results.get("component2"), TEST_DELTA);
		assertEquals(0.5099986668799622, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime50() {
		RAW<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(50);
		assertEquals(2.0547899626783015, results.get("component1"), TEST_DELTA);
		assertEquals(1.1837505989181896, results.get("component2"), TEST_DELTA);
		assertEquals(0.8807970779778825, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime100() {
		RAW<String> im = setupNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(100);
		assertEquals(1.543055386421638, results.get("component1"), TEST_DELTA);
		assertEquals(1.0104675552427984, results.get("component2"), TEST_DELTA);
		assertEquals(0.9820137900379083, results.get("component3"), TEST_DELTA);
	}
	
	
	private RAW<String> setupTimeInconsistentNonCoherentTestSystem() {
		TI_NC_System system = new TI_NC_System();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new RAW<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime0d2() {
		RAW<String> im = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(0.2);
		
		assertEquals(1.0220945188958193, results.get("component1"), TEST_DELTA);
		assertEquals(125.47302084390537, results.get("component2"), TEST_DELTA);
		assertEquals(0.0371200722545637, results.get("component3"), TEST_DELTA);
		assertEquals(125.47302084390537, results.get("component4"), TEST_DELTA);
		assertEquals(1.007291315207327, results.get("component5"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime15() {
		RAW<String> im = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(15);
		
		assertEquals(1.3338093471054555, results.get("component1"), TEST_DELTA);
		assertEquals(1.762827651477927, results.get("component2"), TEST_DELTA);
		assertEquals(1.0289569897082076, results.get("component3"), TEST_DELTA);
		assertEquals(1.762827651477927, results.get("component4"), TEST_DELTA);
		assertEquals(1.043929859912114, results.get("component5"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime30() {
		RAW<String> im = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, Double> results = im.calculate(30);
		
		assertEquals(1.1769452506962408, results.get("component1"), TEST_DELTA);
		assertEquals(1.1654794423678578, results.get("component2"), TEST_DELTA);
		assertEquals(1.0381477042916811, results.get("component3"), TEST_DELTA);
		assertEquals(1.1654794423678578, results.get("component4"), TEST_DELTA);
		assertEquals(1.006920784082757, results.get("component5"), TEST_DELTA);
	}
}
