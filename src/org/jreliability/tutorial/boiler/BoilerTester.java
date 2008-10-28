package org.jreliability.tutorial.boiler;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDs;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.BDDReliabilityFunction;
import org.jreliability.gui.ReliabilityViewer;

/**
 * The {@code BoilerTester} performs some common actions that are done with a
 * modeled system.
 * 
 * @author glass
 * 
 */
public class BoilerTester {

	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Boiler boiler = new Boiler();
		BDD<BoilerElement> boilerBDD = boiler.get();

		// Visualizing the BDD
		String dot = BDDs.toDot(boilerBDD);
		System.out.println(dot);
		System.out.println("***");

		BoilerTransformer transformer = boiler.getTransformer();

		BDDReliabilityFunction<BoilerElement> reliabilityFunction = new BDDReliabilityFunction<BoilerElement>(
				boilerBDD, transformer);

		// Using Evaluators
		// Calculate Mean-Time-To-Failure (the first moment of the density
		// function)
		MomentEvaluator moment = new MomentEvaluator(1);
		Double mttf = moment.evaluate(reliabilityFunction);
		System.out.println("Mean-Time-To-Failure: " + mttf);
		System.out.println("***");

		// Using the GUI
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<String, ReliabilityFunction>();
		reliabilityFunctions.put("Boiler", reliabilityFunction);

		ReliabilityViewer.view("JReliability Viewer - Boiler Tutorial",
				reliabilityFunctions, true);

	}

}
