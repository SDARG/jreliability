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

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.TTRF;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.Terms;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code BDDTTRF} transforms a {@code Boolean function} represented as a
 * {@code Term} into a {@code ReliabilityFunction} or, if needed, into a {@code
 * BDD}.
 * 
 * @author glass
 * 
 */
public class BDDTTRF implements TTRF {

	/**
	 * The {@code BDDProvider}.
	 */
	protected final BDDProvider<Object> provider;

	/**
	 * Constructs a {@code BDDTTRF} with a given {@code BDDProvider}.
	 * 
	 * @param provider
	 *            the bdd provider
	 */
	public BDDTTRF(BDDProvider<Object> provider) {
		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.jreliability.booleanfunction.TTRF#convert(org.jreliability.
	 * booleanfunction.Term, org.apache.commons.collections15.Transformer)
	 */
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer) {
		return convert(term, functionTransformer, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.jreliability.booleanfunction.TTRF#convert(org.jreliability.
	 * booleanfunction.Term, org.apache.commons.collections15.Transformer,
	 * org.apache.commons.collections15.Predicate)
	 */
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer,
			Predicate<Object> existsPredicate) {
		BDD<Object> bdd = convertToBDD(term, existsPredicate);
		BDDReliabilityFunction<Object> function = new BDDReliabilityFunction<Object>(
				bdd, functionTransformer);
		bdd.free();
		return function;
	}

	/**
	 * Converts a given {@code BDD} and a {@code Transformer} to a {@code
	 * ReliabilityFunction.}
	 * 
	 * @param bdd
	 *            the bdd
	 * @param functionTransformer
	 *            the function functionTransformer
	 * @return a reliability function from the given bdd and function
	 *         functionTransformer
	 */
	public ReliabilityFunction convert(BDD<Object> bdd,
			Transformer<Object, ReliabilityFunction> functionTransformer) {
		BDDReliabilityFunction<Object> function = new BDDReliabilityFunction<Object>(
				bdd, functionTransformer);
		return function;
	}

	/**
	 * Returns a {@code BDD} representing the given {@code Term}.
	 * 
	 * @param term
	 *            the term
	 * @return a bdd representing the given term
	 */
	public BDD<Object> convertToBDD(Term term) {
		return convertToBDD(term, null);
	}

	/**
	 * Returns a {@code BDD} representing the given {@code Term} while
	 * respecting the exists-variables.
	 * 
	 * @param term
	 *            the term
	 * @param existsPredicate
	 *            the exists predicate
	 * @return a bdd representing the given term
	 */
	public BDD<Object> convertToBDD(Term term, Predicate<Object> existsPredicate) {
		BDD<Object> bdd = transform(term);
		if (!(existsPredicate == null)) {
			Set<Object> variables = Terms.getVariables(term);
			for (Object variable : variables) {
				if (existsPredicate.evaluate(variable)) {
					BDD<Object> tmp = bdd.exist(variable);
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
	protected BDD<Object> transform(Term term) {
		BDD<Object> bdd = null;
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
			LiteralTerm literalTerm = (LiteralTerm) term;
			bdd = transformLiteral(literalTerm);
		} else if (term instanceof TRUETerm) {
			TRUETerm trueTerm = (TRUETerm) term;
			bdd = transformTRUE(trueTerm);
		} else if (term instanceof FALSETerm) {
			FALSETerm falseTerm = (FALSETerm) term;
			bdd = transformFALSE(falseTerm);
		} else if (term instanceof NOTTerm) {
			NOTTerm notTerm = (NOTTerm) term;
			bdd = transformNOT(notTerm);
		} else {
			throw new IllegalArgumentException(
					"Unknown Term class in boolean function.");
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
	protected BDD<Object> transformAND(ANDTerm term) {
		BDD<Object> bdd = provider.one();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<Object> elementBDD = transform(element);
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
	protected BDD<Object> transformOR(ORTerm term) {
		BDD<Object> bdd = provider.zero();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<Object> elementBDD = transform(element);
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
	protected BDD<Object> transformLinear(LinearTerm term) {
		List<Integer> coefficients = term.getCoefficients();
		List<BDD<Object>> bdds = new ArrayList<BDD<Object>>();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<Object> elementBDD = transform(element);
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
	protected BDD<Object> transformLiteral(LiteralTerm term) {
		Object variable = term.get();
		return provider.get(variable);
	}

	/**
	 * Transforms a {@code TRUETerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the TRUE term
	 */
	protected BDD<Object> transformTRUE(TRUETerm term) {
		return provider.one();
	}

	/**
	 * Transforms a {@code FALSETerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the FALSE term
	 */
	protected BDD<Object> transformFALSE(FALSETerm term) {
		return provider.zero();
	}

	/**
	 * Transforms a {@code NOTTerm} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the NOT term
	 */
	protected BDD<Object> transformNOT(NOTTerm term) {
		Term element = term.get();
		BDD<Object> bdd = transform(element);
		BDD<Object> temp = bdd.not();
		bdd.free();
		bdd = temp;
		return bdd;
	}

}
