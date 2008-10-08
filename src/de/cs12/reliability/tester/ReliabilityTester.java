package de.cs12.reliability.tester;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.common.Samples;
import de.cs12.reliability.distribution.Distribution;
import de.cs12.reliability.evaluator.SamplingEvaluator;
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
		TestExample example = new TestExample();

		Map<String, Distribution> exponentialDistributions = example
				.getExponentialDistributions();
		Map<String, Distribution> weibullDistributions = example
				.getWeibullDistributions();

		BDD<String> bdd = example.get();
		SamplingEvaluator<String> evaluator = new SamplingEvaluator<String>(bdd);

		Samples exponentialSamples = evaluator.getValues(
				exponentialDistributions, 300);
		Samples weibullSamples = evaluator.getValues(weibullDistributions, 300);

		SortedMap<String, Samples> samples = new TreeMap<String, Samples>();
		samples.put("Exponential", exponentialSamples);
		samples.put("Weibull", weibullSamples);

		new ReliabilityViewer("Reliability Viewer", samples);

	}

}
