package org.jreliability.tutorial.tmr;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.BDDs;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.gui.ReliabilityViewer;

/**
 * The {@code TMRTester} performs some common actions that are done with a modeled system.
 * 
 * @author glass
 * 
 */
public class TMRTester {

	/**
	 * Main.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		TMR tmr = new TMR();
		Term term = tmr.getTerm();
		System.out.println("The Term:");
		System.out.println(term);
		System.out.println("The BDD:");

		// For this example, a BDD is generated first to visualizing the BDD of
		// an MTTR
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Object> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF bddTTRF = new BDDTTRF(bddProvider);
		BDD<Object> bdd = bddTTRF.convertToBDD(term);
		String dot = BDDs.toDot(bdd);
		System.out.println(dot);
		System.out.println("***");

		// The more interesting part is the usage of the ReliabilityFunctions:
		ReliabilityFunction reliabilityFunctionTMR = tmr.get();

		// The single element solution equals a simple
		// ExponentiaRreliabilityFunction
		ReliabilityFunction reliabilityFunctionSingle = new ExponentialReliabilityFunction(0.1);

		// Calculate Mean-Time-To-Failures (the first moment of the density
		// function)
		MomentEvaluator moment = new MomentEvaluator(1);
		Double mttfTMR = moment.evaluate(reliabilityFunctionTMR);
		System.out.println("Mean-Time-To-Failure of TMR: " + mttfTMR);
		Double mttfSingle = moment.evaluate(reliabilityFunctionSingle);
		System.out.println("Mean-Time-To-Failure of single element: " + mttfSingle);
		System.out.println("***");

		// Using the GUI
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<>();
		reliabilityFunctions.put("TMR", reliabilityFunctionTMR);
		reliabilityFunctions.put("Single Component", reliabilityFunctionSingle);

		ReliabilityViewer.view("JReliability Viewer - TMR Tutorial", reliabilityFunctions);

	}

}
