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

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.adapter.TermToBDDAdapter;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.cra.Adapter;
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
		TestExample example = new TestExample();
		Term term = example.get();

		Adapter<String, ReliabilityFunction> exponentialTransformer = new TestExponentialTransformer();
		Adapter<String, ReliabilityFunction> weibullTransformer = new TestWeibullTransformer();

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		TermToBDDAdapter<String> adapter = new TermToBDDAdapter<String>(bddProvider);

		BDD<String> bdd = adapter.transform(term);
		BDD<String> bdd2 = bdd.copy();
		BDDTTRF<String> bddtexponentialrf = new BDDTTRF<String>(exponentialTransformer);
		ReliabilityFunction exponentialDistribution = bddtexponentialrf.convert(bdd);

		BDDTTRF<String> bddtweibullrf = new BDDTTRF<String>(weibullTransformer);
		ReliabilityFunction weibullDistribution = bddtweibullrf.convert(bdd2);

		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<String, ReliabilityFunction>();
		reliabilityFunctions.put("Exponential", exponentialDistribution);
		reliabilityFunctions.put("Weibull", weibullDistribution);

		ReliabilityViewer.view("JReliability Viewer", reliabilityFunctions);

	}
}
