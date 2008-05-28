package de.cs12.bdd.reliability;

import java.util.HashMap;
import java.util.Map;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code AbstractEvaluator} is the abstract base class for all evaluators
 * of a {@code BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variables
 */
public abstract class AbstractEvaluator<T> {

	protected final Top<T> top;

	protected final BDD<T> bdd;

	/**
	 * Constructs a {@code AbstractEvaluator} with a {@code BDD}.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	public AbstractEvaluator(BDD<T> bdd) {
		this.bdd = bdd;
		this.top = new Top<T>(bdd);
	}

	/**
	 * Calculates the top event of the {@code BDD} based on the
	 * {@code Distribution} of each variable and the given {@code x} value.
	 * 
	 * @param distributions
	 *            the distribution for each element
	 * @param x
	 *            the x value
	 * @return the top event of the BDD
	 */
	protected double calculateTop(Map<T, Distribution> distributions, double x) {
		return top.getValue(calculate(distributions, x));
	}

	/**
	 * Calculates the value for each element based on its {@code Distribution}
	 * and value {@code x}.
	 * 
	 * @param distributions
	 *            the distribution for each element
	 * @param x
	 *            the x value
	 * @return the value for each element
	 */
	protected Map<T, Double> calculate(Map<T, Distribution> distributions,
			double x) {
		Map<T, Double> values = new HashMap<T, Double>();
		for (T t : distributions.keySet()) {
			values.put(t, distributions.get(t).getY(x));
		}
		return values;

	}

}
