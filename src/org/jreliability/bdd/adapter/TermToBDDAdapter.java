package org.jreliability.bdd.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDs;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.cra.Adapter;

public class TermToBDDAdapter<T> implements Adapter<Term, BDD<T>> {
	protected final BDDProvider<T> provider;

	/**
	 * Constructs a {@code BDDTTRF} with a given {@code BDDProvider}.
	 * 
	 * @param provider
	 *            the bdd provider
	 */
	public TermToBDDAdapter(BDDProvider<T> provider) {
		this.provider = provider;
	}

	/**
	 * Transforms a {@code Term} to a {@code BDD}.
	 * 
	 * @param term
	 *            the term to transform
	 * @return a bdd representing the term
	 */
	@Override
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
	protected BDD<T> transformLiteral(LiteralTerm<T> term) {
		T variable = term.get();
		return provider.get(variable);
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

	/**
	 * Transforms a {@code NOTTerm} to a {@code BDD}.
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
