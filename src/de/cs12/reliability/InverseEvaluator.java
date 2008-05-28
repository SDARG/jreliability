package de.cs12.reliability;

import java.util.Map;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code InverseEvaluator} calculates the inverse function of the
 * {@code BDD}, i.e., f^(-1)(y) = x. This is typically used to derive measures
 * like, e.g., the Mission Time (MT).
 * 
 * @author glass
 * @param <T>
 *            the type of the variables
 */
public class InverseEvaluator<T> extends AbstractEvaluator<T> {

	protected final double epsilon;

	/**
	 * Constructs a {@code InverseEvaluator} with a {@code BDD} and a maximum
	 * error {@code 0.01}.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	public InverseEvaluator(BDD<T> bdd) {
		this(bdd, 0.01);
	}

	/**
	 * Constructs a {@code InverseEvaluator} with a {@code BDD} and a maximum
	 * error {@code epsilon}.
	 * 
	 * @param bdd
	 *            the bdd
	 * @param epsilon
	 *            the maximum error
	 */
	public InverseEvaluator(BDD<T> bdd, double epsilon) {
		super(bdd);
		this.epsilon = epsilon;
	}

	/**
	 * Returns the {@code x} value of the inverse function {@code f^(-1)(y) = x}
	 * for the {@code BDD} and the given {@code y} value.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @param y
	 *            the y value
	 * @return the x value
	 */
	public double getValue(Map<T, Distribution> distributions, double y) {
		return bisection(distributions, y, 0, 1);
	}

	/**
	 * Calculates the x value using bisection with a maximum error epsilon.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @param y
	 *            the y value
	 * @param low
	 *            the lower end of the interval
	 * @param high
	 *            the upper end of the interval
	 * @return the x value
	 */
	protected double bisection(Map<T, Distribution> distributions, double y,
			double low, double high) {

		double x = high - ((high - low) / 2);
		double tmpY = calculateTop(distributions, x);

		if (tmpY < (y - epsilon)) {
			return bisection(distributions, y, low, x);
		} else if (tmpY > (y + epsilon)) {
			return bisection(distributions, y, x, high);
		} else {
			return x;
		}

	}

}
