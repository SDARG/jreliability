package de.cs12.reliability.function;

/**
 * The {@code AbstractDistribution}
 * 
 * @author glass
 * 
 */
public abstract class AbstractDistribution implements Distribution {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Distribution#getX(double)
	 */
	@Override
	public double getX(double y) {
		double epsilon = 1.0 / 10000;
		double x = 1;
		double tmpY = getY(x);

		if (tmpY < (y - epsilon)) {
			return bisection(y, 0.0, x, epsilon);
		} else if (tmpY > (y + epsilon)) {
			return bisection(y, x, 2.0, epsilon);
		} else {
			return x;
		}
	}

	/**
	 * Performs a bisection approach to determine the {@code x} for
	 * {@code y = f(x)}.
	 * 
	 * @param y
	 *            the y value
	 * @param low
	 *            the current lower bound of the interval
	 * @param high
	 *            the current upper bound of the interval
	 * @param epsilon
	 *            the epsilon value
	 * @return x for y = f(x)
	 */
	protected double bisection(double y, double low, double high, double epsilon) {
		double x = high - ((high - low) / 2);
		double tmpY = getY(x);

		if (tmpY < (y - epsilon)) {
			return bisection(y, low, x, epsilon);
		} else if (tmpY > (y + epsilon)) {
			return bisection(y, x, high, epsilon);
		} else {
			return x;
		}

	}

}
