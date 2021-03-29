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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.function.DensityFunction;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.testsystems.TCNCSystem;
import org.jreliability.testsystems.TMR;
import org.junit.Test;

public class BarlowProschanTest {
	protected final double TEST_DELTA = 0.000001;
	
	private BarlowProschan<String> setupCoherentTestSystem() {		
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  	 new ExponentialReliabilityFunction(0.02), 
				  		  	 new ExponentialReliabilityFunction(0.03));
		
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		return new BarlowProschan<>(bdd, system.getTransformer());
	}
	
	@Test
	public void testBarlowProschanFunctionInternals() {
		ReliabilityFunction rel1 = new ExponentialReliabilityFunction(0.1);
		ReliabilityFunction rel2 = new ExponentialReliabilityFunction(0.2);
		DensityFunction density = new DensityFunction(rel1);
		
		/* Some valid system is needed for object initialization but is not actually accessed for this test */
		BarlowProschan<?> bp = setupCoherentTestSystem();
		BarlowProschan<?>.BarlowProschanFunction bpf = bp.new BarlowProschanFunction(rel1, rel2, density);
		
		Double[] times = {0.1, 0.3, 0.9, 3.0, 9.0};
		Double[] results = new Double[times.length];
		
		for (int i = 0; i < times.length; i++) {
			results[i] = bpf.getY(times[i]);
		}
		
		List<Double> timesList = Arrays.asList(times);
		List<Double> resultsList = Arrays.asList(results);
		
		assertEquals(resultsList, bpf.getY(timesList));
	}
	
	@Test
	public void testCoherentSystem() {
		BarlowProschan<String> im = setupCoherentTestSystem();
		
		Map<String, Double> results = im.calculate();
		assertEquals(0.2500943301861718, results.get("component1"), TEST_DELTA);
		assertEquals(0.4001030622034817, results.get("component2"), TEST_DELTA);
		assertEquals(0.3504359866196826, results.get("component3"), TEST_DELTA);
	}
	 
	@Test(expected = ArithmeticException.class)
	public void testNonCoherentSystem() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TCNCSystem system = new TCNCSystem();
			
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		BarlowProschan<String> im = new BarlowProschan<>(bdd, system.getTransformer());
		
		im.calculate();
	}
}
