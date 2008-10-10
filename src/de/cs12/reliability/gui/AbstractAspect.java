package de.cs12.reliability.gui;

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
	 * The number of functions that are created when automatic sampling is used.
	 */
	protected final int numberOfSamples = 500;

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

}
