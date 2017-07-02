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

import org.jreliability.function.Function;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code MomentEvaluator} determines the {@code n}-th {@code Moment} of a
 * density function {@code f(x)} given a {@code ReliabilityFunction} {@code
 * R(x)}.
 * <p>
 * E(X^n)={@code integral_0^infinity x^n f(x) dx}.
 * <p>
 * It performs an integration from {@code 0} to {@code infinity} using Rombergs
 * integration. This is commonly used to derived measures like, e.g., Mean Time
 * To Failure (MTTF) (E(X)) and its variance (E(X^2)-E(X)^2).
 * 
 * @author glass, lukasiewycz
 * 
 */
public class MomentEvaluator implements Evaluator {

	/**
	 * The moment function.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class MomentFunction implements Function {

		private ReliabilityFunction reliabilityFunction;
		private int n;

		/**
		 * Constructs the moment function.
		 * 
		 * @param reliabilityFunction
		 *            the reliability function
		 * @param n
		 *            the moment
		 */
		public MomentFunction(ReliabilityFunction reliabilityFunction, int n) {
			this.reliabilityFunction = reliabilityFunction;
			this.n = n;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jreliability.function.Function#getY(double)
		 */
		public double getY(double x) {
			return n * Math.pow(x, n - 1) * reliabilityFunction.getY(x);
		}

	}

	/**
	 * The allowed error / {@code epsilon} for Rombergs integration.
	 */
	protected final double epsilon;

	/**
	 * The {@code n}-th moment.
	 */
	protected final int n;

	/**
	 * Constructs a {@code MomentEvaluator} for the given {@code n}-th moment
	 * and a maximum error / {@code epsilon} of {@code 1.0E-5}.
	 * 
	 * @param n
	 *            the n value
	 */
	public MomentEvaluator(int n) {
		this(n, 1.0E-5);
	}

	/**
	 * Constructs a {@code MomentEvaluator} for the given {@code n}-th moment
	 * and a maximum error {@code epsilon}.
	 * 
	 * @param n
	 *            the n value
	 * 
	 * @param epsilon
	 *            the maximum error
	 * 
	 */
	public MomentEvaluator(int n, double epsilon) {
		super();
		this.n = n;
		this.epsilon = epsilon;
		if (n < 1) {
			throw new IllegalArgumentException(
					"An n-th moment with n < 1 is undefined.");
		}
	}

	/**
	 * Returns the value derived from an integration of the {@code
	 * ReliabilityFunction}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @return the value derived from the integration of the reliabilityFunction
	 */
	public double evaluate(ReliabilityFunction reliabilityFunction) {
		double upperBound = getUpperBound(reliabilityFunction);
		return integrate(reliabilityFunction, 0, upperBound);

	}

	/**
	 * Returns the calculated upper bound that will be used in the integration
	 * process.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @return the calculated upper bound that will be used in the integration
	 *         process
	 */
	public double getUpperBound(ReliabilityFunction reliabilityFunction) {
		double upperBound = 0.5;
		double diff;

		Function momentFunction = new MomentFunction(reliabilityFunction, n);

		do {
			upperBound *= 2;
			diff = momentFunction.getY(upperBound);
		} while (diff > epsilon);

		return upperBound;

	}

	/**
	 * Calculates the integral between a and b using Rombergs integration.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @param a
	 *            the lower bound
	 * @param b
	 *            the upper bound
	 * @return the value of the integral between a and b
	 */
	protected double integrate(ReliabilityFunction reliabilityFunction,
			double a, double b) {
		Function f = new MomentFunction(reliabilityFunction, n);

		IntegralEvaluator integral = new IntegralEvaluator(epsilon);
		double value = integral.evaluate(f, a, b);
		return value;
	}

}
