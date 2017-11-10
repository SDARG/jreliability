package org.jreliability.sl;

import org.jreliability.booleanfunction.Term;

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
		SLReliabilityFunction<SimpleComponent> slReliabilityFunction = new SLReliabilityFunction<>(term, simpleSystem.getTransformer(), 10000000);
		System.out.println(slReliabilityFunction.getY(1.0));
		// It works until numberOfBits = 10,000,000 (But it takes a few seconds.)
		// If numberOfBits >= 100,000,000, Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	}
	
}