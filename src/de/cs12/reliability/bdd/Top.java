package de.cs12.reliability.bdd;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The {@code Top} calculates the probability of the root node (top event) of a
 * {@code BDD}.
 * 
 * @author glass
 * @param <T>
 *            the type of the variables
 * @see BDD
 * 
 */
public class Top<T> {

	protected final BDD<T> bdd;

	protected Set<BDD<T>> upSort = new LinkedHashSet<BDD<T>>();

	/**
	 * Constructs a {@code Top} with a {@code BDD}.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	public Top(BDD<T> bdd) {
		this.bdd = bdd;
	}

	/**
	 * Calculates the top event with the given values of each variable.
	 * 
	 * @param values
	 *            the value of each variable
	 * @return the top event of the bdd
	 */
	public double getValue(Map<T, Double> values) {
		if (bdd.isOne()) {
			return 1.0;
		}
		if (bdd.isZero()) {
			return 0.0;
		}

		if (upSort.size() == 0) {
			traverseBDD(bdd);
		}

		return evaluate(values);
	}

	/**
	 * Traverses the BDD to sort the nodes.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	private void traverseBDD(BDD<T> bdd) {
		if (bdd.isOne() || bdd.isZero() || upSort.contains(bdd)) {
			return;
		}
		traverseBDD(bdd.high());
		traverseBDD(bdd.low());

		upSort.add(bdd);
	}

	/**
	 * Evaluates the BDD to determine the top event.
	 * 
	 * @param values
	 *            the values for each variable
	 * @return the top event
	 */
	private double evaluate(Map<T, Double> values) {
		HashMap<BDD<T>, Double> bddToDouble = new HashMap<BDD<T>, Double>();

		for (BDD<T> tmpBdd : upSort) {
			double r = values.get(tmpBdd.var());
			double high;
			double low;

			if (tmpBdd.high().isOne()) {
				high = 1.0;
			} else if (tmpBdd.high().isZero()) {
				high = 0.0;
			} else {
				high = bddToDouble.get(tmpBdd.high());
			}

			if (tmpBdd.low().isOne()) {
				low = 1.0;
			} else if (tmpBdd.low().isZero()) {
				low = 0.0;
			} else {
				low = bddToDouble.get(tmpBdd.low());
			}

			// Shannon decomposition
			double reliability = r * high + (1 - r) * low;

			bddToDouble.put(tmpBdd, reliability);
		}

		return bddToDouble.get(bdd);
	}

}
