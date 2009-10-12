package org.jreliability.tutorial.boiler;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.BDDs;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.function.ReliabilityFunction;
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
		Term term = boiler.getTerm();
		System.out.println("The Term:");
		System.out.println(term);
		System.out.println("The BDD:");
		// For this example, a BDD is generated first to visualize the BDD of
		// the Boiler
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Object> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF bddTTRF = new BDDTTRF(bddProvider);
		BDD<Object> bdd = bddTTRF.convertToBDD(term);
		String dot = BDDs.toDot(bdd);
		System.out.println(dot);
		ReliabilityFunction reliabilityFunctionBoiler = boiler.get();

		// Using the GUI
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<String, ReliabilityFunction>();
		reliabilityFunctions.put("Boiler", reliabilityFunctionBoiler);

		ReliabilityViewer.view("JReliability Viewer - Boiler Tutorial",
				reliabilityFunctions);

	}

}
