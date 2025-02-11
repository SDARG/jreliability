/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.bdd;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link BDD} is an interface containing the very basic functionality of a
 * {@link BDD}. Thus, it is used as a front end for the various available BDD
 * packages.
 * 
 * @author glass, reimann
 * 
 * @param <T> the type of the variables
 */
public interface BDD<T> {

	/**
	 * Returns a {@link BDD} {@link Iterator} containing all satisfying variable
	 * assignments.
	 * 
	 * @return all satisfying variable assignments
	 */
	public Iterator<BDD<T>> allsat();

	/**
	 * Returns the logical {@code and} of two BDDs. Note: Both BDDs remain unchanged
	 * after this and-operation.
	 * 
	 * @param that the BDD to and with this BDD
	 * @return the logical and of the two BDDs
	 */
	public BDD<T> and(BDD<T> that);

	/**
	 * Makes this BDD the logical {@code and} of this and {@code that} {@link BDD}.
	 * Note: {@code That} BDD is consumed(!) within this operation and invalid
	 * afterwards.
	 * 
	 * @param that the BDD to and with this BDD
	 */
	public void andWith(BDD<T> that);

	/**
	 * Makes this BDD the logical {@code and} of this and {@code that} variables.
	 * 
	 * @param that the variables to and with this BDD
	 */
	public void andWith(Collection<T> that);

	/**
	 * Makes this BDD the logical {@code and} of this and {@code that} variable.
	 * 
	 * @param that the variable to and with this BDD
	 */
	public void andWith(T that);

	/**
	 * TODO
	 * 
	 * Returns {@code true} if this BDD equals {@code that} BDD.
	 * 
	 * @param that the BDD to compare with this BDD
	 * @return true if this BDD equals that BDD
	 */
	@Override
	public boolean equals(Object that);

	/**
	 * Returns this {@link BDD} after an existential quantification of the specified
	 * variable.
	 * 
	 * @param variable the variable for the existential quantification
	 * @return this BDD after an existential quantification of the specified
	 *         variables
	 */
	public BDD<T> exist(T variable);

	/**
	 * Returns this {@link BDD} after a universal quantification of the specified
	 * variable.
	 * 
	 * @param variable the variable for the universal quantification
	 * @return this BDD after a universal quantification of the specified variables
	 */
	public BDD<T> forAll(T variable);

	/**
	 * Returns one satisfying variable assignment as a {@link BDD}.
	 * 
	 * @return one satisfying variable assignment as a BDD
	 */
	public BDD<T> sat();

	/**
	 * Returns the {@code true} or {@code 1} branch of this BDD.
	 * 
	 * @return the true or 1 branch of this BDD
	 */
	public BDD<T> high();

	/**
	 * Returns the used {@link BDDProvider}.
	 * 
	 * @return the used bdd provider
	 */
	public BDDProvider<T> getProvider();

	/**
	 * Returns {@code true} if this BDD is the {@code one} BDD.
	 * 
	 * @return true if this BDD is the one BDD
	 */
	public boolean isOne();

	/**
	 * Returns {@code true} if this BDD is the {@code zero} BDD.
	 * 
	 * @return true if this BDD is the zero BDD
	 */
	public boolean isZero();

	/**
	 * Returns the if-then-else} {@link BDD} with this {@link BDD} being the if, the
	 * {@code thenBDD} being the then and {@code elseBDD} being the else statement.
	 * 
	 * @param thenBDD the BDD for the then statement
	 * @param elseBDD the BDD for the else statement
	 * @return the if-then-else BDD
	 */
	public BDD<T> ite(BDD<T> thenBDD, BDD<T> elseBDD);

	/**
	 * Returns the level of this {@link BDD}.
	 * 
	 * @return the level of this BDD
	 */
	public int level();

	/**
	 * Returns the {@code false} or {@code 0} branch of this {@link BDD}.
	 * 
	 * @return the false or 0 branch of this BDD
	 */
	public BDD<T> low();

	/**
	 * Returns a {@link BDD} that is the negation of this {@link BDD}.
	 * 
	 * @return a BDD that is the negation of this BDD
	 */
	public BDD<T> not();

	/**
	 * Returns the number of node in this {@link BDD}.
	 * 
	 * @return the number of nodes in this bdd
	 */
	public int nodeCount();

	/**
	 * Returns the logical or of two {@link BDD}s. Note: Both BDDs remain unchanged
	 * after this or-operation.
	 * 
	 * @param that the BDD to or with this BDD
	 * @return the logical or of the two BDDs
	 */
	public BDD<T> or(BDD<T> that);

	/**
	 * Makes this BDD the logical or of this and {@code that} {@link BDD}. Note:
	 * {@code That} BDD is consumed(!) within this operation and invalid afterwards.
	 * 
	 * @param that the BDD to or with this BDD
	 */
	public void orWith(BDD<T> that);

	/**
	 * Makes this {@link BDD} the logical or of this and {@code that} variables.
	 * 
	 * @param that the variables to or with this BDD
	 */
	public void orWith(Collection<T> that);

	/**
	 * Makes this BDD the logical or of this and {@code that} variable.
	 * 
	 * @param that the variable to or with this BDD
	 */
	public void orWith(T that);

	/**
	 * Returns a {@link BDD} where the variable for {@code variable1} is replaced
	 * with the variable of {@code variable2}.
	 * 
	 * @param variable1 the first variable
	 * @param variable2 the second variable
	 * @return a BDD where the variable for variable1 is replaced with the variable
	 *         of variable2
	 */
	public BDD<T> replace(T variable1, T variable2);

	/**
	 * Replaces the variable for {@code variable1} with the variable of
	 * {@code variable2} in this {@link BDD}.
	 * 
	 * @param variable1 the first variable
	 * @param variable2 the second variable
	 */
	public void replaceWith(T variable1, T variable2);

	/**
	 * Returns a {@link BDD} where the variables of {@code that} {@link BDD} are set
	 * to constant reliabilityFunctions in this BDD. Note: Both BDDs remain
	 * unchanged after this or-operation.
	 * 
	 * @param that the BDD to restrict this BDD with
	 * @return a new BDD representing this BDD restricted with that BDD
	 */
	public BDD<T> restrict(BDD<T> that);

	/**
	 * Restricts the variables of {@code that} to constant reliabilityFunctions in
	 * this BDD. Note: {@code That} BDD is consumed(!) within this operation and
	 * invalid afterwards.
	 * 
	 * @param that the BDD to restrict this BDD with
	 */
	public void restrictWith(BDD<T> that);

	/**
	 * Returns the variable labeling the {@link BDD}.
	 * 
	 * @return the variable labeling the BDD
	 */
	public T var();

	/**
	 * Returns the logical xor of two {@link BDD}s. Note: Both BDDs remain unchanged
	 * after this xor-operation.
	 * 
	 * @param that the BDD to xor with this BDD
	 * @return the logical xor of the two BDDs
	 */
	public BDD<T> xor(BDD<T> that);

	/**
	 * Makes this {@link BDD} the logical xor of this and {@code that} BDD. Note:
	 * {@code That} BDD is consumed(!) within this operation and invalid afterwards.
	 * 
	 * @param that the BDD to xor with this BDD
	 */
	public void xorWith(BDD<T> that);

	/**
	 * Makes this {@link BDD} the logical xor of this and {@code that} variable.
	 * 
	 * @param that the variable to or with this BDD
	 */
	public void xorWith(T that);

	/**
	 * Returns the logical implication of two {@link BDD}s. Note: Both BDDs remain
	 * unchanged after this and-operation.
	 * 
	 * @param that the BDD to implicate with this BDD
	 * @return the logical implication of the two BDDs
	 */
	public BDD<T> imp(BDD<T> that);

	/**
	 * Returns the logical implication of two {@link BDD}s. Note: {@code That} BDD
	 * is consumed(!) within this operation and invalid afterwards.
	 * 
	 * @param that the BDD to implicate with this BDD
	 */
	public void impWith(BDD<T> that);

	/**
	 * Makes this BDD the logical implication of this and {@code that} variable.
	 * 
	 * @param that the variable to implicate with this BDD
	 */
	public void impWith(T that);

	/**
	 * Returns a copy of this {@link BDD}.
	 * 
	 * @return a copy of this object
	 */
	public BDD<T> copy();

	/**
	 * Returns the set of variables that are used in this {@link BDD}.
	 * 
	 * @return the set of variables that are used in this bdd
	 */
	public Set<T> getVariables();

	/**
	 * Destroys this {@link BDD}.
	 */
	public void free();
}
