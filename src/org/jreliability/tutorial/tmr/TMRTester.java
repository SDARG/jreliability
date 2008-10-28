package org.jreliability.tutorial.tmr;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDs;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.FunctionTransformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.BDDReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.gui.ReliabilityViewer;

/**
 * The {@code TMRTester} performs some common actions that are done with a
 * modeled system.
 * 
 * @author glass
 * 
 */
public class TMRTester {

	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		TMR tmr = new TMR();
		BDD<String> tmrBDD = tmr.get();

		// Visualizing the BDD
		String dot = BDDs.toDot(tmrBDD);
		System.out.println(dot);
		System.out.println("***");

		FunctionTransformer<String> transformer = tmr.getTransformer();

		BDDReliabilityFunction<String> reliabilityFunctionTMR = new BDDReliabilityFunction<String>(
				tmrBDD, transformer);

		// The single element solution equals a simple
		// ExponentiaRreliabilityFunction
		ReliabilityFunction reliabilityFunctionSingle = new ExponentialReliabilityFunction(
				0.1);

		// Calculate Mean-Time-To-Failures (the first moment of the density
		// function)
		MomentEvaluator moment = new MomentEvaluator(1);
		Double mttfTMR = moment.evaluate(reliabilityFunctionTMR);
		System.out.println("Mean-Time-To-Failure of TMR: " + mttfTMR);
		Double mttfSingle = moment.evaluate(reliabilityFunctionSingle);
		System.out.println("Mean-Time-To-Failure of single element: "
				+ mttfSingle);
		System.out.println("***");

		// Using the GUI
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<String, ReliabilityFunction>();
		reliabilityFunctions.put("TMR", reliabilityFunctionTMR);
		reliabilityFunctions.put("Single Element", reliabilityFunctionSingle);

		ReliabilityViewer.view("JReliability Viewer - TMR Tutorial",
				reliabilityFunctions);

	}

}
