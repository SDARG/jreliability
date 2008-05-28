package de.cs12.reliability;

import java.util.ArrayList;
import java.util.Map;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code IntegralEvaluator} performs an integration of the {@code BDDs} top
 * event from {@code 0} to {@code infinity} using Rombergs integration. This is
 * commonly used to derived measures like, e.g., Mean Time To Failure (MTTF).
 * 
 * @author glass
 * @param <T>
 *            the type of the variables
 * 
 */
public class IntegralEvaluator<T> extends AbstractEvaluator<T> {

	protected final double epsilon;

	/**
	 * Constructs a {@code IntegralEvaluator} with a {@code BDD} and a maximum
	 * error of {@code 0.01}.
	 * 
	 * @param bdd
	 *            the bdd
	 */
	public IntegralEvaluator(BDD<T> bdd) {
		this(bdd, 0.01);
	}

	/**
	 * Constructs a {@code IntegralEvaluator} with a {@code BDD} and a maximum
	 * error {@code epsilon}.
	 * 
	 * @param bdd
	 *            the bdd
	 * @param epsilon
	 *            the maximum error
	 */
	public IntegralEvaluator(BDD<T> bdd, double epsilon) {
		super(bdd);
		this.epsilon = epsilon;
	}

	/**
	 * Returns the value derived from an integration of the given {@code BDD}.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @return the value derived from an integration of the given BDD
	 */
	public double getValue(Map<T, Distribution> distributions) {
		if (bdd.isOne()) {
			return 1.0;
		}
		if (bdd.isZero()) {
			return 0.0;
		}

		double upperBound = 1.0;
		double diff = calculateTop(distributions, upperBound);
		while (diff > 0.1) {
			upperBound *= 2;
			diff = calculateTop(distributions, upperBound);
		}

		return integrate(distributions, 0, upperBound);

	}

	/**
	 * Calculates the integral between low and high using Rombergs integration.
	 * 
	 * @param distributions
	 *            the distribution of each variable
	 * @param low
	 *            the lower bound
	 * @param high
	 *            the upper bound
	 * @return the value of the integral between low and high
	 */
	private double integrate(Map<T, Distribution> distributions, double low,
			double high) {

		double error;
		ArrayList<ArrayList<Double>> rTable = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> line = new ArrayList<Double>(1);

		rTable.add(line);

		double borderVal = calculateTop(distributions, low)
				+ calculateTop(distributions, high);
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
				sum += calculateTop(distributions, low + (i * gap) / div);
			}
			rTable.get(j).add(0, (gap / (2 * div)) * (borderVal + 2 * sum));

			// the rest of the values with Neville-Aitken
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
