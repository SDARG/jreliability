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

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDReliabilityFunction;
import org.jreliability.common.Transformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.gui.ReliabilityViewer;

/**
 * The {@code ReliabilityTester} is a basic tester that uses the {@code
 * TestExample} to launch the {@code ReliabilityViewer}.
 * 
 * @author glass
 * 
 */
public class ReliabilityTester {

	/**
	 * Constructs a {@code ReliabilityTester}.
	 */
	public ReliabilityTester() {
		super();
	}

	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TestExample example = new TestExample();
		BDD<String> bdd = example.get();

		Transformer<String, ReliabilityFunction> exponentialTransformer = new TestExponentialTransformer();
		Transformer<String, ReliabilityFunction> weibullTransformer = new TestWeibullTransformer();

		BDDReliabilityFunction<String> exponentialDistribution = new BDDReliabilityFunction<String>(bdd,
				exponentialTransformer);
		BDDReliabilityFunction<String> weibullDistribution = new BDDReliabilityFunction<String>(bdd, weibullTransformer);

		Map<String, ReliabilityFunction> reliabilityFunction = new HashMap<String, ReliabilityFunction>();
		reliabilityFunction.put("Exponential", exponentialDistribution);
		reliabilityFunction.put("Weibull", weibullDistribution);

		ReliabilityViewer.view("JReliability Viewer", reliabilityFunction);

	}

}
