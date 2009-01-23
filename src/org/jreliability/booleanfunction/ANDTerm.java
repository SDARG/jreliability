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

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ANDTerm} is a {@code Term} that interrelates its embedded {@code
 * Terms} with the {@code AND-Operator}.
 * 
 * @author glass
 * 
 */
public class ANDTerm extends AbstractHierarchicalTerm {

	/**
	 * Constructs an {@code ANDTerm}.
	 * 
	 */
	public ANDTerm() {
		this(true, new ArrayList<Term>());
	}

	/**
	 * Constructs an {@code ANDTerm} with a given sign.
	 * 
	 * @param sign
	 *            the sign of the term
	 */
	public ANDTerm(boolean sign) {
		this(sign, new ArrayList<Term>());
	}

	/**
	 * Constructs an {@code ANDTerm} with a given list of embedded {@code Terms}
	 * .
	 * 
	 * @param terms
	 *            the embedded terms
	 */
	public ANDTerm(List<Term> terms) {
		this(true, terms);
	}

	/**
	 * Constructs an {@code ANDTerm} with a given sign and a list of embedded
	 * {@code Terms}.
	 * 
	 * @param sign
	 *            the sign of the term
	 * @param terms
	 *            the embedded terms
	 */
	public ANDTerm(boolean sign, List<Term> terms) {
		this.sign = sign;
		this.terms = terms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "(";
		if(!sign){
			s += "~";
		}
		s += "AND";
		for(Term term: terms){
			s += " "+term;
		}
		return s+")";
	}

}
