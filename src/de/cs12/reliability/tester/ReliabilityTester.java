package de.cs12.reliability.tester;

import java.util.Map;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.common.Samples;
import de.cs12.reliability.distribution.Distribution;
import de.cs12.reliability.evaluator.SamplingEvaluator;
import de.cs12.reliability.gui.ReliabilityViewer;

public class ReliabilityTester {

	public static void main(String[] args) {
		TestExample example = new TestExample();

		Map<String, Distribution> distributions = example.getDistributions();
		BDD<String> bdd = example.get();

		SamplingEvaluator<String> evaluator = new SamplingEvaluator<String>(bdd);
		Samples samples = evaluator.getValues(distributions, 300);

		new ReliabilityViewer("Reliability Viewer", samples);

	}

}
