package de.cs12.reliability.gui;

import de.cs12.reliability.function.Function;

/**
 * The {@code AbstractAspect} is the basic class for all {@code Aspects}.
 * 
 * @author glass
 * 
 */
public abstract class AbstractAspect implements Aspect {

	/**
	 * The name of the {@code Aspect}.
	 */
	protected final String name;

	/**
	 * The label of the x-axis.
	 */
	protected final String xAxis;

	/**
	 * The label of the y-axis.
	 */
	protected final String yAxis;

	/**
	 * The standard lower bound is {@code 0}.
	 */
	protected double lower = 0;

	/**
	 * Constructs an {@code AbstractAspect} with a given name and labels for the
	 * x-axis and y-axis.
	 * 
	 * @param name
	 *            the name of the aspect
	 * @param xAxis
	 *            the label of the x-axis
	 * @param yAxis
	 *            the label of the y-axis
	 */
	public AbstractAspect(String name, String xAxis, String yAxis) {
		super();
		this.name = name;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getXAxis()
	 */
	@Override
	public String getXAxis() {
		return xAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getYAxis()
	 */
	@Override
	public String getYAxis() {
		return yAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getLower(de.cs12.reliability.function.Function)
	 */
	@Override
	public double getLower(Function function) {
		return lower;
	}

}
