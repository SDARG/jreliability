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
package org.jreliability.bdd.javabdd;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDs;

import net.sf.javabdd.BDDPairing;

/**
 * The {@link JBDD} is a {@link BDD} based on the JavaBDD standard java implementation.
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public class JBDD<T> implements BDD<T> {

	JBDDProvider<T> provider;

	net.sf.javabdd.BDD bdd;

	/**
	 * The {@link AllSatIterator} is used as the {@link Iterator}.
	 * 
	 * @author glass, reimann
	 * 
	 */
	private class AllSatIterator implements Iterator<BDD<T>> {

		Iterator<T> iterator;

		/**
		 * Constructs a {@link Iterator} with a given JavaBDD JDD iterator.
		 * 
		 * @param provider
		 *            the used JDDProvider
		 * @param list
		 *            .iterator() the javabdd JDD iterator
		 */
		AllSatIterator(List<T> list) {
			this.iterator = list.iterator();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
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
		@Override
		public void remove() {
			iterator.remove();
		}

	}

	/**
	 * Constructs a {@link JDD} with a {@link JDDProvider} and the BDD implementation used in the {@link JBBFactory} of
	 * the {@link JavaBDD} library.
	 * 
	 * @param provider
	 *            the used JDDProvider
	 * @param bdd
	 *            the BDD implementation used in the JBBFactory of the javabdd library
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
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<BDD<T>> allsat() {
		return new AllSatIterator(bdd.allsat());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#and(org.jreliability.bdd.BDD)
	 */
	@Override
	public BDD<T> and(BDD<T> that) {
		return new JBDD<>(provider, bdd.and(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#andWith(org.jreliability.bdd.BDD)
	 */
	@Override
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
	@Override
	public BDD<T> exist(T variable) {
		JBDD<T> tmp = (JBDD<T>) provider.get(variable);
		JBDD<T> tmp2 = new JBDD<>(provider, bdd.exist((tmp).bdd));
		tmp.free();
		return tmp2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#forAll(java.lang.Object)
	 */
	@Override
	public BDD<T> forAll(T variable) {
		JBDD<T> tmp = (JBDD<T>) provider.get(variable);
		JBDD<T> tmp2 = new JBDD<>(provider, bdd.forAll((tmp).bdd));
		tmp.free();
		return tmp2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#getProvider()
	 */
	@Override
	public BDDProvider<T> getProvider() {
		return provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#high()
	 */
	@Override
	public BDD<T> high() {
		return new JBDD<>(provider, bdd.high());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#isOne()
	 */
	@Override
	public boolean isOne() {
		return bdd.isOne();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#isZero()
	 */
	@Override
	public boolean isZero() {
		return bdd.isZero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#ite(org.jreliability.bdd.BDD, org.jreliability.bdd.BDD)
	 */
	@Override
	public BDD<T> ite(BDD<T> thenBDD, BDD<T> elseBDD) {
		return new JBDD<>(provider, bdd.ite(((JBDD<T>) thenBDD).bdd, ((JBDD<T>) elseBDD).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#level()
	 */
	@Override
	public int level() {
		return bdd.level();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#low()
	 */
	@Override
	public BDD<T> low() {
		return new JBDD<>(provider, bdd.low());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#not()
	 */
	@Override
	public BDD<T> not() {
		return new JBDD<>(provider, bdd.not());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#nodeCount()
	 */
	@Override
	public int nodeCount() {
		return bdd.nodeCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#or(org.jreliability.bdd.BDD)
	 */
	@Override
	public BDD<T> or(BDD<T> that) {
		return new JBDD<>(provider, bdd.or(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#orWith(org.jreliability.bdd.BDD)
	 */
	@Override
	public void orWith(BDD<T> that) {
		bdd.orWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#replace(java.lang.Object, java.lang.Object)
	 */
	@Override
	public BDD<T> replace(T variable1, T variable2) {
		JBDD<T> tmp1 = (JBDD<T>) provider.get(variable1);
		JBDD<T> tmp2 = (JBDD<T>) provider.get(variable2);
		BDDPairing pair = provider.getFactory().makePair(tmp1.bdd.var(), tmp2.bdd.var());
		tmp1.free();
		tmp2.free();
		return new JBDD<>(provider, bdd.replace(pair));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#replaceWith(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void replaceWith(T variable1, T variable2) {
		JBDD<T> tmp1 = (JBDD<T>) provider.get(variable1);
		JBDD<T> tmp2 = (JBDD<T>) provider.get(variable2);
		BDDPairing pair = provider.getFactory().makePair(tmp1.bdd.var(), tmp2.bdd.var());
		tmp1.free();
		tmp2.free();
		bdd.replaceWith(pair);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#restrict(org.jreliability.bdd.BDD)
	 */
	@Override
	public BDD<T> restrict(BDD<T> that) {
		return new JBDD<>(provider, bdd.restrict(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#restrictWith(org.jreliability.bdd.BDD)
	 */
	@Override
	public void restrictWith(BDD<T> that) {
		bdd.restrictWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#sat()
	 */
	@Override
	public BDD<T> sat() {
		return new JBDD<>(provider, bdd.satOne());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#var()
	 */
	@Override
	public T var() {
		return provider.get(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#xor(org.jreliability.bdd.BDD)
	 */
	@Override
	public BDD<T> xor(BDD<T> that) {
		return new JBDD<>(provider, bdd.xor(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#xorWith(org.jreliability.bdd.BDD)
	 */
	@Override
	public void xorWith(BDD<T> that) {
		bdd.xorWith(((JBDD<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#imp(org.jreliability.bdd.BDD)
	 */
	@Override
	public BDD<T> imp(BDD<T> that) {
		return new JBDD<>(provider, bdd.imp(((JBDD<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#impWith(org.jreliability.bdd.BDD)
	 */
	@Override
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
	@Override
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
	@Override
	public void andWith(T that) {
		bdd.andWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#orWith(java.util.Collection)
	 */
	@Override
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
	@Override
	public void orWith(T that) {
		bdd.orWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#impWith(java.lang.Object)
	 */
	@Override
	public void impWith(T that) {
		bdd.impWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#xorWith(java.lang.Object)
	 */
	@Override
	public void xorWith(T that) {
		bdd.xorWith(((JBDD<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#copy()
	 */
	@Override
	public BDD<T> copy() {
		JBDD<T> myCopy = null;
		JBDD<T> one = (JBDD<T>) provider.one();
		net.sf.javabdd.BDD copyBDD = bdd.and(one.bdd);
		myCopy = new JBDD<>(provider, copyBDD);
		return myCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#getVariables()
	 */
	@Override
	public Set<T> getVariables() {
		return BDDs.getVariables(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDD#free()
	 */
	@Override
	public void free() {
		bdd.andWith(((JBDD<T>) provider.zero()).bdd);
	}

}
