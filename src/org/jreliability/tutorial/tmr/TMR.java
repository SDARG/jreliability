package org.jreliability.tutorial.tmr;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.SimpleFunctionTransformer;

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
	protected Transformer<Object, ReliabilityFunction> transformer;

	/**
	 * Constructs a {@code TMR}.
	 * 
	 */
	public TMR() {
		super();
		initialize();
	}

	/**
	 * Initializes the {@code Transformer} of the TMR.
	 */
	private void initialize() {
		Map<Object, ReliabilityFunction> reliabilityFunctions = new HashMap<Object, ReliabilityFunction>();
		ReliabilityFunction function = new ExponentialReliabilityFunction(0.1);
		reliabilityFunctions.put(component1, function);
		reliabilityFunctions.put(component2, function);
		reliabilityFunctions.put(component3, function);
		transformer = new SimpleFunctionTransformer<Object>(
				reliabilityFunctions);
	}

	/**
	 * Returns a model of the {@code TMR} as a {@code Term}.
	 * 
	 * @return the term representation of the TMR
	 */
	public Term getTerm() {

		LinearTerm term = new LinearTerm(Comparator.GREATEREQUAL, 2);

		term.add(1, new LiteralTerm(component1));
		term.add(1, new LiteralTerm(component2));
		term.add(1, new LiteralTerm(component3));

		return term;
	}

	/**
	 * Returns {@code ReliabilityFunction} describing the {@code TMR} using the
	 * {@code BDDTTRF}.
	 * 
	 * @return the reliabilityFunction of the TMR
	 */
	public ReliabilityFunction get() {
		Term term = getTerm();
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Object> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF bddTTRF = new BDDTTRF(bddProvider);
		return bddTTRF.convert(term, transformer);
	}

	/**
	 * Returns a {@code Transformer} for each element of the system to its
	 * {@code ReliabilityFunction}.
	 * 
	 * @return the transformer
	 */
	public Transformer<Object, ReliabilityFunction> getTransformer() {
		return transformer;
	}

}
