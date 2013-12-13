/**
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jreliability.common.Failure;
import org.jreliability.cra.Adapter;
import org.jreliability.cra.CompositionalReliabilityNode;
import org.jreliability.function.InverseFunction;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.SampledReliabilityFunction;

/**
 * The {@code BDDTTRFSimulative} performs a {@code Monte-Carlo simulation} to
 * determine the {@code ReliabilityFunction} based on a {@code BDD}
 * representation of the system structure..
 * 
 * @author glass
 * 
 * @param <T>
 *            the elements of the bdd
 */
public class BDDTTRFSimulative<T> implements CompositionalReliabilityNode<BDD<T>, ReliabilityFunction> {

	/**
	 * The used maximum error / {@code epsilon} value for the simulation.
	 */
	protected final double epsilon;

	/**
	 * The used random number generator to trigger the simulation.
	 */
	protected final Random random = new Random(System.currentTimeMillis());

	private Adapter<T, ReliabilityFunction> functionTransformer;

	/**
	 * Constructs a {@code BDDTTRFSimulative} with a given {@code BDDProvider}
	 * and a standard epsilon of {@code 0.001}.
	 * 
	 * @param provider
	 *            the used bddProvider
	 */
	public BDDTTRFSimulative(Adapter<T, ReliabilityFunction> functionTransformer) {
		this(functionTransformer, 0.001);
	}

	/**
	 * Constructs a {@code BDDTTRFSimulative} with a given {@code BDDProvider}
	 * and an epsilon.
	 * 
	 * @param provider
	 *            the used bddProvider
	 * @param epsilon
	 *            the used epsilon value
	 */
	public BDDTTRFSimulative(Adapter<T, ReliabilityFunction> functionTransformer, double epsilon) {
		this.functionTransformer = functionTransformer;
		this.epsilon = epsilon;
	}

	@Override
	public ReliabilityFunction convert(BDD<T> model) {
		List<Double> samples = collectTimesToFailure(model, 5000);
		return new SampledReliabilityFunction(samples);
	}

	/**
	 * Collects all times-to-failure to derive {@code n} samples needed to
	 * calculate the {@code ReliabilityFunction}.
	 * 
	 * @param term
	 *            the term to convert
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @param existsPredicate
	 *            the element to exists predicate
	 * @param n
	 *            the number of samples
	 * @return the reliability function
	 */
	public List<Double> collectTimesToFailure(BDD<T> bdd, int n) {
		BDDProvider<T> provider = bdd.getProvider();
		List<Double> times = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			Double time = simulateTimeToFailure(bdd, provider);
			times.add(time);
		}
		return times;
	}

	/**
	 * Performs a single simulation run to gather one time-to-failure.
	 * 
	 * @param bdd
	 *            the given bdd
	 * @param provider
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @return a single time-to-failure
	 */
	protected double simulateTimeToFailure(BDD<T> bdd, BDDProvider<T> provider) {
		double time = 0;
		BDD<T> myBDD = bdd.copy();
		Set<Failure<T>> failures = getFailures(bdd);
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
	 * Generates the {@code Failure} {@code Occurrences} for a single simulation
	 * run based on the given {@code BDD} and the {@code ReliabilityFunctions}
	 * of its elements.
	 * 
	 * @param bdd
	 *            the given bdd
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @return the failure occurrences for a single simulation run
	 */
	protected Set<Failure<T>> getFailures(BDD<T> bdd) {
		SortedSet<Failure<T>> failureTimes = new TreeSet<Failure<T>>();
		Set<T> elements = bdd.getVariables();
		for (T element : elements) {
			ReliabilityFunction relFunction = functionTransformer.transform(element);
			InverseFunction inverse = new InverseFunction(relFunction);
			double x = inverse.getY(random.nextDouble());
			Failure<T> failureTime = new Failure<T>(element, x);
			failureTimes.add(failureTime);
		}
		return failureTimes;
	}

}
