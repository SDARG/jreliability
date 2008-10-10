package de.cs12.reliability.bdd;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@code BDD} is an interface containing the very basic functionality of a
 * {@code BDD}. Thus, it is used as a front end for the various available BDD
 * packages.
 * 
 * @author glass, reimann
 * 
 * @param <T>
 *            the type of the variables
 */
public interface BDD<T> {

	/**
	 * Returns a {@code BDDAllSatIterator} containing all satisfying variable
	 * assignments.
	 * 
	 * @return all satisfying variable assignments
	 */
	public Iterator<BDD<T>> allsat();

	/**
	 * Returns the logical {@code and} of two BDDs. Note: Both BDDs remain
	 * unchanged after this and-operation.
	 * 
	 * @param that
	 *            the BDD to and with this BDD
	 * @return the logical and of the two BDDs
	 */
	public BDD<T> and(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code and} of this and {@code that}
	 * BDD. Note: {@code That} BDD is consumed(!) within this operation and
	 * invalid afterwards.
	 * 
	 * @param that
	 *            the BDD to and with this BDD
	 */
	public void andWith(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code and} of this and {@code that}
	 * variables.
	 * 
	 * @param that
	 *            the variables to and with this BDD
	 */
	public void andWith(Collection<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code and} of this and {@code that}
	 * variable.
	 * 
	 * @param that
	 *            the variable to and with this BDD
	 */
	public void andWith(T that);

	/**
	 * TODO
	 * 
	 * Returns {@code true} if {@code this} BDD equals {@code that} BDD.
	 * 
	 * @param that
	 *            the BDD to compare with this BDD
	 * @return true if this BDD equals that BDD
	 */
	public boolean equals(Object that);

	/**
	 * Returns this {@code BDD} after an existential quantification of the
	 * specified variable.
	 * 
	 * @param variable
	 *            the variable for the existential quantification
	 * @return this BDD after an existential quantification of the specified
	 *         variables
	 */
	public BDD<T> exist(T variable);

	/**
	 * Returns this {@code BDD} after a universal quantification of the
	 * specified variable.
	 * 
	 * @param variable
	 *            the variable for the universal quantification
	 * @return this BDD after a universal quantification of the specified
	 *         variables
	 */
	public BDD<T> forAll(T variable);

	/**
	 * Returns one satisfying variable assignment as a {@code BDD}.
	 * 
	 * @return one satisfying variable assignment as a BDD
	 */
	public BDD<T> sat();

	/**
	 * Returns the {@code true} or {@code 1} branch of {@code this} BDD.
	 * 
	 * @return the true or 1 branch of this BDD
	 */
	public BDD<T> high();

	/**
	 * Returns the used {@code BDDProvider}.
	 * 
	 * @return the used bdd provider
	 */
	public BDDProvider<T> getProvider();

	/**
	 * Returns {@code true} if {@code this} BDD is the {@code one} BDD.
	 * 
	 * @return true if this BDD is the one BDD
	 */
	public boolean isOne();

	/**
	 * Returns {@code true} if {@code this} BDD is the {@code zero} BDD.
	 * 
	 * @return true if this BDD is the zero BDD
	 */
	public boolean isZero();

	/**
	 * Returns the {@code if-then-else} {@code BDD} with {@code this} BDD being
	 * the if, {@code thenBDD} being the then and {@code elseBDD} being the else
	 * statement.
	 * 
	 * @param thenBDD
	 *            the BDD for the then statement
	 * @param elseBDD
	 *            the BDD for the else statement
	 * @return the if-then-else BDD
	 */
	public BDD<T> ite(BDD<T> thenBDD, BDD<T> elseBDD);

	/**
	 * Returns the level of {@code this} BDD.
	 * 
	 * @return the level of this BDD
	 */
	public int level();

	/**
	 * Returns the {@code false} or {@code 0} branch of {@code this} BDD.
	 * 
	 * @return the false or 0 branch of this BDD
	 */
	public BDD<T> low();

	/**
	 * Returns a {@code BDD} the is the negation of {@code this} BDD.
	 * 
	 * @return a BDD the is the negation of this BDD
	 */
	public BDD<T> not();

	/**
	 * Returns the number of node in {@code this} BDD.
	 * 
	 * @return the number of nodes in this bdd
	 */
	public int nodeCount();

	/**
	 * Returns the logical {code or} of two BDDs. Note: Both BDDs remain
	 * unchanged after this or-operation.
	 * 
	 * @param that
	 *            the BDD to or with this BDD
	 * @return the logical or of the two BDDs
	 */
	public BDD<T> or(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code or} of this and {@code that}
	 * BDD. Note: {@code That} BDD is consumed(!) within this operation and
	 * invalid afterwards.
	 * 
	 * @param that
	 *            the BDD to or with this BDD
	 */
	public void orWith(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code or} of this and {@code that}
	 * variables.
	 * 
	 * @param that
	 *            the variables to or with this BDD
	 */
	public void orWith(Collection<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code or} of this and {@code that}
	 * variable.
	 * 
	 * @param that
	 *            the variable to or with this BDD
	 */
	public void orWith(T that);

	/**
	 * Returns a {@code BDD} where the variable for {@code variable1} is
	 * replaced with the variable of {@code variable2}.
	 * 
	 * @param variable1
	 *            the first variable
	 * @param variable2
	 *            the second variable
	 * @return a BDD where the variable for variable1 is replaced with the
	 *         variable of variable2
	 */
	public BDD<T> replace(T variable1, T variable2);

	/**
	 * Replaces the variable for {@code variable1} with the variable of {@code
	 * variable2} in {@code this} {@code BDD}.
	 * 
	 * @param variable1
	 *            the first variable
	 * @param variable2
	 *            the second variable
	 */
	public void replaceWith(T variable1, T variable2);

	/**
	 * Returns a BDD where the variables of {@code that} BDD are set to constant
	 * functions in {@code this} BDD. Note: Both BDDs remain unchanged after this
	 * or-operation.
	 * 
	 * @param that
	 *            the BDD to or with this BDD
	 * @return the logical or of the two BDDs
	 */
	public BDD<T> restrict(BDD<T> that);

	/**
	 * Restricts the variables of {@code that} to constant functions in {@code
	 * this} BDD. Note: {@code That} BDD is consumed(!) within this operation
	 * and invalid afterwards.
	 * 
	 * @param that
	 *            the BDD to or with this BDD
	 */
	public void restrictWith(BDD<T> that);

	/**
	 * Returns the variable labeling the {@code BDD}.
	 * 
	 * @return the variable labeling the BDD
	 */
	public T var();

	/**
	 * Returns the logical {@code xor} of two BDDs. Note: Both BDDs remain
	 * unchanged after this xor-operation.
	 * 
	 * @param that
	 *            the BDD to xor with this BDD
	 * @return the logical xor of the two BDDs
	 */
	public BDD<T> xor(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code xor} of this and {@code that}
	 * BDD. Note: {@code That} BDD is consumed(!) within this operation and
	 * invalid afterwards.
	 * 
	 * @param that
	 *            the BDD to xor with this BDD
	 */
	public void xorWith(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code xor} of this and {@code that}
	 * variable.
	 * 
	 * @param that
	 *            the variable to or with this BDD
	 */
	public void xorWith(T that);

	/**
	 * Returns the logical {@code implication} of two BDDs. Note: Both BDDs
	 * remain unchanged after this and-operation.
	 * 
	 * @param that
	 *            the BDD to implicate with this BDD
	 * @return the logical implication of the two BDDs
	 */
	public BDD<T> imp(BDD<T> that);

	/**
	 * Returns the logical {@code implication} of two BDDs. Note: {@code That}
	 * BDD is consumed(!) within this operation and invalid afterwards.
	 * 
	 * @param that
	 *            the BDD to implicate with this BDD
	 */
	public void impWith(BDD<T> that);

	/**
	 * Makes {@code this} BDD the logical {@code implication} of this and
	 * {@code that} variable.
	 * 
	 * @param that
	 *            the variable to implicate with this BDD
	 */
	public void impWith(T that);

	/**
	 * Returns a copy of this object.
	 * 
	 * @return a copy of this object
	 */
	public BDD<T> copy();

	/**
	 * Returns the set of variables that are used in this {@code BDD}.
	 * 
	 * @return the set of variables that are used in this bdd
	 */
	public Set<T> getVariables();
}
