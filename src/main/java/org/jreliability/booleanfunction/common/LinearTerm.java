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
package org.jreliability.booleanfunction.common;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.booleanfunction.AbstractHierarchicalTerm;
import org.jreliability.booleanfunction.Term;

/**
 * The {@code LinearTerm} represents a {@code linear constraint} of the form:
 * <p>
 * {@code left-hand-side comparator right-hand-side}
 * 
 * @author glass
 * 
 */
public class LinearTerm extends AbstractHierarchicalTerm {

	/**
	 * The {@code Comparator} determines the comparator in the {@code
	 * LinearTerm}, i.e. =, &gt;, &gt;=, &lt;, &lt;=.
	 * 
	 * @author glass
	 * 
	 */
	public enum Comparator {
		/**
		 * The equal comparator, i.e. =.
		 */
		EQUAL,
		/**
		 * The greater comparator, i.e. &gt;.
		 */
		GREATER,
		/**
		 * The greater-equal comparator, i.e. &gt;=.
		 */
		GREATEREQUAL,
		/**
		 * The less comparator, i.e. &lt;.
		 */
		LESS,
		/**
		 * The less-equal comparator, i.e. &lt;=.
		 */
		LESSEQUAL;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			switch (this) {
			case EQUAL:
				return "=";
			case GREATER:
				return ">";
			case GREATEREQUAL:
				return ">=";
			case LESS:
				return "<";
			default: // LESSEQUAL
				return "<=";
			}
		}
	}

	/**
	 * The coefficients of the embedded terms.
	 */
	protected final List<Integer> coefficients;

	/**
	 * The used {@code Comparator}.
	 */
	protected final Comparator comparator;

	/**
	 * The right-hand-side of the {@code LinearTerm}.
	 */
	protected int rhs;

	/**
	 * Constructs a {@code LinearTerm} with a given {@code Comparator}, and the right-hand-side.
	 * 
	 * @param comparator
	 *            the used comparator
	 * @param rhs
	 *            the right-hand-side
	 */
	public LinearTerm(Comparator comparator, int rhs) {
		this(new ArrayList<Integer>(), new ArrayList<Term>(), comparator, rhs, true);
	}

	/**
	 * Constructs a {@code LinearTerm} with a given {@code Comparator}, the right-hand-side, and the sign.
	 * 
	 * @param comparator
	 *            the used comparator
	 * @param rhs
	 *            the right-hand-side
	 * @param sign
	 *            the sign
	 */
	public LinearTerm(Comparator comparator, int rhs, boolean sign) {
		this(new ArrayList<Integer>(), new ArrayList<Term>(), comparator, rhs, sign);
	}

	/**
	 * Constructs a {@code LinearTerm} with a given list of coefficients, the embedded {@code Terms}, the
	 * {@code Comparator}, and the right-hand-side.
	 * 
	 * @param coefficients
	 *            the coefficients of the terms
	 * @param terms
	 *            the terms
	 * @param comparator
	 *            the used comparator
	 * @param rhs
	 *            the right-hand-side
	 */
	public LinearTerm(List<Integer> coefficients, List<Term> terms, Comparator comparator, int rhs) {
		this(coefficients, terms, comparator, rhs, true);
	}

	/**
	 * Constructs a {@code LinearTerm} with a given list of coefficients, the embedded {@code Terms}, the
	 * {@code Comparator}, the right-hand-side, and the sign.
	 * 
	 * @param coefficients
	 *            the coefficients of the terms
	 * @param terms
	 *            the terms *
	 * @param comparator
	 *            the used comparator
	 * @param rhs
	 *            the right-hand-side
	 * @param sign
	 *            the sign
	 */
	public LinearTerm(List<Integer> coefficients, List<Term> terms, Comparator comparator, int rhs, boolean sign) {
		this.coefficients = coefficients;
		this.terms = terms;
		this.comparator = comparator;
		this.rhs = rhs;
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
	@Override
	public void add(Term term) {
		add(1, term);
	}

	/**
	 * Adds a {@code Term} and its coefficient.
	 * 
	 * @param coefficient
	 *            the coefficient
	 * @param term
	 *            the term
	 */
	public void add(int coefficient, Term term) {
		terms.add(term);
		coefficients.add(coefficient);
	}

	/**
	 * Returns the {@code Comparator} of the {@code LinearTerm}.
	 * 
	 * @return the comparator of the linear term
	 */
	public Comparator getComparator() {
		return comparator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "(";
		s += comparator + " ";
		s += "\"" + rhs + "\"";
		for (int i = 0; i < size(); i++) {
			s += " " + "\"" + coefficients.get(i) + "\"" + " " + terms.get(i);
		}
		s += ")";
		return s;
	}

}
