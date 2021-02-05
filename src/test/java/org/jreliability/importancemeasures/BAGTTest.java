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

public class BAGTTest {
	final double TEST_DELTA = 0.000001;
	
	@Test
	public void testCoherentSystem() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  	 new ExponentialReliabilityFunction(0.02), 
				  		  	 new ExponentialReliabilityFunction(0.03));
		
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		BAGT<String> im = new BAGT<>(bdd, system.getTransformer());
		
		Map<String, Double> results = im.calculate(BAGT.Variant.PLUS);
		assertEquals(18.33320971180894, results.get("component1"), TEST_DELTA);
		assertEquals(63.333959017207775, results.get("component2"), TEST_DELTA);
		assertEquals(71.66672472239378, results.get("component3"), TEST_DELTA);
		
		results = im.calculate(BAGT.Variant.MINUS);
		assertEquals(25.00660198183078, results.get("component1"), TEST_DELTA);
		assertEquals(20.005272656749337, results.get("component2"), TEST_DELTA);
		assertEquals(11.670674336727288, results.get("component3"), TEST_DELTA);
	}
	 
	@Test
	public void testNonCoherentSystem() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TC_NC_System system = new TC_NC_System();
		
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		BAGT<String> im = new BAGT<>(bdd, system.getTransformer());
		
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS);
		assertEquals(74.99620399721364, results.get("component1"), TEST_DELTA);
		assertEquals(19.974502571796336, results.get("component2"), TEST_DELTA);
		assertEquals(4.989869989110801, results.get("component3"), TEST_DELTA);
		
		/* TODO: Fix BAGT infinite run time to enable complete testing */
//		results = im.calculate(BAGT.Variant.PLUS);
//		assertEquals(Double.POSITIVE_INFINITY, results.get("component1"), TEST_DELTA);
//		assertEquals(13.46, results.get("component2"), TEST_DELTA);
//		assertEquals(21.08, results.get("component3"), TEST_DELTA);
	}
	
	@Test
	public void testTimeInconsistentNonCoherentSystem() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TI_NC_System system = new TI_NC_System();
		
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		BAGT<String> im = new BAGT<>(bdd, system.getTransformer());
		
		/* TODO: Find out if results are acceptable (differ 10-30% from [Ali17]) */
		Map<String, Double> results = im.calculate(BAGT.Variant.MINUS);
		assertEquals(6.846974401650439, results.get("component1"), TEST_DELTA);
		assertEquals(14.276804475931169, results.get("component2"), TEST_DELTA);
		assertEquals(0.5717604268902505, results.get("component3"), TEST_DELTA);	
		assertEquals(14.276804475931169, results.get("component4"), TEST_DELTA);		
		assertEquals(0.45841530680635856, results.get("component5"), TEST_DELTA);

		/* TODO: Fix BAGT infinite run time to enable complete testing */
//		results = im.calculate(BAGT.Variant.PLUS);
//		assertEquals(Double.POSITIVE_INFINITY, results.get("component1"), TEST_DELTA);
//		assertEquals(6, results.get("component2"), TEST_DELTA);
//		assertEquals(2.5, results.get("component3"), TEST_DELTA);
//		assertEquals(6, results.get("component4"), TEST_DELTA);
//		assertEquals(Double.POSITIVE_INFINITY, results.get("component5"), TEST_DELTA);
	}
}
