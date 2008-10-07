package de.cs12.reliability.evaluator;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.common.Failure;
import de.cs12.reliability.distribution.Distribution;
import de.cs12.reliability.distribution.Inverse;

/**
 * The {@code FailureSimulativeEvaluator} performs a Monte-Carlo simulation to
 * determine the mean time to failure (MTTF).
 * 
 * @author glass
 * 
 * @param <T>
 *            the elements of the bdd
 */
public class FailureSimulativeEvaluator<T> extends
		AbstractSimulativeEvaluator<T> {

	protected final double epsilon;
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@code FailureSimulativeEvaluator} with a given {@code BDD}
	 * and a bound epsilon.
	 * 
	 * @param bdd
	 *            the bdd
	 * @param epsilon
	 *            the bound
	 */
	public FailureSimulativeEvaluator(BDD<T> bdd, double epsilon) {
		super(bdd);
		this.epsilon = epsilon;
		this.provider = bdd.getProvider();
	}

	/**
	 * Returns the mean time to failure (MTTF) derived by simulation.
	 * 
	 * @param distributions
	 *            the distributions of the bdd elements
	 * @return the mean time to failure
	 */
	public double getMTTF(Map<T, Distribution> distributions) {
		double timeSum = simulateTimeToFailure(distributions);
		int i = 1;
		double diff;

		do {
			double value = simulateTimeToFailure(distributions);
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
	 * @param distributions
	 *            the distributions of the bdd elements
	 * @return the time to failure of one single simulation run
	 */
	@SuppressWarnings("unchecked")
	protected double simulateTimeToFailure(Map<T, Distribution> distributions) {
		double time = 0;
		BDD<T> myBDD = (BDD<T>) bdd.clone();
		Set<Failure<T>> failures = getFailures(distributions);

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
	 * Returns a set of {@code Failures} for given elements {@code T} and their
	 * corresponding {@code Distributions}.
	 * 
	 * @param distributions
	 *            elements T and their corresponding distributions
	 * @return a set of failures for given elements T and their corresponding
	 *         distributions
	 */
	protected Set<Failure<T>> getFailures(Map<T, Distribution> distributions) {
		SortedSet<Failure<T>> failureTimes = new TreeSet<Failure<T>>();
		for (Entry<T, Distribution> entry : distributions.entrySet()) {
			T object = entry.getKey();
			Distribution distribution = entry.getValue();

			assert (distribution instanceof Inverse) : "There exists no inverse function for "
					+ distribution.getClass();

			Inverse inverse = (Inverse) distribution;
			double x = inverse.getX(random.nextDouble());
			Failure<T> failureTime = new Failure<T>(object, x);

			failureTimes.add(failureTime);
		}
		return failureTimes;
	}

}
