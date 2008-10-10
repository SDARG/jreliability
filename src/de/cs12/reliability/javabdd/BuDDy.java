package de.cs12.reliability.javabdd;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.sf.javabdd.BDDPairing;
import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code BuDDy} is a {@code BDD} from the native {@code BuDDy} library.
 * 
 * @author glass, reimann, lukasiewycz
 * @param <T>
 *            the type of the variables
 */
public class BuDDy<T> implements BDD<T> {
	BuDDyProvider<T> provider;

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
		 * Constructs a {@code BDDIterator} with a given javabdd BuDDy iterator.
		 * 
		 * @param iterator
		 *            the javabdd BuDDy iterator
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
			return new BuDDy<T>(provider, iterator.next());
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
	 * Constructs a {@code BuDDy} with a {@code BuDDyProvider} and the BDD
	 * implementation used in the {@code JBBFactory} of the {@code JavaBDD}
	 * library.
	 * 
	 * @param provider
	 *            the used BuDDyProvider
	 * 
	 * @param bdd
	 *            the BDD implementation used in the JBBFactory of the javabdd
	 *            library
	 */
	BuDDy(BuDDyProvider<T> provider, net.sf.javabdd.BDD bdd) {
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
	public BDD<T> and(BDD<T> that) {
		return new BuDDy<T>(provider, bdd.and(((BuDDy<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#andWith(de.cs12.reliability.bdd.BDD)
	 */
	public void andWith(BDD<T> that) {
		bdd.andWith(((BuDDy<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object that) {
		return bdd.equals(((BuDDy<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#exist(java.lang.Object)
	 */
	public BDD<T> exist(T variable) {
		return new BuDDy<T>(provider, bdd.exist(((BuDDy<T>) provider
				.get(variable)).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#forAll(java.lang.Object)
	 */
	public BDD<T> forAll(T variable) {
		return new BuDDy<T>(provider, bdd.forAll(((BuDDy<T>) provider
				.get(variable)).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#getProvider()
	 */
	@Override
	public BDDProvider<T> getProvider() {
		return provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#high()
	 */
	public BDD<T> high() {
		return new BuDDy<T>(provider, bdd.high());
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
	 * de.cs12.reliability.bdd.BDD)
	 */
	public BDD<T> ite(BDD<T> thenBDD, BDD<T> elseBDD) {
		return new BuDDy<T>(provider, bdd.ite(((BuDDy<T>) thenBDD).bdd,
				((BuDDy<T>) elseBDD).bdd));
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
		return new BuDDy<T>(provider, bdd.low());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#not()
	 */
	public BDD<T> not() {
		return new BuDDy<T>(provider, bdd.not());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#nodeCount()
	 */
	public int nodeCount() {
		return bdd.nodeCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#or(de.cs12.reliability.bdd.BDD)
	 */
	public BDD<T> or(BDD<T> that) {
		return new BuDDy<T>(provider, bdd.or(((BuDDy<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#orWith(de.cs12.reliability.bdd.BDD)
	 */
	public void orWith(BDD<T> that) {
		bdd.orWith(((BuDDy<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#replace(java.lang.Object,
	 * java.lang.Object)
	 */
	public BDD<T> replace(T variable1, T variable2) {
		BDDPairing pair = BuDDyProvider.bddFactory.makePair(
				((BuDDy<T>) provider.get(variable1)).bdd.var(),
				((BuDDy<T>) provider.get(variable2)).bdd.var());
		return new BuDDy<T>(provider, bdd.replace(pair));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#replaceWith(java.lang.Object,
	 * java.lang.Object)
	 */
	public void replaceWith(T variable1, T variable2) {
		BDDPairing pair = BuDDyProvider.bddFactory.makePair(
				((BuDDy<T>) provider.get(variable1)).bdd.var(),
				((BuDDy<T>) provider.get(variable2)).bdd.var());
		bdd.replaceWith(pair);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#restrict(de.cs12.reliability.bdd.BDD)
	 */
	public BDD<T> restrict(BDD<T> that) {
		return new BuDDy<T>(provider, bdd.restrict(((BuDDy<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.cs12.reliability.bdd.BDD#restrictWith(de.cs12.reliability.bdd.BDD)
	 */
	public void restrictWith(BDD<T> that) {
		bdd.restrictWith(((BuDDy<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#sat()
	 */
	public BDD<T> sat() {
		return new BuDDy<T>(provider, bdd.satOne());
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
	public BDD<T> xor(BDD<T> that) {
		return new BuDDy<T>(provider, bdd.xor(((BuDDy<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#xorWith(de.cs12.reliability.bdd.BDD)
	 */
	public void xorWith(BDD<T> that) {
		bdd.xorWith(((BuDDy<T>) that).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#imp(de.cs12.reliability.bdd.BDD)
	 */
	public BDD<T> imp(BDD<T> that) {
		return new BuDDy<T>(provider, bdd.imp(((BuDDy<T>) that).bdd));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#impWith(de.cs12.reliability.bdd.BDD)
	 */
	public void impWith(BDD<T> that) {
		bdd.impWith(((BuDDy<T>) that).bdd);
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
	public void andWith(Collection<T> that) {
		for (T variable : that) {
			bdd.andWith(((BuDDy<T>) provider.get(variable)).bdd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#andWith(java.lang.Object)
	 */
	public void andWith(T that) {
		bdd.andWith(((BuDDy<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#orWith(java.util.Collection)
	 */
	public void orWith(Collection<T> that) {
		for (T variable : that) {
			bdd.orWith(((BuDDy<T>) provider.get(variable)).bdd);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#orWith(java.lang.Object)
	 */
	public void orWith(T that) {
		bdd.orWith(((BuDDy<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#impWith(java.lang.Object)
	 */
	public void impWith(T that) {
		bdd.impWith(((BuDDy<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#xorWith(java.lang.Object)
	 */
	public void xorWith(T that) {
		bdd.xorWith(((BuDDy<T>) provider.get(that)).bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDD#copy()
	 */
	@Override
	public BDD<T> copy() {
		net.sf.javabdd.BDD copyBDD = bdd.and(provider.one().bdd);
		BuDDy<T> myCopy = new BuDDy<T>(provider, copyBDD);
		return myCopy;
	}

	/* (non-Javadoc)
	 * @see de.cs12.reliability.bdd.BDD#getVariables()
	 */
	@Override
	public Set<T> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}
}
