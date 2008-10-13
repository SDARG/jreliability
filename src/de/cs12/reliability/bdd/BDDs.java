package de.cs12.reliability.bdd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.cs12.reliability.common.Constraint;
import de.cs12.reliability.common.Constraint.Literal;
import de.cs12.reliability.common.Constraint.Pair;

/**
 * The {@code BDDs} contains common functions for/on {@code BDD}s.
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
	 * Returns a {@code greater-equal} constraint represented as a {@code BDD}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param constraint
	 *            the greater-equal constraint
	 * @return the bdd representation of the given constraint
	 */
	public static <T> BDD<T> getConstraintBDD(Constraint<T> constraint) {
		List<Literal<T>> literals = constraint.getLhs();
		int materialLeft = 0;
		BDDProvider<T> provider = literals.get(0).getVariable().getProvider();
		for (Literal<T> literal : literals) {
			int coefficient = literal.getCoefficient();
			materialLeft += coefficient;
		}

		int rhs = constraint.getRhs();
		Map<Pair<Integer, Integer>, BDD<T>> memo = new HashMap<Pair<Integer, Integer>, BDD<T>>();
		BDD<T> bdd = buildConstraintBDD(literals, rhs, literals.size(), 0,
				materialLeft, memo, provider);
		return bdd;
	}

	/**
	 * Returns a {@code greater-equal} constraint represented as a {@code BDD}
	 * via a recursive procedure proposed by {@code Een & Soerrensson 2006}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param literals
	 *            the literals
	 * @param rhs
	 *            the right hand side of the constraint
	 * @param index
	 *            the index
	 * @param sum
	 *            the current sum
	 * @param materialLeft
	 *            the material that is potentially left to be added to the sum
	 * @param memo
	 *            the memo maps each point in the recursion to its bdd
	 * @param provider
	 *            the used bdd provider
	 * @return the bdd representation of the given constraint
	 */
	protected static <T> BDD<T> buildConstraintBDD(List<Literal<T>> literals,
			int rhs, int index, int sum, int materialLeft,
			Map<Pair<Integer, Integer>, BDD<T>> memo, BDDProvider<T> provider) {
		if (sum >= rhs) {
			return provider.one();
		} else if (sum + materialLeft < rhs) {
			return provider.zero();
		}

		Pair<Integer, Integer> key = new Pair<Integer, Integer>(index, sum);
		if (!memo.containsKey(key)) {
			index--;
			int coefficient = literals.get(index).getCoefficient();
			materialLeft -= coefficient;
			int hiSum = sum + coefficient;
			int loSum = sum;
			BDD<T> hiBDD = buildConstraintBDD(literals, rhs, index, hiSum,
					materialLeft, memo, provider);
			BDD<T> loBDD = buildConstraintBDD(literals, rhs, index, loSum,
					materialLeft, memo, provider);
			BDD<T> ifBDD = literals.get(index).getVariable();
			BDD<T> resultBDD = ifBDD.ite(hiBDD, loBDD);
			memo.put(key, resultBDD);
		}

		return memo.get(key);
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
