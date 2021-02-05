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
 * Implements a time-inconsistent non-coherent system with exponential failure rates.
 *  
 * The structure function and failure rates have been taken from [Ali17] in order
 * to compare results for the importance measures: 
 * Structure function: Equation 4.51
 * Failure rates: 	   Page 83
 * 
 * Note: The failure rates used in this class are not the ones given on page 83.
 * 		 The example system in [Ali17], for which the importance measures are calculated,
 * 		 also does not use the failure rates on page 83. The rates of some components have
 * 		 been mixed up. Therefore, this class uses the actual failure rates of the used 
 * 		 example system.
 * 		 These are: c1: 0.05, c2: 0.03, c3: 0.02, c4: 0.02, c5: 0.15
 * 
 * [Ali17] ( https://nbn-resolving.org/urn:nbn:de:bvb:29-opus4-87185 )
 * 
 * @author oehmen
 *
 */
public class TI_NC_System {
	protected String component1 = "component1";
	protected String component2 = "component2";
	protected String component3 = "component3";
	protected String component4 = "component4";
	protected String component5 = "component5";
	
	protected Transformer<String, ReliabilityFunction> transformer;
	
	public TI_NC_System() {
		super();
		initialize();
	}
	
	private void initialize() {
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<>();

		reliabilityFunctions.put(component1, new ExponentialReliabilityFunction(0.05));
		reliabilityFunctions.put(component2, new ExponentialReliabilityFunction(0.02));
		reliabilityFunctions.put(component3, new ExponentialReliabilityFunction(0.03));
		reliabilityFunctions.put(component4, new ExponentialReliabilityFunction(0.02));
		reliabilityFunctions.put(component5, new ExponentialReliabilityFunction(0.15));
						
		transformer = new SimpleFunctionTransformer<>(reliabilityFunctions);
	}
	
	public Term getTerm() {
		LiteralTerm<String> c1 = new LiteralTerm<>(component1);
		LiteralTerm<String> c2 = new LiteralTerm<>(component2);
		LiteralTerm<String> c3 = new LiteralTerm<>(component3);
		LiteralTerm<String> c4 = new LiteralTerm<>(component4);
		LiteralTerm<String> c5 = new LiteralTerm<>(component5);

		ANDTerm a24 = new ANDTerm();
		a24.add(c2, c4);
		
		ORTerm o15a24 = new ORTerm();
		o15a24.add(c1, c5, a24);
		
		ORTerm o135 = new ORTerm();
		o135.add(c1, c3, c5);
		
		NOTTerm n3 = new NOTTerm(c3);
		
		ORTerm oa24n3 = new ORTerm();
		oa24n3.add(a24, n3);
		
		ANDTerm system = new ANDTerm();
		system.add(o15a24, o135, oa24n3);
		
		return system;
	}

	public ReliabilityFunction get() {
		Term term = getTerm();
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProvider);
		return bddTTRF.convert(term, transformer);
	}

	public Transformer<String, ReliabilityFunction> getTransformer() {
		return transformer;
	}
}
