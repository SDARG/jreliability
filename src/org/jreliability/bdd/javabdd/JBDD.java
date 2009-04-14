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
package org.jreliability.bdd.javabdd;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.javabdd.BDDPairing;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDs;

/**
 * The {@code JBDD} is a {@code BDD} based on the {@code JavaBDD} standard java
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
		 * Constructs a {@code BDDIterator} with a given javabdd JDD iterator.
		 * 
		 * @param iterator
		 *            the javabdd JDD iterator
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
	 * The {@code AllSatIterator} is used as the {@code Iterator}.
	 * 
	 * @author glass, reimann
	 * 
	 */
	private class AllSatIterator implements Iterator<BDD<T>> {

		Iterator<Object> iterator;

		/**
		 * Constructs a {@code BDDIterator} with a given javabdd JDD iterator.
		 * 
		 * @param provider
		 *            the used JDDProvider
		 * @param list
		 *            .iterator() the javabdd JDD iterator
		 */
		@SuppressWarnings("unchecked")
		AllSatIterator(List list) {
			this.iterator = list.iterator();
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
			assert iterator.hasNext();
			byte[] x = (byte[]) iterator.next();

			BDD<T> bdd = provider.one();
			for (int i = 0; i < x.length; i++) {
				BDD<T> var = provider.get(provider.intToVariable.get(i));

				if (x[i] == 1) {
					bdd.andWith(var);
				} else if (x[i] == 0) {
					bdd.andWith(var.not());
				}
			}
			return bdd;
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
	 * Constructs a {@code JDD} with a {@code JDDProvider} and the BDD
	 * implementation used in the {@code JBBFactory} of the {@code JavaBDD}
	 * library.
	 * 
	 * @param provider
	 *            the used JDDProvider
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
	 * @see org.jreliability.bdd.BDD#allsat()
	 */
	public Iterator<BDD<T>> allsat() {
		return new AllSatIterator(bdd.allsat());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#and(org.jreliability.bdd.BDD)
	 */
	public BDD<T> and(BDD<T> that) {
		return new JBDD<T>(provider, bdd.and(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#andWith(org.jreliability.bdd.BDD)
	 */
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
	 * @see org.jreliability.bdd.BDD#exist(java.lang.Object)
	 */
	public BDD<T> exist(T variable) {
		JBDD<T> tmp = (JBDD<T>) provider.get(variable);
		JBDD<T> tmp2 = new JBDD<T>(provider, bdd.exist((tmp).bdd));
		tmp.free();
		return tmp2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#forAll(java.lang.Object)
	 */
	public BDD<T> forAll(T variable) {
		JBDD<T> tmp = (JBDD<T>) provider.get(variable);
		JBDD<T> tmp2 = new JBDD<T>(provider, bdd.forAll((tmp).bdd));
		tmp.free();
		return tmp2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#getProvider()
	 */
	public BDDProvider<T> getProvider() {
		return provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#high()
	 */
	public BDD<T> high() {
		return new JBDD<T>(provider, bdd.high());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#isOne()
	 */
	public boolean isOne() {
		return bdd.isOne();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#isZero()
	 */
	public boolean isZero() {
		return bdd.isZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#ite(org.jreliability.bdd.BDD,
	 * org.jreliability.bdd.BDD)
	 */
	public BDD<T> ite(BDD<T> thenBDD, BDD<T> elseBDD) {
		return new JBDD<T>(provider, bdd.ite(((JBDD<T>) thenBDD).bdd,
				((JBDD<T>) elseBDD).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#level()
	 */
	public int level() {
		return bdd.level();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#low()
	 */
	public BDD<T> low() {
		return new JBDD<T>(provider, bdd.low());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#not()
	 */
	public BDD<T> not() {
		return new JBDD<T>(provider, bdd.not());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#nodeCount()
	 */
	public int nodeCount() {
		return bdd.nodeCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#or(org.jreliability.bdd.BDD)
	 */
	public BDD<T> or(BDD<T> that) {
		return new JBDD<T>(provider, bdd.or(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#orWith(org.jreliability.bdd.BDD)
	 */
	public void orWith(BDD<T> that) {
		bdd.orWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#replace(java.lang.Object, java.lang.Object)
	 */
	public BDD<T> replace(T variable1, T variable2) {
		JBDD<T> tmp1 = (JBDD<T>) provider.get(variable1);
		JBDD<T> tmp2 = (JBDD<T>) provider.get(variable2);
		BDDPairing pair = provider.getFactory().makePair(tmp1.bdd.var(),
				tmp2.bdd.var());
		tmp1.free();
		tmp2.free();
		return new JBDD<T>(provider, bdd.replace(pair));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#replaceWith(java.lang.Object,
	 * java.lang.Object)
	 */
	public void replaceWith(T variable1, T variable2) {
		JBDD<T> tmp1 = (JBDD<T>) provider.get(variable1);
		JBDD<T> tmp2 = (JBDD<T>) provider.get(variable2);
		BDDPairing pair = provider.getFactory().makePair(tmp1.bdd.var(),
				tmp2.bdd.var());
		tmp1.free();
		tmp2.free();
		bdd.replaceWith(pair);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#restrict(org.jreliability.bdd.BDD)
	 */
	public BDD<T> restrict(BDD<T> that) {
		return new JBDD<T>(provider, bdd.restrict(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#restrictWith(org.jreliability.bdd.BDD)
	 */
	public void restrictWith(BDD<T> that) {
		bdd.restrictWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#sat()
	 */
	public BDD<T> sat() {
		return new JBDD<T>(provider, bdd.satOne());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#var()
	 */
	public T var() {
		return provider.get(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#xor(org.jreliability.bdd.BDD)
	 */
	public BDD<T> xor(BDD<T> that) {
		return new JBDD<T>(provider, bdd.xor(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#xorWith(org.jreliability.bdd.BDD)
	 */
	public void xorWith(BDD<T> that) {
		bdd.xorWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#imp(org.jreliability.bdd.BDD)
	 */
	public BDD<T> imp(BDD<T> that) {
		return new JBDD<T>(provider, bdd.imp(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#impWith(org.jreliability.bdd.BDD)
	 */
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
	 * @see org.jreliability.bdd.BDD#andWith(java.util.Collection)
	 */
	public void andWith(Collection<T> that) {
		for (T variable : that) {
			bdd.andWith(((JBDD<T>) provider.get(variable)).bdd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#andWith(java.lang.Object)
	 */
	public void andWith(T that) {
		bdd.andWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#orWith(java.util.Collection)
	 */
	public void orWith(Collection<T> that) {
		for (T variable : that) {
			bdd.orWith(((JBDD<T>) provider.get(variable)).bdd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#orWith(java.lang.Object)
	 */
	public void orWith(T that) {
		bdd.orWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#impWith(java.lang.Object)
	 */
	public void impWith(T that) {
		bdd.impWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#xorWith(java.lang.Object)
	 */
	public void xorWith(T that) {
		bdd.xorWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#copy()
	 */
	public BDD<T> copy() {
		JBDD<T> myCopy = null;
		JBDD<T> one = (JBDD<T>) provider.one();
		net.sf.javabdd.BDD copyBDD = bdd.and(one.bdd);
		myCopy = new JBDD<T>(provider, copyBDD);
		return myCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#getVariables()
	 */
	public Set<T> getVariables() {
		return BDDs.getVariables(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#free()
	 */
	public void free() {
		bdd.andWith(((JBDD<T>) provider.zero()).bdd);
	}

}
