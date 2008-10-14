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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDs;
import de.cs12.reliability.function.BDDDistribution;
import de.cs12.reliability.function.Function;
import de.cs12.reliability.function.FunctionTransformer;
import de.cs12.reliability.gui.Aspect;
import de.cs12.reliability.gui.DensityAspect;
import de.cs12.reliability.gui.DistributionAspect;
import de.cs12.reliability.gui.FailureRateAspect;
import de.cs12.reliability.gui.ReliabilityViewer;

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
		List<Aspect> aspects = new ArrayList<Aspect>();
		aspects.add(new DistributionAspect());
		aspects.add(new DensityAspect());
		aspects.add(new FailureRateAspect());

		TestExample example = new TestExample();
		BDD<String> bdd = example.get();
		System.out.println(BDDs.toDot(bdd));

		FunctionTransformer<String> exponentialTransformer = new TestExponentialTransformer();
		FunctionTransformer<String> weibullTransformer = new TestWeibullTransformer();

		BDDDistribution<String> exponentialDistribution = new BDDDistribution<String>(
				bdd, exponentialTransformer);
		BDDDistribution<String> weibullDistribution = new BDDDistribution<String>(
				bdd, weibullTransformer);

		SortedMap<String, Function> functions = new TreeMap<String, Function>();
		functions.put("Exponential", exponentialDistribution);
		functions.put("Weibull", weibullDistribution);

		new ReliabilityViewer("Reliability Viewer", functions, aspects);

	}

}
