/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.tester;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDs;
import org.jreliability.common.Constraint;
import org.jreliability.common.Constraint.Literal;
import org.jreliability.javabdd.JBDDProviderFactory;

/**
 * The {@code ConstraintTester} can be used to test the {@code
 * BDDs.getConstraintBDD()} function.
 * 
 * @author glass
 * 
 */
public class ConstraintTester {

	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();

		List<Literal<String>> literals = new ArrayList<Literal<String>>();
		int[] coefficients = { 1, 1, 2, 2, 3, 3, 3, 3, 7 };
		String[] variables = { "x", "y", "z", "a", "b", "c", "d", "e", "f" };
		for (int i = 0; i < 9; i++) {
			Literal<String> literal = new Literal<String>(coefficients[i],
					bddProvider.get(variables[i]));
			literals.add(literal);
		}
		Constraint<String> constraint = new Constraint<String>(8, literals);
		BDD<String> bdd = BDDs.getConstraintBDD(constraint);
		System.out.println(bdd.toString());
		System.out.println(bdd.nodeCount());
		System.out.println(BDDs.toDot(bdd));
	}
}
