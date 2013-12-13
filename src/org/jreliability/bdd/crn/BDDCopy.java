package org.jreliability.bdd.crn;

import java.util.ArrayList;
import java.util.Collection;

import org.jreliability.bdd.BDD;
import org.jreliability.cra.DecompositionNode;

public class BDDCopy<T> implements DecompositionNode<BDD<T>> {

	// TODO get number of successors from graph
	private final int n = 2;
	private BDD<T> bdd;

	@SuppressWarnings("unchecked")
	@Override
	public void set(Object input) {
		if (input instanceof BDD) {
			this.bdd = (BDD<T>) input;
		}
	}

	@Override
	public Collection<BDD<T>> get() {
		Collection<BDD<T>> collection = new ArrayList<BDD<T>>(n);
		collection.add(bdd);
		for (int i = 1; i < n; i++) {
			collection.add(bdd.copy());
		}
		return collection;
	}

	@Override
	public void requires() {
	}

	@Override
	public void provides() {
		// TODO Auto-generated method stub

	}

}
