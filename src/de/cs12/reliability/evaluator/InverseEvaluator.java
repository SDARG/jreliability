package de.cs12.reliability.evaluator;

import de.cs12.reliability.function.Distribution;

/**
 * The {@code InverseEvaluator} calculates the inverse function of the
 * {@code Distribution}, i.e., f^(-1)(y) = x. This is typically used to derive
 * measures like, e.g., the Mission Time (MT).
 * 
 * @author glass
 */
public class InverseEvaluator implements Evaluator {

	/**
	 * Constructs an {@code InverseEvaluator}.
	 * 
	 */
	public InverseEvaluator() {
		super();
	}

	/**
	 * Returns the {@code x} value for a {@code y} value in {@code y = f(x)} for
	 * a given {@code Distribution}.
	 * 
	 * @param distribution
	 *            the distribution
	 * @param y
	 *            the y value
	 * @return the x value for a y value and a given distribution
	 */
	public double evaluate(Distribution distribution, double y) {
		return distribution.getX(y);
	}

}
