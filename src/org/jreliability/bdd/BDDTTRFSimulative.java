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
 * The {@code BDDTTRFSimulative} performs a {@code Monte-Carlo simulation} to
 * determine the {@code ReliabilityFunction} based on a {@code BDD}
 * representation of the system structure..
 * 
 * @author glass
 * 
 */
public class BDDTTRFSimulative implements TTRF {

	/**
	 * The used maximum error / {@code epsilon} value for the simulation.
	 */
	protected final double epsilon;

	/**
	 * The used random number generator to trigger the simulation.
	 */
	protected final Random random = new Random(System.currentTimeMillis());

	/**
	 * The used {@code BDDProvider}.
	 */
	protected final BDDProvider<Object> provider;

	/**
	 * Constructs a {@code BDDTTRFSimulative} with a given {@code BDDProvider}
	 * and a standard epsilon of {@code 0.001}.
	 * 
	 * @param provider
	 *            the used bddProvider
	 */
	public BDDTTRFSimulative(BDDProvider<Object> provider) {
		this(provider, 0.001);
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
	public BDDTTRFSimulative(BDDProvider<Object> provider, double epsilon) {
		this.provider = provider;
		this.epsilon = epsilon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(Term, Transformer)
	 */
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer) {
		return convert(term, functionTransformer, null);
	}

	/**
	 * Converts a given {@code Term} to a {@code ReliabilityFunction} based on
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
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer, int j) {
		return convert(term, functionTransformer, null, j);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(Term, Transformer,
	 * Transformer)
	 */
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer,
			Predicate<Object> existsPredicate) {
		List<Double> samples = collectTimesToFailure(term, functionTransformer,
				existsPredicate);
		return new SampledReliabilityFunction(samples);
	}

	/**
	 * Converts a given {@code Term} to a {@code ReliabilityFunction} based on
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
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer,
			Predicate<Object> existsPredicate, int j) {
		List<Double> samples = collectTimesToFailure(term, functionTransformer,
				existsPredicate, j);
		return new SampledReliabilityFunction(samples);
	}

	/**
	 * Collects all times-to-failure to derive {@code 5000} samples needed to
	 * calculate the {@code ReliabilityFunction}.
	 * 
	 * @param term
	 *            the term to convert
	 * @param functionTransformer
	 *            the element to reliability function transformer
	 * @param existsPredicate
	 *            the element to exists predicate
	 * @return the reliability function
	 */
	public List<Double> collectTimesToFailure(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer,
			Predicate<Object> existsPredicate) {
		return collectTimesToFailure(term, functionTransformer,
				existsPredicate, 5000);
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
	public List<Double> collectTimesToFailure(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer,
			Predicate<Object> existsPredicate, int n) {
		List<Double> times = new ArrayList<Double>();
		BDDTTRF bddTTRF = new BDDTTRF(provider);
		BDD<Object> bdd = bddTTRF.convertToBDD(term, existsPredicate);
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
	protected double simulateTimeToFailure(BDD<Object> bdd,
			Transformer<Object, ReliabilityFunction> functionTransformer) {
		double time = 0;
		BDD<Object> myBDD = bdd.copy();
		Set<Failure<Object>> failures = getFailures(bdd, functionTransformer);
		for (Failure<Object> failure : failures) {
			BDD<Object> objectVariable = provider.get(failure.getObject());
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
	protected Set<Failure<Object>> getFailures(BDD<Object> bdd,
			Transformer<Object, ReliabilityFunction> functionTransformer) {
		SortedSet<Failure<Object>> failureTimes = new TreeSet<Failure<Object>>();
		Set<Object> elements = bdd.getVariables();
		for (Object element : elements) {
			ReliabilityFunction relFunction = functionTransformer
					.transform(element);
			InverseFunction inverse = new InverseFunction(relFunction);
			double x = inverse.getY(random.nextDouble());
			Failure<Object> failureTime = new Failure<Object>(element, x);
			failureTimes.add(failureTime);
		}
		return failureTimes;
	}

}
