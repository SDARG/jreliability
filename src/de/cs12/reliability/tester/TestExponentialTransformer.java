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
package de.cs12.reliability.tester;

import java.util.HashMap;
import java.util.Map;

import de.cs12.reliability.function.Distribution;
import de.cs12.reliability.function.ExponentialDistribution;
import de.cs12.reliability.function.Function;
import de.cs12.reliability.function.FunctionTransformer;

/**
 * The {@code TestExponentialTransformer} is a {@code Transformer} for the
 * {@code TestExample} that uses {@code ExponentialDistributions} as {@code
 * Functions}.
 * 
 * @author glass
 * 
 */
public class TestExponentialTransformer implements FunctionTransformer<String> {

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
	 * Maps each element to its {@code Function}.
	 */
	protected Map<String, Function> functions = new HashMap<String, Function>();
	/**
	 * The {@code Distribution} of the heater.
	 */
	protected Distribution heaterDistribution = new ExponentialDistribution(0.5);
	/**
	 * The {@code Distribution} of the first pump.
	 */
	protected Distribution pump1Distribution = new ExponentialDistribution(1);
	/**
	 * The {@code Distribution} of the second pump.
	 */
	protected Distribution pump2Distribution = new ExponentialDistribution(1);

	/**
	 * Constructs a {@code TestExponentialTransformer}.
	 * 
	 */
	public TestExponentialTransformer() {
		functions.put(heater, heaterDistribution);
		functions.put(pump1, pump1Distribution);
		functions.put(pump2, pump2Distribution);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.common.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Function transform(String a) {
		return functions.get(a);
	}

}
