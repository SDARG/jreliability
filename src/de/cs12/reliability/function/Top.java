package de.cs12.reliability.function;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.cs12.reliability.bdd.BDD;

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
class Top<T> {

	/**
	 * Constructs a {@code Top}.
	 * 
	 */
	public Top() {
		super();
	}

	/**
	 * Calculates the top event of the {@code BDD} based on the {@code Function}
	 * of each variable {@code T} at the given {@code x} value.
	 * 
	 * @param <T> the type of variable
	 * @param bdd the bdd
	 * @param transformer the transformer
	 * @param x the x value
	 * @return the top event of the bdd
	 */
	public static <T> double calculateTop(BDD<T> bdd,
			FunctionTransformer<T> transformer, double x) {
		if (bdd.isOne()) {
			return 1.0;
		}
		if (bdd.isZero()) {
			return 0.0;
		}

		Set<BDD<T>> upSort = new LinkedHashSet<BDD<T>>();
		traverseBDD(bdd, upSort);

		return evaluate(bdd, transformer, upSort, x);
	}

	/**
	 * Traverses the BDD to sort the nodes.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param upSort
	 *            the sorted bdd ndes
	 */
	protected static <T> void traverseBDD(BDD<T> bdd, Set<BDD<T>> upSort) {
		if (bdd.isOne() || bdd.isZero() || upSort.contains(bdd)) {
			return;
		}
		traverseBDD(bdd.high(), upSort);
		traverseBDD(bdd.low(), upSort);

		upSort.add(bdd);
	}

	/**
	 * Evaluates the BDD to determine the top event.
	 * 
	 * @param <T>
	 *            the type of the variables
	 * @param bdd
	 *            the bdd
	 * @param transformer
	 *            the function transformer
	 * @param upSort
	 *            the sorted bdd nodes
	 * @param x
	 *            the x value
	 * @return the top event
	 */
	protected static <T> double evaluate(BDD<T> bdd,
			FunctionTransformer<T> transformer, Set<BDD<T>> upSort, double x) {
		Map<T, Double> values = new HashMap<T, Double>();
		HashMap<BDD<T>, Double> bddToDouble = new HashMap<BDD<T>, Double>();

		for (BDD<T> tmpBdd : upSort) {

			T t = tmpBdd.var();
			Double r = values.get(t);
			if (r == null) {
				r = transformer.transform(t).getY(x);
				values.put(t, r);
			}

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
