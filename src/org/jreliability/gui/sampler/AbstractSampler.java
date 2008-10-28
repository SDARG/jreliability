/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.gui.sampler;

/**
 * The {@code AbstractSampler} is the basic class of all {@code Samplers}.
 * 
 * @author glass
 * 
 */
public abstract class AbstractSampler implements Sampler {

	/**
	 * The name of the {@code Samples} derived by the {@code Sampler}.
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
	 * Constructs an {@code AbstractSampler} with a given name and labels for
	 * the x-axis and y-axis.
	 * 
	 * @param name
	 *            the name of the samples
	 * @param xAxis
	 *            the label of the x-axis
	 * @param yAxis
	 *            the label of the y-axis
	 */
	public AbstractSampler(String name, String xAxis, String yAxis) {
		super();
		this.name = name;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.sampler.Sampler#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.sampler.Sampler#getXAxis()
	 */
	public String getXAxis() {
		return xAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.sampler.Sampler#getYAxis()
	 */
	public String getYAxis() {
		return yAxis;
	}

}
