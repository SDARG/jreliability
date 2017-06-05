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
package org.jreliability.booleanfunction.common;

import org.jreliability.booleanfunction.Term;

/**
 * The {@code NOTTerm} is used to model Boolean negation by embedding a {@code
 * Term} that shall be negated in a {@code NOTTerm}.
 * 
 * @author glass
 * 
 */
public class NOTTerm implements Term {

	/**
	 * The term to negate.
	 */
	protected final Term term;

	/**
	 * Constructs a {@code NOTTerm} with a given term.
	 * 
	 * @param term
	 *            the term
	 */
	public NOTTerm(Term term) {
		this.term = term;
	}

	/**
	 * Returns the term.
	 * 
	 * @return the term
	 */
	public Term get() {
		return term;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "(NOT ";
		s += term + ")";
		return s;
	}

}
