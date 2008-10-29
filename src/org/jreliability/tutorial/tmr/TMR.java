package org.jreliability.tutorial.tmr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDs;
import org.jreliability.function.FunctionTransformer;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.SimpleFunctionTransformer;
import org.jreliability.javabdd.JBDDProviderFactory;

/**
 * The {@code TMR} models a {@code 2-out-of-3} majority voter commonly known as
 * {@code TripleModularRedundancy}. This behavior can be expressed as a linear
 * constrained as follows:
 * <p>
 * {@code 1*component1 + 1*component2 + 1* component3 >= 2}
 * <p>
 * The needed comparator to perform the voting is commonly not modeled
 * explicitly due to its extremely high reliability.
 * 
 * @author glass
 * 
 */
public class TMR {

	/**
	 * The first component.
	 */
	protected String component1 = new String("component1");
	/**
	 * The second component.
	 */
	protected String component2 = new String("component2");
	/**
	 * The third component.
	 */
	protected String component3 = new String("component3");

	/**
	 * The used {@code FunctionTransformer}.
	 */
	protected FunctionTransformer<String> transformer;

	/**
	 * Constructs a {@code TMR}.
	 * 
	 */
	public TMR() {
		super();
		initialize();
	}

	/**
	 * Initializes the {@code FunctionTransformer} of the TMR.
	 */
	private void initialize() {
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<String, ReliabilityFunction>();
		ReliabilityFunction function = new ExponentialReliabilityFunction(0.1);
		reliabilityFunctions.put(component1, function);
		reliabilityFunctions.put(component2, function);
		reliabilityFunctions.put(component3, function);
		transformer = new SimpleFunctionTransformer<String>(
				reliabilityFunctions);
	}

	/**
	 * Returns a model of the {@code TMR} as a {@code BDD}.
	 * 
	 * @return the bdd representation of the TMR
	 */
	public BDD<String> get() {

		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();

		BDD<String> component1BDD = bddProvider.get(component1);
		BDD<String> component2BDD = bddProvider.get(component2);
		BDD<String> component3BDD = bddProvider.get(component3);

		// To use the inbuilt constraint functionality, setup the left-hand-side
		// first

		List<Integer> coefficients = new ArrayList<Integer>();
		List<BDD<String>> variables = new ArrayList<BDD<String>>();

		coefficients.add(1);
		variables.add(component1BDD);

		coefficients.add(1);
		variables.add(component2BDD);

		coefficients.add(1);
		variables.add(component3BDD);

		BDD<String> tmr = BDDs.getBDD(coefficients, variables, ">=", 2);

		return tmr;
	}

	/**
	 * Returns the {@code FunctionTransformer}.
	 * 
	 * @return the transformer
	 */
	public FunctionTransformer<String> getTransformer() {
		return transformer;
	}

}
