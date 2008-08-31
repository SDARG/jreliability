package de.cs12.reliability.distribution;

/**
 * The {@code Inverse} interface describes {@code Distributions} where an
 * inverse function is available.
 * 
 * @author glass
 * 
 */
public interface Inverse extends Distribution {

	/**
	 * Returns an {@code x} value for {@code y = f(x)}.
	 * 
	 * @param y
	 *            the y value
	 * @return an x value for the given y value
	 */
	public double getX(double y);

}
