/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jreliability.booleanfunction.TTRF;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.Terms;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.common.Transformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code BDDTTRF} transforms a {@code Boolean function} represented as a
 * {@code Term} into a {@code ReliabilityFunction} or, if needed, into a
 * {@code BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variables
 */
public class BDDTTRF<T> implements TTRF<T> {

	/**
	 * The {@code BDDProvider}.
	 */
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@code BDDTTRF} with a given {@code BDDProvider}.
	 * 
	 * @param provider
	 *            the bdd provider
	 */
	public BDDTTRF(BDDProvider<T> provider) {
		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(org.jreliability.booleanfunction.Term)
	 */
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer) {
		return convert(term, functionTransformer, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(org.jreliability.booleanfunction.Term,
	 *      org.jreliability.booleanfunction.ExistsTransformer)
	 */
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer,
			Transformer<T, Boolean> existsTransformer) {
		BDD<T> bdd = convertToBDD(term, existsTransformer);
		BDDReliabilityFunction<T> function = new BDDReliabilityFunction<T>(bdd, functionTransformer);
		bdd.free();
		return function;
	}

	/**
	 * Converts a given {@code BDD} and a {@code Transformer} to a
	 * {@code ReliabilityFunction.}
	 * 
	 * @param bdd
	 *            the bdd
	 * @param functionTransformer
	 *            the function functionTransformer
	 * @return a reliability function from the given bdd and function
	 *         functionTransformer
	 */
	public ReliabilityFunction convert(BDD<T> bdd, Transformer<T, ReliabilityFunction> functionTransformer) {
		BDDReliabilityFunction<T> function = new BDDReliabilityFunction<T>(bdd, functionTransformer);
		return function;
	}

	/**
	 * Returns a {@code BDD} representing the given {@code Term}.
	 * 
	 * @param term
	 *            the term
	 * @return a bdd representing the given term
	 */
	public BDD<T> convertToBDD(Term term) {
		return convertToBDD(term, null);
	}

	/**
	 * Returns a {@code BDD} representing the given {@code Term} while
	 * respecting the exists-variables.
	 * 
	 * @param term
	 *            the term
	 * @param existsTransformer
	 *            the exists functionTransformer
	 * @return a bdd representing the given term
	 */
	public BDD<T> convertToBDD(Term term, Transformer<T, Boolean> existsTransformer) {
		BDD<T> bdd = transform(term);
		if (!(existsTransformer == null)) {
			Set<T> variables = Terms.getVariables(term);
			for (T t : variables) {
				if (existsTransformer.transform(t)) {
					BDD<T> tmp = bdd.exist(t);
					bdd.free();
					bdd = tmp;
				}
			}
		}
		return bdd;
	}

	/**
	 * Transforms a {@code Term} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the term
	 */
	@SuppressWarnings("unchecked")
	protected BDD<T> transform(Term term) {
		BDD<T> bdd = null;
		if (term instanceof ANDTerm) {
			ANDTerm andTerm = (ANDTerm) term;
			bdd = transformAND(andTerm);
		} else if (term instanceof ORTerm) {
			ORTerm orTerm = (ORTerm) term;
			bdd = transformOR(orTerm);
		} else if (term instanceof LinearTerm) {
			LinearTerm linearTerm = (LinearTerm) term;
			bdd = transformLinear(linearTerm);
		} else if (term instanceof LiteralTerm) {
			LiteralTerm<T> literalTerm = (LiteralTerm<T>) term;
			bdd = transformLiteral(literalTerm);
		} else if (term instanceof TRUETerm) {
			TRUETerm trueTerm = (TRUETerm) term;
			bdd = transformTRUE(trueTerm);
		} else if (term instanceof FALSETerm) {
			FALSETerm falseTerm = (FALSETerm) term;
			bdd = transformFALSE(falseTerm);
		} else {
			throw new IllegalArgumentException("Unknown Term class in boolean function.");
		}
		if (!term.sign()) {
			BDD<T> temp = bdd.not();
			bdd.free();
			bdd = temp;
		}
		return bdd;
	}

	/**
	 * Transforms an {@code ANDTerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the AND term
	 */
	protected BDD<T> transformAND(ANDTerm term) {
		BDD<T> bdd = provider.one();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<T> elementBDD = transform(element);
			bdd.andWith(elementBDD);
		}

		return bdd;
	}

	/**
	 * Transforms an {@code ORTerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the OR term
	 */
	protected BDD<T> transformOR(ORTerm term) {
		BDD<T> bdd = provider.zero();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<T> elementBDD = transform(element);
			bdd.orWith(elementBDD);
		}
		return bdd;
	}

	/**
	 * Transforms a {@code LinearTerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the linear term
	 */
	protected BDD<T> transformLinear(LinearTerm term) {
		List<Integer> coefficients = term.getCoefficients();
		List<BDD<T>> bdds = new ArrayList<BDD<T>>();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<T> elementBDD = transform(element);
			bdds.add(elementBDD);
		}
		Comparator comparator = term.getComparator();
		int rhs = term.getRHS();
		return BDDs.getBDD(coefficients, bdds, comparator, rhs);
	}

	/**
	 * Transforms a {@code LiteralTerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the literal term
	 */
	@SuppressWarnings("unchecked")
	protected BDD<T> transformLiteral(LiteralTerm term) {
		T t = (T) term.get();
		return provider.get(t);
	}

	/**
	 * Transforms a {@code TRUETerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the TRUE term
	 */
	protected BDD<T> transformTRUE(TRUETerm term) {
		return provider.one();
	}

	/**
	 * Transforms a {@code FALSETerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the FALSE term
	 */
	protected BDD<T> transformFALSE(FALSETerm term) {
		return provider.zero();
	}

}
