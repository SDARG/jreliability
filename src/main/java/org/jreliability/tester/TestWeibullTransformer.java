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
package org.jreliability.tester;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;

/**
 * The {@code TestWeibullTransformer} is a {@code Transformer} for the {@code
 * TestExample} that uses {@code WeibullReliabilityFunctions} as {@code
 * ReliabilityFunctions}.
 * 
 * @author glass
 * 
 */
public class TestWeibullTransformer implements Transformer<String, ReliabilityFunction> {

	/**
	 * The heater.
	 */
	protected final String heater = "Heater";
	/**
	 * The first pump.
	 */
	protected final String pump1 = "Pump 1";
	/**
	 * The second pump.
	 */
	protected final String pump2 = "Pump 2";

	/**
	 * Maps each element to its {@code ReliabilityFunction}.
	 */
	protected Map<String, ReliabilityFunction> reliabilityFunction = new HashMap<String, ReliabilityFunction>();
	/**
	 * The {@code ReliabilityFunction} of the heater.
	 */
	ReliabilityFunction heaterReliabilityFunction = new WeibullReliabilityFunction(3, 5);
	/**
	 * The {@code ReliabilityFunction} of the first pump.
	 */
	ReliabilityFunction pump1ReliabilityFunction = new WeibullReliabilityFunction(1, 3);
	/**
	 * The {@code ReliabilityFunction} of the second pump.
	 */
	ReliabilityFunction pump2ReliabilityFunction = new WeibullReliabilityFunction(1.2, 3);

	/**
	 * Constructs a {@code TestWeibullTransformer}.
	 * 
	 */
	public TestWeibullTransformer() {
		reliabilityFunction.put(heater, heaterReliabilityFunction);
		reliabilityFunction.put(pump1, pump1ReliabilityFunction);
		reliabilityFunction.put(pump2, pump2ReliabilityFunction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.common.Transformer#transform(java.lang.Object)
	 */
	public ReliabilityFunction transform(String a) {
		return reliabilityFunction.get(a);
	}

}
