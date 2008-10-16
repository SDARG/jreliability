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

import java.util.ArrayList;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code MomentEvaluator} determines the {@code n}-th {@code Moment} of a
 * density function {@code f(x)} given a {@code ReliabilityFunction} {@code
 * R(x)}.
 * <p>
 * {@code integral_0^infinity x^n f(x) dx}.
 * <p>
 * It performs an integration from {@code 0} to {@code infinity} using Rombergs
 * integration. This is commonly used to derived measures like, e.g., Mean Time
 * To Failure (MTTF) (n = 1), standard deviation (n = 2) and so on.
 * 
 * @author glass
 * 
 */
public class MomentEvaluator implements Evaluator {

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
	 * and a maximum error / {@code epsilon} of {@code 0.01}.
	 * 
	 * @param n
	 *            the n value
	 */
	public MomentEvaluator(int n) {
		this(n, 0.01);
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
		if (n < 1) {
			throw new IllegalArgumentException(
					"An n-th moment with n < 1 is undefined.");
		}
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
		if (n < 1) {
			throw new IllegalArgumentException(
					"An n-th moment with n < 1 is undefined.");
		}
		double upperBound = 1.0;
		double prefix = n * Math.pow(upperBound, (n - 1));
		double diff = prefix * reliabilityFunction.getY(upperBound);
		while (diff > 0.01) {
			upperBound *= 2;
			diff = prefix * reliabilityFunction.getY(upperBound);
		}
		return upperBound;

	}

	/**
	 * Calculates the integral between low and high using Rombergs integration.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * @param low
	 *            the lower bound
	 * @param high
	 *            the upper bound
	 * @return the value of the integral between low and high
	 */
	protected double integrate(ReliabilityFunction reliabilityFunction,
			double low, double high) {
		double error;

		ArrayList<ArrayList<Double>> rTable = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> line = new ArrayList<Double>(1);
		rTable.add(line);

		double lowPrefix = n * Math.pow(low, (n - 1));
		double highPrefix = n * Math.pow(high, (n - 1));
		double lowY = lowPrefix * reliabilityFunction.getY(low);
		double highY = highPrefix * reliabilityFunction.getY(high);

		double borderVal = lowY + highY;
		double gap = high - low;

		int j = 1;

		rTable.get(0).add(0, (gap / 2) * borderVal);

		do {
			ArrayList<Double> j_line = new ArrayList<Double>(j + 1);
			rTable.add(j_line);

			double div = Math.pow(2, j);

			// Calculate 1st value of the line with the trapezium rule
			double sum = 0.0;
			for (int i = 1; i < div; i++) {
				double x = low + (i * gap) / div;
				double xPrefix = n * Math.pow(x, (n - 1));
				sum += xPrefix * reliabilityFunction.getY(x);
			}
			rTable.get(j).add(0, (gap / (2 * div)) * (borderVal + 2 * sum));

			// the rest of the reliabilityFunctions with Neville-Aitken
			for (int k = 1; k <= j; k++) {
				double fourPow = Math.pow(2, (2 * k));

				rTable.get(j).add(
						k,
						((fourPow * rTable.get(j).get(k - 1)) - rTable.get(
								j - 1).get(k - 1))
								/ (fourPow - 1));
			}

			error = rTable.get(j - 1).get(j - 1) - rTable.get(j).get(j - 1);
			j++;
		} while (error > epsilon);
		return rTable.get(j - 1).get(j - 1);
	}

}
