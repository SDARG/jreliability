/**
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.tester;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.adapter.TermToBDDAdapter;
import org.jreliability.bdd.crn.BDDtoReliabilityFunction;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.cra.CompositeAnalysis;
import org.jreliability.cra.Node;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.gui.ReliabilityViewer;

/**
 * The {@code ReliabilityTester} is a basic tester that uses the
 * {@code TestExample} to launch the {@code ReliabilityViewer}.
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
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();

		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<String, ReliabilityFunction>();
		CompositeAnalysis analysis = new CompositeAnalysis();

		// Node bddcopy = new BDDCopy();
		Node bddttrf = new BDDtoReliabilityFunction<String>();
		analysis.connect(new TestExample(), new TermToBDDAdapter<String>(bddProvider), // bddcopy,
				bddttrf);
		analysis.connect(new TestExponentialTransformer(), bddttrf);
		String id1 = "Exponential";
		analysis.setTargetID(bddttrf, id1);

		// Node bddttrf2 = new BDDTTRF<String>();
		// analysis.connect(bddcopy, bddttrf2);
		// analysis.connect(new TestWeibullTransformer(), bddttrf2);
		// String id2 = "Weibull";
		// analysis.setTargetID(bddttrf2, id2);

		Map<String, ReliabilityFunction> reliabilityFunctions2 = new HashMap<String, ReliabilityFunction>();
		ReliabilityFunction reliabilityFunction = analysis.<ReliabilityFunction> get(id1);
		reliabilityFunctions.put(id1, reliabilityFunction);

		ReliabilityViewer.view("JReliability Viewer2", reliabilityFunctions);
	}
}
