package de.cs12.reliability.gui;

import de.cs12.reliability.common.Samples;
import de.cs12.reliability.function.Function;

/**
 * The {@code Aspect} allows to determine the {@code Samples} of a
 * {@code Function} under the current {@code Aspect}.
 * 
 * @author glass
 * 
 */
public interface Aspect {

	/**
	 * Returns {@code 500} functions in an auto-generated interval for the
	 * {@code Function} under the current {@code Aspect}.
	 * 
	 * @param function
	 *            the function
	 * 
	 * @return the functions for the current aspect
	 */
	public Samples getSamples(Function function);

	/**
	 * Returns the functions in an interval from {@code low} to {@code high} with
	 * of distance {@code step} for the {@code Function} under the current
	 * {@code Aspect}.
	 * 
	 * @param function
	 *            the function
	 * @param low
	 *            the lower bound of the interval
	 * @param high
	 *            the upper bound of the interval
	 * @param step
	 *            the distance between two sampled points
	 * @return the functions for the current aspect
	 */
	public Samples getSamples(Function function, double low, double high,
			double step);

	/**
	 * Returns the name of the {@code Aspect}.
	 * 
	 * @return the name of the aspect
	 */
	public String getName();

	/**
	 * Returns the label for the {@code x-axis}.
	 * 
	 * @return the label for the x-axis
	 */
	public String getXAxis();

	/**
	 * Returns the label for the {@code x-axis}.
	 * 
	 * @return the label for the y-axis
	 */
	public String getYAxis();
}
