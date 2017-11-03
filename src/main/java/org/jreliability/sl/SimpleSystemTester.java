package org.jreliability.sl;

import org.jreliability.booleanfunction.Term;
import org.apache.commons.collections15.Transformer;

public class SimpleSystemTester {

	public static void main(String[] args) {
		System.out.println("JReliability - Stochastic Logic\n");
		
		SimpleSystem simpleSystem = new SimpleSystem();
		Term term = simpleSystem.getTerm();
		System.out.print("The Term: ");
		System.out.println(term);
		System.out.println();

		System.out.println("The Components: ");
		System.out.println(simpleSystem.getComponents());
		System.out.println();
						
		System.out.println("The Stochastic Logic: ");
		SLTTRF<SimpleComponent> SLTTRF = new SLTTRF<>(simpleSystem.getTransformer());
		SLTTRF.convertToSL(term);
	}
	
}