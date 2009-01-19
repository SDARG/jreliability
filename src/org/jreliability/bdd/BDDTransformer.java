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

import org.jreliability.booleanfunction.ANDTerm;
import org.jreliability.booleanfunction.LinearTerm;
import org.jreliability.booleanfunction.LiteralTerm;
import org.jreliability.booleanfunction.ORTerm;
import org.jreliability.booleanfunction.Term;

/**
 * The {@code BDDTransformer} transforms a {@code Boolean function} represented
 * as a {@code Term} into a {@code BDD}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variables
 */
public class BDDTransformer<T> {

	/**
	 * The {@code BDDProvider}.
	 */
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@code BDDTransformer}.
	 * 
	 */
	/**
	 * Constructs a {@code BDDTransformer} with a given {@code BDDProvider}.
	 * 
	 * @param provider
	 *            the bddProvider
	 */
	public BDDTransformer(BDDProvider<T> provider) {
		this.provider = provider;
	}

	/**
	 * Transforms a {@code Term} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the term
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> transform(Term term) {
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
		} else {
			throw new IllegalArgumentException("Unknown Term class in boolean function.");
		}
		if (!term.sign()) {
			bdd = bdd.not();
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
			bdd = bdd.and(elementBDD);
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
			bdd = bdd.or(elementBDD);
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
		int rhs = term.getRHS();
		return BDDs.getBDD(coefficients, bdds, ">=", rhs);
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

}
