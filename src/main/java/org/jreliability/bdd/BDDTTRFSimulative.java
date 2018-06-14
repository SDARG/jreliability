/*******************************************************************************
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
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.TTRF;
import org.jreliability.booleanfunction.Term;
import org.jreliability.common.Failure;
import org.jreliability.function.InverseFunction;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.SampledReliabilityFunction;

/**
 * The {@link BDDTTRFSimulative} performs a Monte-Carlo simulation to determine
 * the {@link ReliabilityFunction} based on a {@link BDD} representation of the
 * system structure.
 * 
 * @author glass
 * 
 * @param <T>
 *            the elements of the bdd
 */
public class BDDTTRFSimulative<T> implements TTRF<T> {

	/**
	 * The used maximum error {@code epsilon} value for the simulation.
	 */
	protected final double epsilon;

	/**
	 * The used random number generator to trigger the simulation.
	 */
	protected final Random random = new Random(System.currentTimeMillis());

	/**
	 * The used {@link BDDProvider}.
	 */
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@link BDDTTRFSimulative} with a given {@link BDDProvider}
	 * and a standard epsilon of {@code 0.001}.
	 * 
	 * @param provider
	 *            the used bddProvider
	 */
	public BDDTTRFSimulative(BDDProvider<T> provider) {
		this(provider, 0.001);
	}

	/**
	 * Constructs a {@link BDDTTRFSimulative} with a given {@link BDDProvider}
	 * and an epsilon.
	 * 
	 * @param provider
	 *            the used bddProvider
	 * @param epsilon
	 *            the used epsilon value
	 */
	public BDDTTRFSimulative(BDDProvider<T> provider, double epsilon) {
		this.provider = provider;
		this.epsilon = epsilon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(Term, Transformer)
	 */
	@Override
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer) {
		return convert(term, functionTransformer, null);
	}

	/**
	 * Converts a given {@link Term} to a {@link ReliabilityFunction} based on
	 * {@code j}-samples.
	 * 
	 * @param term
	 *            the term to convert
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @param j
	 *            the number of samples to use
	 * @return the reliability function
	 */
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer, int j) {
		return convert(term, functionTransformer, null, j);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(org.jreliability.
	 * booleanfunction.Term, org.apache.commons.collections15.Transformer,
	 * org.apache.commons.collections15.Predicate)
	 */
	@Override
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer,
			Predicate<T> existsPredicate) {
		List<Double> samples = collectTimesToFailure(term, functionTransformer, existsPredicate);
		return new SampledReliabilityFunction(samples);
	}

	/**
	 * Converts a given {@link Term} to a {@link ReliabilityFunction} based on
	 * {@code j}-samples.
	 * 
	 * @param term
	 *            the term to convert
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @param existsPredicate
	 *            the element to exists predicate
	 * @param j
	 *            the number of samples to use
	 * @return the reliability function
	 */
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer,
			Predicate<T> existsPredicate, int j) {
		List<Double> samples = collectTimesToFailure(term, functionTransformer, existsPredicate, j);
		return new SampledReliabilityFunction(samples);
	}

	/**
	 * Collects all times-to-failure to derive {@code 5000} samples needed to
	 * calculate the {@link ReliabilityFunction}.
	 * 
	 * @param term
	 *            the term to convert
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @param existsPredicate
	 *            the element to exists predicate
	 * @return the reliability function
	 */
	public List<Double> collectTimesToFailure(Term term, Transformer<T, ReliabilityFunction> functionTransformer,
			Predicate<T> existsPredicate) {
		return collectTimesToFailure(term, functionTransformer, existsPredicate, 5000);
	}

	/**
	 * Collects all times-to-failure to derive {@code n} samples needed to
	 * calculate the {@link ReliabilityFunction}.
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
	public List<Double> collectTimesToFailure(Term term, Transformer<T, ReliabilityFunction> functionTransformer,
			Predicate<T> existsPredicate, int n) {
		List<Double> times = new ArrayList<>();
		BDDTTRF<T> bddTTRF = new BDDTTRF<>(provider);
		BDD<T> bdd = bddTTRF.convertToBDD(term, existsPredicate);
		for (int i = 0; i < n; i++) {
			Double time = simulateTimeToFailure(bdd, functionTransformer);
			times.add(time);
		}
		return times;
	}

	/**
	 * Performs a single simulation run to gather one time-to-failure.
	 * 
	 * @param bdd
	 *            the given bdd
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @return a single time-to-failure
	 */
	protected double simulateTimeToFailure(BDD<T> bdd, Transformer<T, ReliabilityFunction> functionTransformer) {
		double time = 0;
		BDD<T> myBDD = bdd.copy();
		Set<Failure<T>> failures = getFailures(bdd, functionTransformer);
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
	 * Generates the {@link Failure} occurrences for a single simulation run
	 * based on the given {@link BDD} and the {@link ReliabilityFunction}s of
	 * its elements.
	 * 
	 * @param bdd
	 *            the given bdd
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @return the failure occurrences for a single simulation run
	 */
	protected Set<Failure<T>> getFailures(BDD<T> bdd, Transformer<T, ReliabilityFunction> functionTransformer) {
		SortedSet<Failure<T>> failureTimes = new TreeSet<>();
		Set<T> elements = bdd.getVariables();
		for (T element : elements) {
			ReliabilityFunction relFunction = functionTransformer.transform(element);
			InverseFunction inverse = new InverseFunction(relFunction);
			double x = inverse.getY(random.nextDouble());
			Failure<T> failureTime = new Failure<>(element, x);
			failureTimes.add(failureTime);
		}
		return failureTimes;
	}

}
