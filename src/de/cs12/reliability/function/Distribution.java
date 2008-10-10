package de.cs12.reliability.function;

/**
 * The {@code Distribution} is an interface to represent functions that are also
 * distributions in the mathematical sense.
 * 
 * @author glass
 * 
 */
public interface Distribution extends Function {

	/**
	 * Returns an {@code x} value for {@code y = f(x)}.
	 * 
	 * @param y
	 *            the y value
	 * @return an x value for the given y value
	 */
	public double getX(double y);

}
