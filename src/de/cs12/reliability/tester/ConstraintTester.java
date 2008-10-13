package de.cs12.reliability.tester;

import java.util.ArrayList;
import java.util.List;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;
import de.cs12.reliability.bdd.BDDs;
import de.cs12.reliability.common.Constraint;
import de.cs12.reliability.common.Constraint.Literal;
import de.cs12.reliability.javabdd.JBDDProviderFactory;

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
		BDDProvider<Integer> bddProvider = bddProviderFactory.getProvider();

		List<Literal<Integer>> literals = new ArrayList<Literal<Integer>>();
		int[] coefficients = { 1, 1, 2, 2, 3, 3, 3, 3, 7 };
		for (int i = 0; i < 9; i++) {
			Literal<Integer> literal = new Literal<Integer>(coefficients[i],
					bddProvider.get(i));
			literals.add(literal);
		}
		Constraint<Integer> constraint = new Constraint<Integer>(8, literals);
		BDD<Integer> bdd = BDDs.getConstraintBDD(constraint);
		System.out.println(bdd.toString());
		System.out.println(bdd.nodeCount());
	}
}
