/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.function.Function;

/**
 * The {@link IntegralEvaluator} determines the integral of a {@link Function}
 * using Romberg's method.
 * 
 * @author lukasiewycz
 * 
 */
public class IntegralEvaluator {

	/**
	 * The allowed error / {@code epsilon} for Romberg's method.
	 */
	protected final double epsilon;

	/**
	 * Constructs the {@link IntegralEvaluator} with the epsilon value
	 * {@code 1.0E-8}.
	 */
	public IntegralEvaluator() {
		this(1.0E-8);
	}

	/**
	 * Constructs the {@link IntegralEvaluator}.
	 * 
	 * @param epsilon
	 *            the allowed error for Romberg's method.
	 */
	public IntegralEvaluator(double epsilon) {
		super();
		this.epsilon = epsilon;
	}

	/**
	 * Calculates the integral from {@code a} to {@code b}.
	 * 
	 * @param f
	 *            the function to be integrated
	 * @param a
	 *            the lower bound
	 * @param b
	 *            the upper bound
	 * @return the value of the integral from {@code a} to {@code b}
	 */
	public double evaluate(Function f, double a, double b) {
		double error;
		double r;

		class Table {
			protected List<List<Double>> tab = new ArrayList<>();

			public double get(int n, int m) {
				return tab.get(n).get(m);
			}

			public void set(int n, int m, double value) {
				if (tab.size() == n) {
					tab.add(new ArrayList<Double>());
				}
				List<Double> row = tab.get(n);
				assert (row.size() == m);
				row.add(value);
			}
		}

		Table R = new Table();

		double fa = f.getY(a);
		double fb = f.getY(b);
		double h1 = h(1, a, b);
		r = h1 * (fa + fb);

		R.set(0, 0, r);

		int n = 1;
		while (true) {
			double hn = h(n, a, b);

			double sum = 0.0;
			for (int k = 1; k <= Math.pow(2, n - 1); k++) {
				double x = a + (2 * k - 1) * hn;
				sum += f.getY(x);
			}

			r = 0.5 * R.get(n - 1, 0) + hn * sum;
			R.set(n, 0, r);

			for (int m = 1; m <= n; m++) {
				r = R.get(n, m - 1) + (R.get(n, m - 1) - R.get(n - 1, m - 1)) / Math.pow(4, m - 1);
				R.set(n, m, r);
			}

			error = Math.abs(R.get(n, n) - R.get(n, n - 1));
			if (error < epsilon) {
				return R.get(n, n);
			}
			n++;
		}
	}

	private double h(int n, double a, double b) {
		return (b - a) / Math.pow(2, n);
	}
}
