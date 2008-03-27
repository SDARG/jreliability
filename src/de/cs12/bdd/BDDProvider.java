package de.cs12.bdd;

import java.util.List;

/**
 * The {@code BDDProvider}
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public interface BDDProvider<T> {
	/**
	 * Register variables in the specified order.
	 * 
	 * @param variables
	 */
	public void add(List<T> variables);

	/**
	 * Register variables in the specified order.
	 * 
	 * @param variables
	 */
	public void add(T... variables);

	/**
	 * Returns the {@code false} or {@code 0} BDD.
	 * 
	 * @return the false or 0 BDD
	 */
	public BDD<T> zero();

	/**
	 * Returns the {@code true} or {@code 1} BDD.
	 * 
	 * @return the true or 1 BDD
	 */
	public BDD<T> one();

	/**
	 * Returns the {@code BDD} that represents the {@code variable}.
	 * 
	 * @param variable
	 *            the variable
	 * @return the BDD that represents the Object
	 */
	public BDD<T> get(T variable);

	/**
	 * Returns the {@code variable} that is represented by the {@code BDD}.
	 * 
	 * @param bdd
	 *            the bdd
	 * @return the variable represented by the BDD
	 */
	public T get(BDD<T> bdd);
}
