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

package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.common.StructureFunction;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.SequentialFunction;

/**
 * The {@link BDDReliabilityFunction} represents the {@link ReliabilityFunction}
 * that is inherently included in a {@link BDD}.
 * 
 * @author glass
 * 
 * @param <T> the type of variable
 */
public class BDDReliabilityFunction<T> extends SequentialFunction implements ReliabilityFunction, StructureFunction<T> {

	/**
	 * The BDD representing the {@link ReliabilityFunction}.
	 */
	protected final BDD<T> bdd;

	/**
	 * The used {@link Transformer} to get the {@link ReliabilityFunction} of each
	 * element of the {@link BDD}.
	 */
	protected final Transformer<T, ReliabilityFunction> functionTransformer;

	/**
	 * The calculator for the top event.
	 */
	protected final BDDTopEvent<T> topEvent;

	/**
	 * Constructs a {@link BDDReliabilityFunction} with a given {@link BDD} and
	 * {@link Transformer}.
	 * 
	 * @param bdd                 the bdd representing the reliabilityFunction
	 * 
	 * @param functionTransformer the functionTransformer to transform bdd elements
	 *                            to reliabilityFunction
	 */
	public BDDReliabilityFunction(BDD<T> bdd, Transformer<T, ReliabilityFunction> functionTransformer) {
		super();
		this.bdd = bdd;
		this.functionTransformer = functionTransformer;
		this.topEvent = new BDDTopEvent<>(bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(final double x) {

		final Transformer<T, Double> t = new Transformer<T, Double>() {
			/**
			 * Default {@link Transformer}.
			 * 
			 * @param a parameter a
			 * @return the double value of a
			 */
			@Override
			public Double transform(T a) {
				return functionTransformer.transform(a).getY(x);
			}
		};

		return topEvent.calculate(t);
	}

	/**
	 * Returns the {@link BDD}.
	 * 
	 * @return the bdd
	 */
	public BDD<T> getBdd() {
		return bdd;
	}

	/**
	 * Returns the used {@link Transformer}.
	 * 
	 * @return the used functionTransformer
	 */
	public Transformer<T, ReliabilityFunction> getTransformer() {
		return functionTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.common.StructureFunction#isProvidingService(Map<T, Boolean>)
	 */
	@Override
	public boolean isProvidingService(Map<T, Boolean> variables) {
		// Convert variables and their phase to a term
		List<Term> terms = new ArrayList<Term>();
		for (Map.Entry<T, Boolean> entry : variables.entrySet()) {
			LiteralTerm<T> literalTerm = new LiteralTerm<T>(entry.getKey());
			if (entry.getValue()) {
				terms.add(literalTerm);
			} else {
				terms.add(new NOTTerm(literalTerm));
			}
		}
		ANDTerm variablesTerm = new ANDTerm(terms);
		// Get BDD from term
		BDDTTRF<T> bddTTRF = new BDDTTRF<T>(bdd.getProvider());
		BDD<T> variablesBDD = bddTTRF.convertToBDD(variablesTerm);
		
		// Combine variablesBDD and bdd via AND: If result is zero, no service is provided anymore
		BDD<T> tmpBDD = bdd.copy();
		tmpBDD.andWith(variablesBDD);
		boolean hasFailed = tmpBDD.isZero();
		return !hasFailed;
	}

}
