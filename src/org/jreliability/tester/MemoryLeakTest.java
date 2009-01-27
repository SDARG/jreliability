package org.jreliability.tester;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.TermToReliabilityFunctionBDD;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.common.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.javabdd.JBDDProviderFactory;

/**
 * The {@code MemoryLeakTest}.
 * 
 * @author lukasiewycz
 * 
 */
public class MemoryLeakTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Integer> bddProvider = bddProviderFactory.getProvider();
		TermToReliabilityFunctionBDD<Integer> transformer = new TermToReliabilityFunctionBDD<Integer>(bddProvider);
		Transformer<Integer, ReliabilityFunction> functionTransformer = new Transformer<Integer, ReliabilityFunction>() {
			public ReliabilityFunction transform(Integer a) {
				return new ExponentialReliabilityFunction(0.0001);
			}
		};

		Random r = new Random(0);

		/*
		 * for (int i = 0; i < 1000; i++) { BDD<Integer> and =
		 * bddProvider.one(); for (int j = 0; j < 10; j++) { Set<Integer> vars =
		 * new HashSet<Integer>(); for (int k = 0; k < 30; k++) {
		 * vars.add(r.nextInt(100)); } BDD<Integer> or = bddProvider.zero();
		 * for (int var : vars) { BDD<Integer> bdd = bddProvider.get(var);
		 * or.orWith(bdd); } and.andWith(or); }
		 * 
		 * and.free(); }
		 */

		// if(true) return;
		System.out.println("SECOND");

		for (int i = 0; i < 1000; i++) {
			ANDTerm term = new ANDTerm();
			for (int j = 0; j < 10; j++) {
				Set<Integer> vars = new HashSet<Integer>();
				for (int k = 0; k < 10; k++) {
					vars.add(r.nextInt(100) + 1);
				}

				ORTerm or = new ORTerm();
				for (int var : vars) {
					LiteralTerm<Integer> lit = new LiteralTerm<Integer>(r.nextBoolean(), var);
					or.add(lit);
				}
				term.add(or);
			}

			BDD<Integer> result = transformer.convertToBDD(term);

			// ReliabilityFunction reliabilityFunction =
			// functionTransformer.convert(result, functionTransformer);
			// ((BDDReliabilityFunction)reliabilityFunction).getBdd().free();
			result.free();
			// System.out.println("Done!");
		}

	}

}
