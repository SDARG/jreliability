/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
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
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link BDDTTRF} transforms a Boolean function represented as a {@link Term} into a {@link ReliabilityFunction}
 * or, if needed, into a {@link BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variables
 */
public class BDDTTRF<T> implements TTRF<T> {

	/**
	 * The {@link BDDProvider}.
	 */
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@link BDDTTRF} with a given {@link BDDProvider}.
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
	 * @see org.jreliability.booleanfunction.TTRF#convert(org.jreliability. booleanfunction.Term,
	 * org.apache.commons.collections15.Transformer)
	 */
	@Override
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer) {
		return convert(term, functionTransformer, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.TTRF#convert(org.jreliability. booleanfunction.Term,
	 * org.apache.commons.collections15.Transformer, org.apache.commons.collections15.Predicate)
	 */
	@Override
	public ReliabilityFunction convert(Term term, Transformer<T, ReliabilityFunction> functionTransformer,
			Predicate<T> existsPredicate) {
		BDD<T> bdd = convertToBDD(term, existsPredicate);
		return convert(bdd, functionTransformer);
	}

	/**
	 * Converts a given {@link BDD} and a {@link Transformer} to a {@link ReliabilityFunction}.
	 * 
	 * @param bdd
	 *            the bdd
	 * @param functionTransformer
	 *            the function functionTransformer
	 * @return a reliability function from the given bdd and function functionTransformer
	 */
	public ReliabilityFunction convert(BDD<T> bdd, Transformer<T, ReliabilityFunction> functionTransformer) {
		BDDReliabilityFunction<T> function = new BDDReliabilityFunction<>(bdd, functionTransformer);
		bdd.free();
		return function;
	}

	/**
	 * Returns a {@link BDD} representing the given {@link Term}.
	 * 
	 * @param term
	 *            the term
	 * @return a bdd representing the given term
	 */
	public BDD<T> convertToBDD(Term term) {
		return convertToBDD(term, null);
	}

	/**
	 * Returns a {@link BDD} representing the given {@link Term} while respecting the exists-variables.
	 * 
	 * @param term
	 *            the term
	 * @param existsPredicate
	 *            the exists predicate
	 * @return a bdd representing the given term
	 */
	public BDD<T> convertToBDD(Term term, Predicate<T> existsPredicate) {
		BDD<T> bdd = transform(term);
		if (!(existsPredicate == null)) {
			Set<T> variables = Terms.getVariables(term);
			for (T variable : variables) {
				if (existsPredicate.evaluate(variable)) {
					BDD<T> tmp = bdd.exist(variable);
					bdd.free();
					bdd = tmp;
				}
			}
		}
		return bdd;
	}

	/**
	 * Transforms a {@link Term} to a {@link BDD}.
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
		} else if (term instanceof NOTTerm) {
			NOTTerm notTerm = (NOTTerm) term;
			bdd = transformNOT(notTerm);
		} else {
			throw new IllegalArgumentException("Unknown Term class in boolean function.");
		}
		return bdd;
	}

	/**
	 * Transforms an {@link ANDTerm} to a {@link BDD}.
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
	 * Transforms an {@link ORTerm} to a {@link BDD}.
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
	 * Transforms a {@link LinearTerm} to a {@link BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the linear term
	 */
	protected BDD<T> transformLinear(LinearTerm term) {
		List<Integer> coefficients = term.getCoefficients();
		List<BDD<T>> bdds = new ArrayList<>();
		List<Term> terms = term.getTerms();
		for (Term element : terms) {
			BDD<T> elementBDD = transform(element);
			bdds.add(elementBDD);
		}
		Comparator comparator = term.getComparator();
		int rhs = term.getRHS();
		return BDDs.getBDD(coefficients, bdds, comparator, rhs, provider);
	}

	/**
	 * Transforms a {@link LiteralTerm} to a {@link BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the literal term
	 */
	protected BDD<T> transformLiteral(LiteralTerm<T> term) {
		T variable = term.get();
		return provider.get(variable);
	}

	/**
	 * Transforms a {@link TRUETerm} to a {@link BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the TRUE term
	 */
	protected BDD<T> transformTRUE(TRUETerm term) {
		return provider.one();
	}

	/**
	 * Transforms a {@link FALSETerm} to a {@link BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the FALSE term
	 */
	protected BDD<T> transformFALSE(FALSETerm term) {
		return provider.zero();
	}

	/**
	 * Transforms a {@link NOTTerm} to a {@link BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the NOT term
	 */
	protected BDD<T> transformNOT(NOTTerm term) {
		Term element = term.get();
		BDD<T> bdd = transform(element);
		BDD<T> temp = bdd.not();
		bdd.free();
		bdd = temp;
		return bdd;
	}

}
