package de.cs12.reliability.distribution;

import de.cs12.reliability.Distribution;

/**
 * The {@code Constant} represents a constant value with
 * <p>
 * {@code f(x) = c}.
 * 
 * @author glass
 * 
 */
public class Constant implements Distribution {

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
	 * @see de.cs12.reliability.bdd.reliability.Distribution#getY(double)
	 */
	public double getY(double x) {
		return c;
	}

}
