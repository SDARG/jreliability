/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.tester;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.gui.ReliabilityViewer;

/**
 * The {@link ReliabilityTester} is a basic tester that uses the {@link
 * TestExample} to launch the {@link ReliabilityViewer}.
 * 
 * @author glass
 * 
 */
public class ReliabilityTester {

	/**
	 * Constructs a {@link ReliabilityTester}.
	 */
	public ReliabilityTester() {
		super();
	}

	/**
	 * Main.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		TestExample example = new TestExample();
		Term bdd = example.get();

		Transformer<String, ReliabilityFunction> exponentialTransformer = new TestExponentialTransformer();
		Transformer<String, ReliabilityFunction> weibullTransformer = new TestWeibullTransformer();

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> transformer = new BDDTTRF<>(bddProvider);
		ReliabilityFunction exponentialDistribution = transformer.convert(bdd, exponentialTransformer);
		ReliabilityFunction weibullDistribution = transformer.convert(bdd, weibullTransformer);

		Map<String, ReliabilityFunction> reliabilityFunction = new HashMap<>();
		reliabilityFunction.put("Exponential", exponentialDistribution);
		reliabilityFunction.put("Weibull", weibullDistribution);

		ReliabilityViewer.view("JReliability Viewer", reliabilityFunction);

	}

}
