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

import org.jreliability.evaluator.FailureSimulativeEvaluator;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.BDDReliabilityFunction;

/**
 * The {@code TTFFrequencyDistributionSampler} uses the {@code
 * FailureSimulativeEvaluator} to get all the single {@code times-to-failure}
 * that have been determined in the sampling process. Given these values, a
 * {@code frequency distribution} of the {@code times-to-failure} be be
 * visualized using a {@code Histogram}.
 * 
 * @author glass
 * 
 */
public class TTFFrequencyDistributionSampler extends AbstractSampler {

	/**
	 * Constructs a {@code TTFFrequencyDistributionSampler}.
	 * 
	 */
	public TTFFrequencyDistributionSampler() {
		super("Sampled TTF Frequency Distribution", "time t",
				"number of times to failure");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jreliability.gui.sampler.Sampler#getSamples(org.jreliability.function
	 * .ReliabilityFunction)
	 */
	@SuppressWarnings("unchecked")
	public List<Double> getSamples(ReliabilityFunction reliabilityFunction) {
		List<Double> times = null;
		if (reliabilityFunction instanceof BDDReliabilityFunction) {
			BDDReliabilityFunction<Object> bddFunction = (BDDReliabilityFunction<Object>) reliabilityFunction;
			FailureSimulativeEvaluator<Object> evaluator = new FailureSimulativeEvaluator<Object>();
			times = evaluator.collectTimesToFailure(bddFunction);
		}
		return times;
	}
}
