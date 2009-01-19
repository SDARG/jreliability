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
package org.jreliability.booleanfunction;

import java.util.List;

/**
 * The {@code LinearTerm} represents a {@code greater-equal linear constraint}.
 * 
 * @author glass
 * 
 */
public class LinearTerm extends AbstractHierarchicalTerm {

	/**
	 * The coefficients of the embedded terms.
	 */
	protected final List<Integer> coefficients;

	/**
	 * The right-hand-side of the {@code LinearTerm}.
	 */
	protected int rhs;

	/**
	 * Constructs a {@code LinearTerm} with a given list of coefficients, the
	 * embedded {@code Terms}, and the right-hand-side.
	 * 
	 * @param coefficients
	 *            the coefficients of the terms
	 * @param terms
	 *            the terms
	 * @param rhs
	 *            the right-hand-side
	 */
	public LinearTerm(List<Integer> coefficients, List<Term> terms, int rhs) {
		this(coefficients, terms, rhs, true);
	}

	/**
	 * Constructs a {@code LinearTerm} with a given list of coefficients, the
	 * embedded {@code Terms}, the right-hand-side, and the sign.
	 * 
	 * @param coefficients
	 *            the coefficients of the terms
	 * @param terms
	 *            the terms
	 * @param rhs
	 *            the right-hand-side
	 * @param sign
	 *            the sign
	 */
	public LinearTerm(List<Integer> coefficients, List<Term> terms, int rhs, boolean sign) {
		this.coefficients = coefficients;
		this.terms = terms;
		this.rhs = rhs;
		this.sign = sign;
	}

	/**
	 * Returns the coefficients of the {@code Terms}.
	 * 
	 * @return the coefficients of the terms
	 */
	public List<Integer> getCoefficients() {
		return coefficients;
	}

	/**
	 * Returns the right-hand-side.
	 * 
	 * @return the right-hand-side
	 */
	public int getRHS() {
		return rhs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.AbstractHierarchicalTerm#add(org.jreliability.booleanfunction.Term)
	 */
	public void add(Term term) {
		add(term, 1);
	}

	/**
	 * Adds a {@code Term} and its coefficient.
	 * 
	 * @param term
	 *            the term
	 * @param coefficient
	 *            the coefficient
	 */
	public void add(Term term, int coefficient) {
		terms.add(term);
		coefficients.add(coefficient);
	}

}
