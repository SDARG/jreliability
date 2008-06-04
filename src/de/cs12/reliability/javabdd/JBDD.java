package de.cs12.reliability.javabdd;

import java.util.Collection;
import java.util.Iterator;

import net.sf.javabdd.BDDPairing;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code JBDD} is a {@code BDD} based on the {@code JavaBDD}
 * implementation.
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public class JBDD<T> implements BDD<T> {
	JBDDProvider<T> provider;

	net.sf.javabdd.BDD bdd;

	/**
	 * The {@code BDDIterator} is used as the {@code Iterator}.
	 * 
	 * @author glass, reimann
	 * 
	 */
	public class BDDIterator implements Iterator<BDD<T>> {

		Iterator<net.sf.javabdd.BDD> iterator;

		/**
		 * Constructs a {@code BDDIterator} with a given javabdd JBDD iterator.
		 * 
		 * @param iterator
		 *            the javabdd JBDD iterator
		 */
		BDDIterator(Iterator<net.sf.javabdd.BDD> iterator) {
			this.iterator = iterator;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		public BDD<T> next() {
			return new JBDD<T>(provider, iterator.next());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			iterator.remove();
		}

	}

	/**
	 * Constructs a {@code JBDD} with a {@code JBDDProvider} and the BDD
	 * implementation used in the {@code JBBFactory} of the {@code JavaBDD}
	 * library.
	 * 
	 * @param provider
	 *            the used JBDDProvider
	 * 
	 * @param bdd
	 *            the BDD implementation used in the JBBFactory of the javabdd
	 *            library
	 */
	JBDD(JBDDProvider<T> provider, net.sf.javabdd.BDD bdd) {
		this.provider = provider;
		this.bdd = bdd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#allsat()
	 */
	@SuppressWarnings("unchecked")
	public BDDIterator allsat() {
		return new BDDIterator(bdd.allsat().iterator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#and(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> and(BDD<T> that) {
		return new JBDD<T>(provider, bdd.and(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#andWith(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public void andWith(BDD<T> that) {
		bdd.andWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object that) {
		return bdd.equals(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#exist(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> exist(T variable) {
		return new JBDD<T>(provider, bdd.exist(((JBDD<T>) provider
				.get(variable)).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#forAll(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> forAll(T variable) {
		return new JBDD<T>(provider, bdd.forAll(((JBDD<T>) provider
				.get(variable)).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#high()
	 */
	public BDD<T> high() {
		return new JBDD<T>(provider, bdd.high());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#isOne()
	 */
	public boolean isOne() {
		return bdd.isOne();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#isZero()
	 */
	public boolean isZero() {
		return bdd.isZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#ite(de.cs12.reliability.bdd.BDD,
	 *      de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> ite(BDD<T> thenBDD, BDD<T> elseBDD) {
		return new JBDD<T>(provider, bdd.ite(((JBDD<T>) thenBDD).bdd,
				((JBDD<T>) elseBDD).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#level()
	 */
	public int level() {
		return bdd.level();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#low()
	 */
	public BDD<T> low() {
		return new JBDD<T>(provider, bdd.low());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#not()
	 */
	public BDD<T> not() {
		return new JBDD<T>(provider, bdd.not());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#or(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> or(BDD<T> that) {
		return new JBDD<T>(provider, bdd.or(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#orWith(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public void orWith(BDD<T> that) {
		bdd.orWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#replace(java.lang.Object,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> replace(T variable1, T variable2) {
		BDDPairing pair = provider.bddFactory.makePair(((JBDD<T>) provider
				.get(variable1)).bdd.var(),
				((JBDD<T>) provider.get(variable2)).bdd.var());
		return new JBDD<T>(provider, bdd.replace(pair));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#replaceWith(java.lang.Object,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void replaceWith(T variable1, T variable2) {
		BDDPairing pair = provider.bddFactory.makePair(((JBDD<T>) provider
				.get(variable1)).bdd.var(),
				((JBDD<T>) provider.get(variable2)).bdd.var());
		bdd.replaceWith(pair);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#sat()
	 */
	public BDD<T> sat() {
		return new JBDD<T>(provider, bdd.satOne());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#var()
	 */
	public T var() {
		return provider.get(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#xor(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> xor(BDD<T> that) {
		return new JBDD<T>(provider, bdd.xor(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#xorWith(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public void xorWith(BDD<T> that) {
		bdd.xorWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#imp(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> imp(BDD<T> that) {
		return new JBDD<T>(provider, bdd.imp(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#impWith(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public void impWith(BDD<T> that) {
		bdd.impWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return bdd.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return bdd.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#andWith(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public void andWith(Collection<T> that) {
		for (T variable : that) {
			bdd.andWith(((JBDD<T>) provider.get(variable)).bdd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#andWith(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void andWith(T that) {
		bdd.andWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#orWith(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public void orWith(Collection<T> that) {
		for (T variable : that) {
			bdd.orWith(((JBDD<T>) provider.get(variable)).bdd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#orWith(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void orWith(T that) {
		bdd.orWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#impWith(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void impWith(T that) {
		bdd.impWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#xorWith(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void xorWith(T that) {
		bdd.xorWith(((JBDD<T>) provider.get(that)).bdd);
	}
}
