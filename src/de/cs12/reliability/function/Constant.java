package de.cs12.reliability.function;

/**
 * The {@code Constant} represents a constant value with
 * <p>
 * {@code f(x) = c}.
 * 
 * @author glass
 * 
 */
public class Constant implements Function {

	/**
	 * The used constant value.
	 */
	protected final double c;

	/**
	 * Constructs a {@code Constant} with a constant value {@code c}.
	 * 
	 * @param c
	 *            the constant value
	 */
	public Constant(double c) {
		this.c = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	public double getY(double x) {
		return c;
	}

}
