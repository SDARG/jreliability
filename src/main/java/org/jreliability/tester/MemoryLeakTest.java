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
package org.jreliability.tester;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;

/**
 * The {@link MemoryLeakTest}.
 * 
 * @author lukasiewycz
 * 
 */
public class MemoryLeakTest {

	/**
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Integer> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<Integer> transformer = new BDDTTRF<>(bddProvider);

		Random r = new Random(0);

		/*
		 * for (int i = 0; i < 1000; i++) { BDD<Integer> and =
		 * bddProvider.one(); for (int j = 0; j < 10; j++) { Set<Integer> vars =
		 * new HashSet<Integer>(); for (int k = 0; k < 30; k++) {
		 * vars.add(r.nextInt(100)); } BDD<Integer> or = bddProvider.zero(); for
		 * (int var : vars) { BDD<Integer> bdd = bddProvider.get(var);
		 * or.orWith(bdd); } and.andWith(or); }
		 * 
		 * and.free(); }
		 */

		// if(true) return;
		System.out.println("SECOND");

		for (int i = 0; i < 1000; i++) {
			ANDTerm term = new ANDTerm();
			for (int j = 0; j < 10; j++) {
				Set<Integer> vars = new HashSet<>();
				for (int k = 0; k < 10; k++) {
					vars.add(r.nextInt(100) + 1);
				}

				ORTerm or = new ORTerm();
				for (int var : vars) {
					LiteralTerm<Integer> lit = new LiteralTerm<>(var);
					if (!r.nextBoolean()) {
						NOTTerm notLit = new NOTTerm(lit);
						or.add(notLit);
					}
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
