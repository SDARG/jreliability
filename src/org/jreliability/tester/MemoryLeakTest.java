package org.jreliability.tester;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTransformer;
import org.jreliability.booleanfunction.ANDTerm;
import org.jreliability.booleanfunction.LiteralTerm;
import org.jreliability.booleanfunction.ORTerm;
import org.jreliability.javabdd.JBDDProviderFactory;

public class MemoryLeakTest {

	public static void main(String[] args) {

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Integer> bddProvider = bddProviderFactory.getProvider();
		BDDTransformer<Integer> transformer = new BDDTransformer<Integer>(
				bddProvider);

		Random r = new Random(0);
		for (int i = 0; i < 1000; i++) {

			BDD<Integer> and = bddProvider.one();
			for (int j = 0; j < 10; j++) {
				Set<Integer> vars = new HashSet<Integer>();
				for (int k = 0; k < 10; k++) {
					vars.add(r.nextInt(100));
				}
				BDD<Integer> or = bddProvider.zero();
				for (int var : vars) {
					BDD<Integer> bdd = bddProvider.get(var);
					if (!r.nextBoolean()) {
						bdd = bdd.not();
					}
					or.orWith(bdd);
				}
				and.andWith(or);
			}
			// kill the bdd as follows:
			and.andWith(bddProvider.zero());

		}
		
		System.out.println("SECOND");
		
		
		for (int i = 0; i < 1000; i++) {
			ANDTerm term = new ANDTerm();
			for (int j = 0; j < 10; j++) {
				Set<Integer> vars = new HashSet<Integer>();
				for (int k = 0; k < 10; k++) {
					vars.add(r.nextInt(1000) + 1);
				}

				ORTerm or = new ORTerm();
				for (int var : vars) {
					LiteralTerm lit = new LiteralTerm(r.nextBoolean(), var);
					or.add(lit);
				}
				term.add(or);
			}

			BDD<Integer> result = transformer.transform(term);
			// same as above:
			result.andWith(bddProvider.zero());

		}
		

		
	}

}
