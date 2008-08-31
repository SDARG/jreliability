/**
 * 
 */
package de.cs12.reliability.evaluator;

import java.util.Random;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code AbstractSimulativeEvaluator} is the basic class for all evaluators
 * that determine the aimed value using simulative techniques, e.g., Monte Carlo
 * simulation.
 * 
 * @author glass
 * @param <T>
 *            the objects in the bdd
 * 
 */
public abstract class AbstractSimulativeEvaluator<T> extends
		AbstractEvaluator<T> {

	protected final double epsilon;
	protected final Random random = new Random(System.currentTimeMillis()); 

	/**
	 * Constructs an {@code AbstractSimulativeEvaluator} with a given {@code
	 * BDD} and a bound epsilon.
	 * 
	 * @param bdd
	 *            the bdd
	 * @param epsilon
	 *            the bound
	 */
	public AbstractSimulativeEvaluator(BDD<T> bdd, double epsilon) {
		super(bdd);
		this.epsilon = epsilon;
	}

}
