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

import static org.junit.Assert.*;

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

public class CriticalityCalculatorTest {
	final double TEST_DELTA = 0.000001;
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtTime0() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		CriticalityCalculator<String> im = setupCoherentTestSystem();		
		im.getCriticalityValues(0);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAtNegativeTime() {
		/* Some valid system is needed for importance measure object initialization, but is not 
		 * actually accessed for this test, thus the test is representative for all possible systems. */
		CriticalityCalculator<String> im = setupCoherentTestSystem();		
		im.getCriticalityValues(-1);
	}
	
	
	private CriticalityCalculator<String> setupCoherentTestSystem() {
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  	 new ExponentialReliabilityFunction(0.02), 
				  		  	 new ExponentialReliabilityFunction(0.03));
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new CriticalityCalculator<>(bdd, system.getTransformer());
	}

	@Test
	public void testCoherentSystemAtTime50() {		
		CriticalityCalculator<String> critCalc = setupCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(50);
		
		assertEquals(0.42683960407207455, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.5589902533878379, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.5281497805872161, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component3").getRepairCriticality(), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime200() {		
		CriticalityCalculator<String> critCalc = setupCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(200);
		
		assertEquals(0.020703591205875567, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.13714311015747405, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.14869341777201414, results.get("component3").getFailureCriticality(), TEST_DELTA);		
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component3").getRepairCriticality(), TEST_DELTA);
	}
	
	@Test
	public void testCoherentSystemAtTime345() {		
		CriticalityCalculator<String> critCalc = setupCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(345);
		
		assertEquals(0.0010397137350914546, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.03177559790490363, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.03268943622756107, results.get("component3").getFailureCriticality(), TEST_DELTA);		
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component3").getRepairCriticality(), TEST_DELTA);
	}
	
	
	private CriticalityCalculator<String> setupNonCoherentTestSystem() {
		TC_NC_System system = new TC_NC_System();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new CriticalityCalculator<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testNonCoherentSystemAtTime2() {
		CriticalityCalculator<String> critCalc = setupNonCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(2);
		
		assertEquals(0.05823546641575128, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.9417645335842487, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.019024905568074547, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0.03843413972250662, results.get("component3").getRepairCriticality(), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime100() {
		CriticalityCalculator<String> critCalc = setupNonCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(100);
		
		assertEquals(0.950212931632136, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.049787068367863944, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.08554821486874875, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0.3180923728035784, results.get("component3").getRepairCriticality(), TEST_DELTA);
	}
	
	@Test
	public void testNonCoherentSystemAtTime200() {
		CriticalityCalculator<String> critCalc = setupNonCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(200);
		
		assertEquals(0.9975212478233336, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.0024787521766663585, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.01583688671206782, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0.13285653105994633, results.get("component3").getRepairCriticality(), TEST_DELTA);
	}
	
	
	private CriticalityCalculator<String> setupTimeInconsistentNonCoherentTestSystem() {
		TI_NC_System system = new TI_NC_System();
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new CriticalityCalculator<>(bdd, system.getTransformer());
	}	
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime1() {
		CriticalityCalculator<String> critCalc = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(1);
		
		assertEquals(0.00411670143770604, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.9512294245007139, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.006526981004286112, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.9512294245007139, results.get("component4").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.0014413883374135978, results.get("component5").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0.03894418969975297, results.get("component3").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component4").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component5").getRepairCriticality(), TEST_DELTA);
	}	
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime50() {
		CriticalityCalculator<String> critCalc = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(50);
		
		assertEquals(0.7764401652855091, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.0820849986238988, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.12415757908083602, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.0820849986238988, results.get("component4").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.7131004801164056, results.get("component5").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0.07141497890850747, results.get("component3").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component4").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component5").getRepairCriticality(), TEST_DELTA);
	}	
	
	@Test
	public void testTimeInconsistentNonCoherentSystemAtTime100() {
		CriticalityCalculator<String> critCalc = setupTimeInconsistentNonCoherentTestSystem();
		Map<String, CriticalityValues> results = critCalc.getCriticalityValues(100);
		
		assertEquals(0.9502126409597953, results.get("component1").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.006737946999085468, results.get("component2").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.01819222351960241, results.get("component3").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.006737946999085468, results.get("component4").getFailureCriticality(), TEST_DELTA);
		assertEquals(0.9438104472609531, results.get("component5").getFailureCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component1").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component2").getRepairCriticality(), TEST_DELTA);
		assertEquals(0.006614835471120574, results.get("component3").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component4").getRepairCriticality(), TEST_DELTA);
		assertEquals(0, results.get("component5").getRepairCriticality(), TEST_DELTA);
	}
}
