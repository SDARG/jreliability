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
 * Implements a coherent bridge system with two paths 1 2 and 3 4 and the bridge 5.
 * 
 * @author oehmen
 * 
 */
public class BridgeSystem {
	protected String component1 = "component1";
	protected String component2 = "component2";
	protected String component3 = "component3";
	protected String component4 = "component4";
	protected String component5 = "component5";
	
	protected Transformer<String, ReliabilityFunction> transformer;
	
	public BridgeSystem(ReliabilityFunction rf1, ReliabilityFunction rf2, ReliabilityFunction rf3, ReliabilityFunction rf4, ReliabilityFunction rf5) {
		super();
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<>();

		reliabilityFunctions.put(component1, rf1);
		reliabilityFunctions.put(component2, rf2);
		reliabilityFunctions.put(component3, rf3);
		reliabilityFunctions.put(component4, rf4);
		reliabilityFunctions.put(component5, rf5);

		transformer = new SimpleFunctionTransformer<>(reliabilityFunctions);
	}

	public Term getTerm() {
		LiteralTerm<String> c1 = new LiteralTerm<>(component1);
		LiteralTerm<String> c2 = new LiteralTerm<>(component2);
		LiteralTerm<String> c3 = new LiteralTerm<>(component3);
		LiteralTerm<String> c4 = new LiteralTerm<>(component4);
		LiteralTerm<String> c5 = new LiteralTerm<>(component5);
		
		ANDTerm a12 = new ANDTerm();
		a12.add(c1, c2);
		
		ANDTerm a34 = new ANDTerm();
		a34.add(c3, c4);
		
		ANDTerm a135 = new ANDTerm();
		a135.add(c1, c3, c5);
		
		ANDTerm a245 = new ANDTerm();
		a245.add(c2, c4, c5);
		
		ORTerm system = new ORTerm();
		system.add(a12, a34, a135, a245);
		
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