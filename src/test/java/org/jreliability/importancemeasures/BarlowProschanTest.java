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
import org.jreliability.testsystems.TMR;
import org.junit.Test;

public class BarlowProschanTest {
	final double TEST_DELTA = 0.000001;
	
	@Test
	public void testCoherentSystem() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  	 new ExponentialReliabilityFunction(0.02), 
				  		  	 new ExponentialReliabilityFunction(0.03));
		
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		BarlowProschan<String> im = new BarlowProschan<>(bdd, system.getTransformer());
		
		Map<String, Double> results = im.calculate();
		assertEquals(0.2500943301861718, results.get("component1"), TEST_DELTA);
		assertEquals(0.4001030622034817, results.get("component2"), TEST_DELTA);
		assertEquals(0.3504359866196826, results.get("component3"), TEST_DELTA);
	}
	 
	@Test(expected = ArithmeticException.class)
	public void testNonCoherentSystem() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TC_NC_System system = new TC_NC_System();
			
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		BarlowProschan<String> im = new BarlowProschan<>(bdd, system.getTransformer());
		
		im.calculate();
	}
}
