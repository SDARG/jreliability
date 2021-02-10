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

package org.jreliability.testsystems;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.SimpleFunctionTransformer;


/**
 * Implements a time-consistent non-coherent system with exponential failure rates.
 *  
 * The structure function and failure rates have been taken from [Ali17] in order
 * to compare results for the importance measures: 
 * Structure function: Equation 4.42
 * Failure rates: 	   Page 81
 * 
 * [Ali17] ( https://nbn-resolving.org/urn:nbn:de:bvb:29-opus4-87185 )
 *  
 * @author oehmen
 *
 */
public class TC_NC_System {
	protected String component1 = "component1";
	protected String component2 = "component2";
	protected String component3 = "component3";
	
	protected Transformer<String, ReliabilityFunction> transformer;
	
	public TC_NC_System() {
		super();
		initialize();
	}
	
	private void initialize() {
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<>();

		reliabilityFunctions.put(component1, new ExponentialReliabilityFunction(0.01));
		reliabilityFunctions.put(component2, new ExponentialReliabilityFunction(0.02));
		reliabilityFunctions.put(component3, new ExponentialReliabilityFunction(0.03));
				
		transformer = new SimpleFunctionTransformer<>(reliabilityFunctions);
	}
	
	public Term getTerm() {
		LiteralTerm<String> c1 = new LiteralTerm<>(component1);
		LiteralTerm<String> c2 = new LiteralTerm<>(component2);
		LiteralTerm<String> c3 = new LiteralTerm<>(component3);
		
		ANDTerm a12 = new ANDTerm();
		a12.add(c1, c2);
		
		NOTTerm n3 = new NOTTerm(c3);
		
		ANDTerm a1n3 = new ANDTerm();
		a1n3.add(c1, n3);
		
		ANDTerm a23 = new ANDTerm();
		a23.add(c2, c3);
		
		ORTerm system = new ORTerm();
		system.add(a12, a1n3, a23);
		
		return system;
	}

	public ReliabilityFunction get() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProvider);
		
		return bddTTRF.convert(getTerm(), getTransformer());
	}

	public Transformer<String, ReliabilityFunction> getTransformer() {
		return transformer;
	}
}
