package de.cs12.reliability.evaluator;

import java.util.ArrayList;

import de.cs12.reliability.function.Function;

/**
 * The {@code IntegralEvaluator} performs an integration of the {@code Function}
 * from {@code 0} to {@code infinity} using Rombergs integration. This is
 * commonly used to derived measures like, e.g., Mean Time To Failure (MTTF).
 * 
 * @author glass
 * 
 */
public class IntegralEvaluator implements Evaluator {

	/**
	 * The allowed error / {@code epsilon} for Rombergs integration.
	 */
	protected final double epsilon;

	/**
	 * Constructs an {@code IntegralEvaluator} with a maximum error /
	 * {@code epsilon} of {@code 0.01}.
	 * 
	 */
	public IntegralEvaluator() {
		this(0.01);
	}

	/**
	 * Constructs an {@code IntegralEvaluator} with a maximum error
	 * {@code epsilon}.
	 * 
	 * @param epsilon
	 *            the maximum error
	 */
	public IntegralEvaluator(double epsilon) {
		super();
		this.epsilon = epsilon;
	}

	/**
	 * Returns the value derived from an integration of the {@code Function}.
	 * 
	 * @param function
	 *            the function
	 * @return the value derived from the integration of the function
	 */
	public double evaluate(Function function) {
		double upperBound = 1.0;
		double diff = function.getY(upperBound);
		while (diff > 0.1) {
			upperBound *= 2;
			diff = function.getY(upperBound);
		}

		return integrate(function, 0, upperBound);

	}

	/**
	 * Returns the calculated upper bound that will be used in the integration
	 * process.
	 * 
	 * @param function
	 *            the function
	 * @return the calculated upper bound that will be used in the integration
	 *         process
	 */
	public double getUpperBound(Function function) {
		double upperBound = 1.0;
		double diff = function.getY(upperBound);
		while (diff > 0.01) {
			upperBound *= 2;
			diff = function.getY(upperBound);
		}
		return upperBound;

	}

	/**
	 * Calculates the integral between low and high using Rombergs integration.
	 * 
	 * @param function
	 *            the function
	 * @param low
	 *            the lower bound
	 * @param high
	 *            the upper bound
	 * @return the value of the integral between low and high
	 */
	protected double integrate(Function function, double low, double high) {

		double error;
		ArrayList<ArrayList<Double>> rTable = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> line = new ArrayList<Double>(1);

		rTable.add(line);

		double borderVal = function.getY(low) + function.getY(high);
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
				sum += function.getY(low + (i * gap) / div);
			}
			rTable.get(j).add(0, (gap / (2 * div)) * (borderVal + 2 * sum));

			// the rest of the functions with Neville-Aitken
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
