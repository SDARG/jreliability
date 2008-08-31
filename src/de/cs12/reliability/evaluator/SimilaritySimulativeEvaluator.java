package de.cs12.reliability.evaluator;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.common.FailureTime;
import de.cs12.reliability.distribution.Distribution;
import de.cs12.reliability.distribution.Inverse;

/**
 * The {@code SimilaritySimulativeEvaluator} performs a Monte-Carlo simulation
 * of failures and determines the similarity of the chosen paths to {@code true}
 * .
 * 
 * @author glass
 * 
 * @param <T>
 *            the elements of the bdd
 */
public class SimilaritySimulativeEvaluator<T> extends
		AbstractSimulativeEvaluator<T> {

	protected SortedSet<FailureTime<T>> failureTimes = new TreeSet<FailureTime<T>>();
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@code SimilaritySimulativeEvaluator} with a given {@code
	 * BDD} and a bound epsilon.
	 * 
	 * @param bdd
	 *            the bdd
	 * @param epsilon
	 *            the bound
	 */
	public SimilaritySimulativeEvaluator(BDD<T> bdd, double epsilon) {
		super(bdd, epsilon);
		this.provider = bdd.getProvider();
	}

	/**
	 * Returns the similarity of the {@code true} paths.
	 * 
	 * @param distributions
	 *            the distributions of the bdd elements
	 * @return the similarity of the {@code true} paths
	 */
	public double simulate(Map<T, Distribution> distributions) {
		double similarity = oneRun(distributions);
		double lastSimilarity = 0;
		int i = 0;
		while (Math.abs(similarity - lastSimilarity) > epsilon) {
			lastSimilarity = similarity;
			similarity = oneRun(distributions);
			i++;
			if (i > 1000) {
				System.out.println("Simulation doesn't seem to converge.");
				break;
			}
		}
		initializeFailureTimes(distributions);
		return 0;
	}

	@SuppressWarnings("unchecked")
	protected double oneRun(Map<T, Distribution> distributions) {
		double similarity = 0;
		BDD<T> myBDD = (BDD<T>) bdd.clone();
		initializeFailureTimes(distributions);
		for (FailureTime<T> failure : failureTimes) {
			myBDD.restrictWith(provider.get(failure.getObject()).not());
			if(myBDD.isZero()) {
				similarity = failure.getInformation();
				break;
			}
		}
		return similarity;
	}

	protected void initializeFailureTimes(Map<T, Distribution> distributions) {
		failureTimes.clear();
		for (Entry<T, Distribution> entry : distributions.entrySet()) {
			// TODO Ask Lucky for special Exception or other things
			Inverse inverse = (Inverse) entry.getValue();
			double x = inverse.getX(random.nextDouble());
			failureTimes.add(new FailureTime<T>(entry.getKey(), x));
		}
	}

}
