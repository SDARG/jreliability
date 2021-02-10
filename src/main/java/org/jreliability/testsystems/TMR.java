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
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.SimpleFunctionTransformer;


/**
 * Implements a 2-out-of-3 majority voter system, also known as Triple Modular Redundancy.
 * 
 * @author oehmen
 *
 */
public class TMR {
	protected String component1 = "component1";
	protected String component2 = "component2";
	protected String component3 = "component3";

	protected Transformer<String, ReliabilityFunction> transformer;
	
	public TMR(ReliabilityFunction rf1, ReliabilityFunction rf2, ReliabilityFunction rf3) {
		super();		
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<>();
		
		reliabilityFunctions.put(component1, rf1);
		reliabilityFunctions.put(component2, rf2);
		reliabilityFunctions.put(component3, rf3);
		
		transformer = new SimpleFunctionTransformer<>(reliabilityFunctions);				
	}

	public Term getTerm() {
		LiteralTerm<String> c1 = new LiteralTerm<>(component1);
		LiteralTerm<String> c2 = new LiteralTerm<>(component2);
		LiteralTerm<String> c3 = new LiteralTerm<>(component3);
		
		ANDTerm a12 = new ANDTerm();
		a12.add(c1, c2);

		ANDTerm a13 = new ANDTerm();
		a13.add(c1, c3);
		
		ANDTerm a23 = new ANDTerm();
		a23.add(c2, c3);
		
		ORTerm system = new ORTerm();
		system.add(a12, a13, a23);
		
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