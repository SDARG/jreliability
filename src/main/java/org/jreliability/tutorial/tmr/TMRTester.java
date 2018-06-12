/*******************************************************************************
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
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/
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
 * The {@link TMRTester} performs some common actions that are done with a
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
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProvider);
		BDD<String> bdd = bddTTRF.convertToBDD(term);
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
