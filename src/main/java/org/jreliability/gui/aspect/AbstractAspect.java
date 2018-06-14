/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.gui.aspect;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link AbstractAspect} is the basic class for all {@link Aspect}s.
 * 
 * @author glass
 * 
 */
public abstract class AbstractAspect implements Aspect {

	/**
	 * The name of the {@link Aspect}.
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
	 * Constructs an {@link AbstractAspect} with a given name and labels for the
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
	 * @see org.jreliability.gui.Aspect#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getXAxis()
	 */
	@Override
	public String getXAxis() {
		return xAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getYAxis()
	 */
	@Override
	public String getYAxis() {
		return yAxis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.gui.Aspect#getLower(org.jreliability.function.
	 * Function)
	 */
	@Override
	public double getLower(ReliabilityFunction reliabilityFunction) {
		return lower;
	}

}
