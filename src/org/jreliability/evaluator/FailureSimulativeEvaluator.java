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
package org.jreliability.evaluator;

import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.common.Failure;
import org.jreliability.function.BDDDistribution;


/**
 * The {@code FailureSimulativeEvaluator} performs a Monte-Carlo simulation to
 * determine the mean time to failure (MTTF).
 * 
 * @author glass
 * 
 * @param <T>
 *            the elements of the bdd
 */
public class FailureSimulativeEvaluator<T> implements Evaluator {

	/**
	 * The used maximum error / {@code epsilon} value for the simulation.
	 */
	protected final double epsilon;

	/**
	 * The used random number generator to trigger the simulation.
	 */
	protected final Random random = new Random(System.currentTimeMillis());

	/**
	 * Constructs a {@code FailureSimulativeEvaluator} with an epsilon of
	 * {@code 0.001}.
	 * 
	 */
	public FailureSimulativeEvaluator() {
		this(0.001);
	}

	/**
	 * Constructs a {@code FailureSimulativeEvaluator} with a bound epsilon.
	 * 
	 * @param epsilon
	 *            the maximum error
	 */
	public FailureSimulativeEvaluator(double epsilon) {
		this.epsilon = epsilon;
	}

	/**
	 * Returns the mean time to failure (MTTF) derived by simulation.
	 * 
	 * @param distribution
	 *            the bdd distribution
	 * @return the mean time to failure
	 */
	public double evaluate(BDDDistribution<T> distribution) {
		double timeSum = simulateTimeToFailure(distribution);
		int i = 1;
		double diff;

		do {
			double value = simulateTimeToFailure(distribution);
			timeSum += value;
			i++;
			if (i > 10000) {
				System.err.println("Simulation doesn't seem to converge.");
				break;
			}
			diff = Math.abs((timeSum / i) - ((timeSum - value) / (i - 1)));
			System.out.println(diff);
		} while (diff > epsilon);

		double meanTime = (timeSum / i);
		return meanTime;

	}

	/**
	 * Returns the time to failure of one single simulation run.
	 * 
	 * @param distribution
	 *            the bdd distribution
	 * @return the time to failure of one single simulation run
	 */
	protected double simulateTimeToFailure(BDDDistribution<T> distribution) {
		double time = 0;
		BDD<T> bdd = distribution.getBdd();
		BDDProvider<T> provider = bdd.getProvider();
		BDD<T> myBDD = bdd.copy();
		Set<Failure<T>> failures = getFailures(distribution);

		for (Failure<T> failure : failures) {
			BDD<T> objectVariable = provider.get(failure.getObject());
			myBDD.restrictWith(objectVariable.not());
			if (myBDD.isZero()) {
				time = failure.getTime();
				break;
			}
		}

		return time;
	}

	/**
	 * Returns a set of {@code Failures} for the elements {@code T} in the
	 * {@code BDDDistribution}.
	 * 
	 * @param distribution
	 *            the bdd distribution
	 * @return a set of failures for given elements T in the distribution
	 */
	protected Set<Failure<T>> getFailures(BDDDistribution<T> distribution) {
		BDD<T> bdd = distribution.getBdd();
		SortedSet<Failure<T>> failureTimes = new TreeSet<Failure<T>>();
		Set<T> elements = bdd.getVariables();
		for (T element : elements) {
			double x = distribution.getX(random.nextDouble());
			Failure<T> failureTime = new Failure<T>(element, x);
			failureTimes.add(failureTime);
		}
		return failureTimes;
	}

}
