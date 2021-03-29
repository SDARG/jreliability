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

package org.jreliability.tester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.evaluator.MomentEvaluator;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.WeibullReliabilityFunction;
import org.jreliability.importancemeasures.ABGT;
import org.jreliability.importancemeasures.BAGT;
import org.jreliability.importancemeasures.BarlowProschan;
import org.jreliability.importancemeasures.BirnbaumAB;
import org.jreliability.importancemeasures.Birnbaum;
import org.jreliability.importancemeasures.RAW;
import org.jreliability.importancemeasures.RRW;
import org.jreliability.importancemeasures.TimeDependentImportanceMeasure;
import org.jreliability.importancemeasures.Vaurio;
import org.jreliability.testsystems.BridgeSystem;
import org.jreliability.testsystems.SeriesParallelSystem;
import org.jreliability.testsystems.TCNCSystem;
import org.jreliability.testsystems.TINCSystem;
import org.jreliability.testsystems.TMR;

/**
 * This file is used to create importance measure data in order to plot
 * and compare them to the results and graphs in [Ali17]. The failure rates
 * for the example systems are the same as in [Ali17] unless noted otherwise.
 * 
 * The data is exported into seperate .csv files for each importance measure to
 * allow for customized plotting. 
 * 
 * [Ali17] ( https://nbn-resolving.org/urn:nbn:de:bvb:29-opus4-87185 )
 * 
 * @author oehmen
 */
public class PlotDataForImportanceMeasuresCreator {
	
	private final String OUTPUT_PATH = "plotdata/";
	
	@SuppressWarnings("unused")
	private void bagt() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProvider);
		Map<String, Double> results;
		BDD<String> bdd;
		BAGT<String> im;
				
		
		System.out.println("\nTMR:");
		TMR tmr = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  new ExponentialReliabilityFunction(0.02), 
				  		  new ExponentialReliabilityFunction(0.03));	
		bdd = bddTTRF.convertToBDD(tmr.getTerm());		
		im = new BAGT<String>(bdd, tmr.getTransformer());
		
		results = im.calculate(BAGT.Variant.MINUS);
		System.out.println("BAGT_MINUS: " + results);
//		results = im.calculate(BAGT_IM.Variant.PLUS);
//		System.out.println("BAGT_PLUS: " + results);
		
		
		System.out.println("\nBridge:");	
		BridgeSystem bridge = new BridgeSystem(new WeibullReliabilityFunction(0.02, 6),
											   new WeibullReliabilityFunction(0.04, 8),
											   new WeibullReliabilityFunction(0.06, 6),
											   new WeibullReliabilityFunction(0.08, 5),
											   new WeibullReliabilityFunction(0.1, 8));
		bdd = bddTTRF.convertToBDD(bridge.getTerm());		
		im = new BAGT<String>(bdd, bridge.getTransformer());
		
		results = im.calculate(BAGT.Variant.MINUS);
		System.out.println("BAGT_MINUS: " + results);
//		results = im.calculate(BAGT_IM.Variant.PLUS);
//		System.out.println("BAGT_PLUS: " + results);
		
		
		System.out.println("\nTC_NC:");
		TCNCSystem tcnc = new TCNCSystem();
		bdd = bddTTRF.convertToBDD(tcnc.getTerm());		
		im = new BAGT<String>(bdd, tcnc.getTransformer());

		results = im.calculate(BAGT.Variant.MINUS);
		System.out.println("BAGT_MINUS: " + results);
//		results = im.calculate(BAGT_IM.Variant.PLUS);
//		System.out.println("BAGT_PLUS: " + results);
		
		

		System.out.println("\nTI_NC:");
		TINCSystem tinc = new TINCSystem();
		bdd = bddTTRF.convertToBDD(tinc.getTerm());		
		im = new BAGT<String>(bdd, tinc.getTransformer());

		results = im.calculate(BAGT.Variant.MINUS);
		System.out.println("BAGT_MINUS: " + results);
//		results = im.calculate(BAGT_IM.Variant.PLUS);
//		System.out.println("BAGT_PLUS: " + results);
		
	}

	@SuppressWarnings("unused")
	private void barlowProschan() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProvider);
		Map<String, Double> results;
		BDD<String> bdd;
		BarlowProschan<String> im;
		
		System.out.println("BarlowProschan:");
		
		System.out.println("TMR:");
		TMR tmr = new TMR(new ExponentialReliabilityFunction(0.01), 
				  		  new ExponentialReliabilityFunction(0.02), 
				  		  new ExponentialReliabilityFunction(0.03));
		bdd = bddTTRF.convertToBDD(tmr.getTerm());		
		im = new BarlowProschan<String>(bdd, tmr.getTransformer());
		
		results = im.calculate();
		System.out.println(results.toString() + '\n');
	}

	@SuppressWarnings("unused")
	private void tmr() throws IOException {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		/* The exponential failure rates have been taken from Page 77 of [Ali17] */
		TMR system = new TMR(new ExponentialReliabilityFunction(0.01), 
								  new ExponentialReliabilityFunction(0.02), 
								  new ExponentialReliabilityFunction(0.03));
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
	
		Birnbaum<String> b_im = new Birnbaum<>(bdd, system.getTransformer());		
		sampleTDIMToFile(b_im, "B_IM.csv", 350, 5);

		BirnbaumAB<String> bab_im = new BirnbaumAB<>(bdd, system.getTransformer());		
		sampleTDIMToFile(bab_im, "BAB_IM.csv", 350, 5);
		
		ABGT<String> abgt_im = new ABGT<>(bdd, system.getTransformer());		
		sampleTDIMToFile(abgt_im, "ABGT_IM.csv", 350, 5);
		
		Vaurio<String> vaurio_im = new Vaurio<>(bdd, system.getTransformer());		
		sampleTDIMToFile(vaurio_im, "V_IM.csv", 350, 5);

		RAW<String> raw_im = new RAW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(raw_im, "RAW_IM.csv", 10, 0.05);

		RRW<String> rrw_im = new RRW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(rrw_im, "RRW_IM.csv", 200, 1);

		
		System.out.println("TMR:");		
		double mttf = new MomentEvaluator(1).evaluate(bddTTRF.convert(bdd.copy(), system.getTransformer()));
		System.out.println("System MTTF: " + mttf);

		BAGT<String> bagt_im = new BAGT<String>(bdd, system.getTransformer());
		System.out.println("BAGT-: " + bagt_im.calculate(BAGT.Variant.MINUS));
		System.out.println("BAGT+: " + bagt_im.calculate(BAGT.Variant.PLUS));
		
		BarlowProschan<String> bp_im = new BarlowProschan<String>(bdd, system.getTransformer());
		System.out.println("BaPr:  " + bp_im.calculate().toString() + '\n');
	}
	
	@SuppressWarnings("unused")
	private void bridgeSystem() throws IOException {
		/**
		 * Implements a coherent bridge system with Weibull failure times.
		 *  
		 * The structure function and failure rates have been taken from [Ali17] in order
		 * to compare results for the importance measures: 
		 * Structure function: Equation 4.39
		 * Failure rates: 	   Page 79
		 * 
		 * [Ali17] https://nbn-resolving.org/urn:nbn:de:bvb:29-opus4-87185		 *
		 */
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		BridgeSystem system = new BridgeSystem(new WeibullReliabilityFunction(0.02, 6), 	// [Ali17] C1
											   new WeibullReliabilityFunction(0.06, 6), 	// [Ali17] C3
											   new WeibullReliabilityFunction(0.04, 8),		// [Ali17] C2
											   new WeibullReliabilityFunction(0.08, 5),		// [Ali17] C4
											   new WeibullReliabilityFunction(0.1, 8));		// [Ali17] C5
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		MomentEvaluator moment = new MomentEvaluator(1);
		double mttf = moment.evaluate(bddTTRF.convert(bdd.copy(), system.getTransformer()));	
		System.out.println("Bridge System MTTF: " + mttf);

		Birnbaum<String> b_im = new Birnbaum<>(bdd, system.getTransformer());		
		sampleTDIMToFile(b_im, "B_IM.csv", 2.6, 0.05);
		
		BirnbaumAB<String> bab_im = new BirnbaumAB<>(bdd, system.getTransformer());		
		sampleTDIMToFile(bab_im, "BAB_IM.csv", 2.6, 0.05);
		
		ABGT<String> ABGT_IM = new ABGT<>(bdd, system.getTransformer());		
		sampleTDIMToFile(ABGT_IM, "ABGT_IM.csv", 2.6, 0.05);

		RAW<String> raw_im = new RAW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(raw_im, "RAW_IM.csv", 2, 0.05);

		RRW<String> rrw_im = new RRW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(rrw_im, "RRW_IM.csv", 2.4, 0.05);		
	}

	@SuppressWarnings("unused")
	private void tcncsSystem() throws IOException {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TCNCSystem system = new TCNCSystem();
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());		
		
		Birnbaum<String> b_im = new Birnbaum<>(bdd, system.getTransformer());		
		sampleTDIMToFile(b_im, "B_IM.csv", 200, 2);		

		BirnbaumAB<String> bab_im = new BirnbaumAB<>(bdd, system.getTransformer());		
		sampleTDIMToFile(bab_im, "BAB_IM.csv", 200, 2);

		ABGT<String> ABGT_IM = new ABGT<>(bdd, system.getTransformer());		
		sampleTDIMToFile(ABGT_IM, "ABGT_IM.csv", 200, 2);
		
		Vaurio<String> vaurio_im = new Vaurio<>(bdd, system.getTransformer());		
		sampleTDIMToFile(vaurio_im, "V_IM.csv", 200, 2);

		RAW<String> raw_im = new RAW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(raw_im, "RAW_IM.csv", 100, 1);

		RRW<String> rrw_im = new RRW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(rrw_im, "RRW_IM.csv", 100, 1);
		
		
		System.out.println("TCNC:");		
		double mttf = new MomentEvaluator(1).evaluate(bddTTRF.convert(bdd.copy(), system.getTransformer()));
		System.out.println("System MTTF: " + mttf);

		BAGT<String> bagt_im = new BAGT<String>(bdd, system.getTransformer());
		System.out.println("BAGT-: " + bagt_im.calculate(BAGT.Variant.MINUS));
//		System.out.println("BAGT+: " + bagt_im.calculate(BAGT.Variant.PLUS));
	}

	@SuppressWarnings("unused")
	private void tincSystem() throws IOException {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		TINCSystem system = new TINCSystem();
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());		

		Birnbaum<String> b_im = new Birnbaum<>(bdd, system.getTransformer());		
		sampleTDIMToFile(b_im, "B_IM.csv", 100, 1);		

		BirnbaumAB<String> bab_im = new BirnbaumAB<>(bdd, system.getTransformer());		
		sampleTDIMToFile(bab_im, "BAB_IM.csv", 100, 1);

		ABGT<String> ABGT_IM = new ABGT<>(bdd, system.getTransformer());		
		sampleTDIMToFile(ABGT_IM, "ABGT_IM.csv", 100, 1);
	
		Vaurio<String> vaurio_im = new Vaurio<>(bdd, system.getTransformer());		
		sampleTDIMToFile(vaurio_im, "V_IM.csv", 100, 1);

		RAW<String> raw_im = new RAW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(raw_im, "RAW_IM.csv", 30, 0.2);

		RRW<String> rrw_im = new RRW<String>(bdd, system.getTransformer());
		sampleTDIMToFile(rrw_im, "RRW_IM.csv", 50, 0.1);
		
		
		System.out.println("TINC:");		
		double mttf = new MomentEvaluator(1).evaluate(bddTTRF.convert(bdd.copy(), system.getTransformer()));
		System.out.println("System MTTF: " + mttf);

		BAGT<String> bagt_im = new BAGT<String>(bdd, system.getTransformer());
		System.out.println("BAGT-: " + bagt_im.calculate(BAGT.Variant.MINUS));
	}
	
	@SuppressWarnings("unused")
	private void introductionPresentationData() throws IOException {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProviderFactory.getProvider());
		
		ReliabilityFunction rf1 = new ExponentialReliabilityFunction(0.1);
		ReliabilityFunction rf2 = new ExponentialReliabilityFunction(0.08);
		ReliabilityFunction rf3 = new ExponentialReliabilityFunction(0.01);
		
		SeriesParallelSystem system = new SeriesParallelSystem(rf1, rf2, rf3);
		BDD<String> bdd = bddTTRF.convertToBDD(system.getTerm());
		
		Birnbaum<String> b_im = new Birnbaum<>(bdd, system.getTransformer());		
		sampleTDIMToFile(b_im, "B_IM.csv", 350, 5);
		
		
		Map<String, ReliabilityFunction> functions = new HashMap<>();		
		functions.put("system", bddTTRF.convert(bdd.copy(), system.getTransformer()));
		functions.put("c1", rf1);
		functions.put("c2", rf2);
		functions.put("c3", rf3);		
		sampleReliabilitiesToFile(functions, "Reliability.csv", 350, 5);

		double mttf = new MomentEvaluator(1).evaluate(bddTTRF.convert(bdd.copy(), system.getTransformer()));
		System.out.println("System MTTF: " + mttf);		
	}
	
	private void sampleTDIMToFile(TimeDependentImportanceMeasure<String> im, String filename, double timeBound, double timeStep) throws IOException {
		File outputFile = new File(OUTPUT_PATH + File.separator + filename);
		outputFile.getParentFile().mkdirs();
		FileWriter fileWriter = new FileWriter(outputFile);
		
		boolean firstIteration = true;		
		String output = "";
		
		for (double time = timeStep; time <= timeBound; time += timeStep) {
			Map<String, Double> results = im.calculate(time);

			if (firstIteration) {
				firstIteration = false;

				output = "time; ";
				output += results.entrySet().stream().sorted(Map.Entry.comparingByKey()).
			    		map(entry -> entry.getKey().toString()).collect(Collectors.joining("; "));
				
		    	fileWriter.write(output + "\n");
			}
			
	    	output = "" + time + "; ";
	    	output += results.entrySet().stream().sorted(Map.Entry.comparingByKey()).
	    		map(entry -> entry.getValue().toString()).collect(Collectors.joining("; "));
	    	
	    	fileWriter.write(output + "\n");
	    }				
		fileWriter.close();
	}
	
	private void sampleReliabilitiesToFile(Map<String, ReliabilityFunction> functions, String filename, double timeBound, double timeStep) throws IOException {
		File outputFile = new File(OUTPUT_PATH + File.separator + filename);
		outputFile.getParentFile().mkdirs();
		FileWriter fileWriter = new FileWriter(outputFile);
		
		boolean firstIteration = true;		
		String output = "";
		
		for (double time = 0; time <= timeBound; time += timeStep) {
			Map<String, Double> results = new HashMap<>();
			
			for (Map.Entry<String, ReliabilityFunction> entry : functions.entrySet()) {
				results.put(entry.getKey(), entry.getValue().getY(time));
		    }

			if (firstIteration) {
				firstIteration = false;

				output = "time; ";
				output += results.entrySet().stream().sorted(Map.Entry.comparingByKey()).
			    		map(entry -> entry.getKey().toString()).collect(Collectors.joining("; "));
				
		    	fileWriter.write(output + "\n");
			}
			
	    	output = "" + time + "; ";
	    	output += results.entrySet().stream().sorted(Map.Entry.comparingByKey()).
		    		map(entry -> entry.getValue().toString()).collect(Collectors.joining("; "));
	    	
	    	fileWriter.write(output + "\n");
	    }				
		fileWriter.close();
	}
	
	public static void main(String[] args) {
		PlotDataForImportanceMeasuresCreator plotData = new PlotDataForImportanceMeasuresCreator();
		
		/* These are time-independent and their results are written to the console */
		plotData.bagt();
//		plotData.barlowProschan();
		
		try {
			/* Only one of these should be active, as the resulting .csv files do not differentiate
			 * between test systems, thus later executions overwrite previous results. */
			plotData.tmr();
//			plotData.bridgeSystem();
//			plotData.tcncsSystem();
//			plotData.tincSystem();
//			plotData.introductionPresentationData();
		}
		catch(IOException e) { 
			System.out.println("Writing to file failed.");
		}
		System.out.println("Done.");
	}
}
