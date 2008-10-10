package de.cs12.reliability.bdd;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code BDDs} contains common functions on {@code BDD}s.
 * 
 * @author glass
 * 
 */
public class BDDs {

	/**
	 * Returns all variables (elements) {@code T} included in the {@code BDD}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @return all variables T included in the bdd
	 */
	public static <T> Set<T> getVariables(BDD<T> bdd) {
		Set<T> variables = new HashSet<T>();
		traverseBDD(bdd, variables);
		return variables;
	}

	/**
	 * Traverses the BDD to collect all variables.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param variables
	 *            the variables
	 */
	protected static <T> void traverseBDD(BDD<T> bdd, Set<T> variables) {
		if (bdd.isOne() || bdd.isZero() || variables.contains(bdd.var())) {
			return;
		}
		traverseBDD(bdd.high(), variables);
		traverseBDD(bdd.low(), variables);
		variables.add(bdd.var());
	}

}
