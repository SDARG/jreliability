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

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.function.common.SimpleFunctionTransformer;

/**
 * The {@link TMR} models a 2-out-of-3 majority voter commonly known as Triple
 * Modular Redundancy. This behavior can be expressed as a linear constrained as
 * follows:<br>
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
	protected String component1 = "component1";
	/**
	 * The second component.
	 */
	protected String component2 = "component2";
	/**
	 * The third component.
	 */
	protected String component3 = "component3";

	/**
	 * The used {@link ReliabilityFunction} {@link Transformer}.
	 */
	protected Transformer<String, ReliabilityFunction> transformer;

	/**
	 * Constructs a {@link TMR}.
	 * 
	 */
	public TMR() {
		super();
		initialize();
	}

	/**
	 * Initializes the {@link Transformer} of the TMR.
	 */
	private void initialize() {
		Map<String, ReliabilityFunction> reliabilityFunctions = new HashMap<>();
		ReliabilityFunction function = new ExponentialReliabilityFunction(0.1);
		reliabilityFunctions.put(component1, function);
		reliabilityFunctions.put(component2, function);
		reliabilityFunctions.put(component3, function);
		transformer = new SimpleFunctionTransformer<>(reliabilityFunctions);
	}

	/**
	 * Returns a model of the {@link TMR} as a {@link Term}.
	 * 
	 * @return the term representation of the TMR
	 */
	public Term getTerm() {

		LinearTerm term = new LinearTerm(Comparator.GREATEREQUAL, 2);

		term.add(1, new LiteralTerm<>(component1));
		term.add(1, new LiteralTerm<>(component2));
		term.add(1, new LiteralTerm<>(component3));

		return term;
	}

	/**
	 * Returns {@link ReliabilityFunction} describing the {@link TMR} using the
	 * {@link BDDTTRF}.
	 * 
	 * @return the reliabilityFunction of the TMR
	 */
	public ReliabilityFunction get() {
		Term term = getTerm();
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<String> bddTTRF = new BDDTTRF<>(bddProvider);
		return bddTTRF.convert(term, transformer);
	}

	/**
	 * Returns a {@link Transformer} for each element of the system to its
	 * {@link ReliabilityFunction}.
	 * 
	 * @return the transformer
	 */
	public Transformer<String, ReliabilityFunction> getTransformer() {
		return transformer;
	}

}
