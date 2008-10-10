package de.cs12.reliability.function;

/**
 * The {@code Function} represents an arbitrary mathematical function.
 * 
 * @author glass
 * 
 */
public interface Function {

	/**
	 * Returns the {@code y} value for {@code y = f(x)}.
	 * 
	 * @param x
	 *            the x value
	 * @return the y for y = f(x)
	 */
	public double getY(double x);

}
