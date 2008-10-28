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

import java.util.List;

import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code Sampler} is responsible to collect {@code Samples} of a {@code
 * ReliabilityFunction} using whatever kind of {@code Evaluator}. Each {@code
 * Sampler} invokes one or several {@code Evaluators} and transforms the
 * interesting data into a single list of sampled values.
 * 
 * @author glass
 * 
 */
public interface Sampler {

	/**
	 * Returns the list of values for the {@code ReliabilityFunction} derived by
	 * the the current {@code Sampler}.
	 * 
	 * @param reliabilityFunction
	 *            the reliabilityFunction
	 * 
	 * @return the list of values for the reliability function derived by the
	 *         current sampler
	 */
	public List<Double> getSamples(ReliabilityFunction reliabilityFunction);

	/**
	 * Returns the name of the values that are derived by the {@code Sampler}.
	 * 
	 * @return the name of the values derived by the sampler
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
