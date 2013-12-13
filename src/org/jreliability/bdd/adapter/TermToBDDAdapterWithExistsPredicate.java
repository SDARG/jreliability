package org.jreliability.bdd.adapter;

import java.util.Set;

import org.apache.commons.collections15.Predicate;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.Terms;

public class TermToBDDAdapterWithExistsPredicate<T> extends TermToBDDAdapter<T> {

	protected final Predicate<T> existsPredicate;

	/**
	 * Constructs a {@code BDDTTRF} with a given {@code BDDProvider}.
	 * 
	 * @param provider
	 *            the bdd provider
	 */
	public TermToBDDAdapterWithExistsPredicate(BDDProvider<T> provider, Predicate<T> existsPredicate) {
		super(provider);
		this.existsPredicate = existsPredicate;
	}

	@Override
	@SuppressWarnings("unchecked")
	public BDD<T> transform(Term term) {
		BDD<T> bdd = super.transform(term);
		bdd = applyPredicate(bdd, term, existsPredicate);
		return bdd;
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
	public BDD<T> applyPredicate(BDD<T> bdd, Term term, Predicate<T> existsPredicate) {
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
}
