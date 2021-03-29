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

public class BirnbaumABTest {
	protected final double TEST_DELTA = 0.000001;
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtTime0() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		BirnbaumAB<String> im = setupCoherentTestSystem();		
		im.calculate(0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtNegativeTime() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		BirnbaumAB<String> im = setupCoherentTestSystem();		
		im.calculate(-1);
	}
	
	
	private BirnbaumAB<String> setupCoherentTestSystem() {
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  	 new ExponentialReliabilityFunction(0.02), 
				  		  	 new ExponentialReliabilityFunction(0.03));
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new BirnbaumAB<>(bdd, system.getTransformer());
	}	

	@Test
	public void testCoherentSystemAtTime50() {
		BirnbaumAB<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(50);
		
		assertEquals(0.42683960407207455, results.get("component1"), TEST_DELTA);
		assertEquals(0.5589902533878379, results.get("component2"), TEST_DELTA);
		assertEquals(0.5281497805872161, results.get("component3"), TEST_DELTA);
	}

	@Test
	public void testCoherentSystemAtTime200() {
		BirnbaumAB<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(200);
		
		assertEquals(0.020703591205875567, results.get("component1"), TEST_DELTA);
		assertEquals(0.13714311015747405, results.get("component2"), TEST_DELTA);
		assertEquals(0.14869341777201414, results.get("component3"), TEST_DELTA);
	}

	@Test
	public void testCoherentSystemAtTime345() {
		BirnbaumAB<String> im = setupCoherentTestSystem();
		Map<String, Double> results = im.calculate(345);
		
		assertEquals(0.0010397137350914546, results.get("component1"), TEST_DELTA);
		assertEquals(0.03177559790490363, results.get("component2"), TEST_DELTA);
		assertEquals(0.03268943622756107, results.get("component3"), TEST_DELTA);
	}
	
	
	
	private BirnbaumAB<String> setupNonCoherentTestSystem() {
		TC_NC_System system = new TC_NC_System();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new BirnbaumAB<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testNonCoherentSystemAtTime2() {
		BirnbaumAB<String> im = setupNonCoherentTestSystem();		
		Map<String, Double> results = im.calculate(2);
		
		assertEquals(0.05823546641575128, results.get("component1"), TEST_DELTA);
		assertEquals(0.9417645335842487, results.get("component2"), TEST_DELTA);
		assertEquals(0.05745904529058117, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime100() {
		BirnbaumAB<String> im = setupNonCoherentTestSystem();		
		Map<String, Double> results = im.calculate(100);
		
		assertEquals(0.950212931632136, results.get("component1"), TEST_DELTA);
		assertEquals(0.049787068367863944, results.get("component2"), TEST_DELTA);
		assertEquals(0.40364058767232713, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime200() {
		BirnbaumAB<String> im = setupNonCoherentTestSystem();		
		Map<String, Double> results = im.calculate(200);
		
		assertEquals(0.9975212478233336, results.get("component1"), TEST_DELTA);
		assertEquals(0.0024787521766663585, results.get("component2"), TEST_DELTA);
		assertEquals(0.14869341777201414, results.get("component3"), TEST_DELTA);
	}
	
	
	
	private BirnbaumAB<String> setupTimeInconsistentNonCoherentTestSystem() {
		TI_NC_System system = new TI_NC_System();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());					
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new BirnbaumAB<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime1() {
		BirnbaumAB<String> im = setupTimeInconsistentNonCoherentTestSystem();		
		Map<String, Double> results = im.calculate(1);
		
		assertEquals(0.00411670143770604, results.get("component1"), TEST_DELTA);
		assertEquals(0.9512294245007139, results.get("component2"), TEST_DELTA);
		assertEquals(0.04547117070403908, results.get("component3"), TEST_DELTA);
		assertEquals(0.9512294245007139, results.get("component4"), TEST_DELTA);
		assertEquals(0.0014413883374135978, results.get("component5"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime50() {
		BirnbaumAB<String> im = setupTimeInconsistentNonCoherentTestSystem();		
		Map<String, Double> results = im.calculate(50);
		
		assertEquals(0.7764401652855091, results.get("component1"), TEST_DELTA);
		assertEquals(0.0820849986238988, results.get("component2"), TEST_DELTA);
		assertEquals(0.19557255798934348, results.get("component3"), TEST_DELTA);
		assertEquals(0.0820849986238988, results.get("component4"), TEST_DELTA);
		assertEquals(0.7131004801164056, results.get("component5"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime100() {
		BirnbaumAB<String> im = setupTimeInconsistentNonCoherentTestSystem();		
		Map<String, Double> results = im.calculate(100);		
		
		assertEquals(0.9502126409597953, results.get("component1"), TEST_DELTA);
		assertEquals(0.006737946999085468, results.get("component2"), TEST_DELTA);
		assertEquals(0.024807058990722983, results.get("component3"), TEST_DELTA);
		assertEquals(0.006737946999085468, results.get("component4"), TEST_DELTA);
		assertEquals(0.9438104472609531, results.get("component5"), TEST_DELTA);
	}
}
