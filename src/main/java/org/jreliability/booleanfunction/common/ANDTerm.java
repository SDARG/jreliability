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
 * The {@code ANDTerm} is a {@code Term} that interrelates its embedded {@code
 * Terms} with the {@code AND-Operator}.
 * <p>
 * (AND term1 ... termN)
 * </p>
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
		this(new ArrayList<Term>());
	}

	/**
	 * Constructs an {@code ANDTerm} with a given list of embedded {@code Terms} .
	 * 
	 * @param terms
	 *            the embedded terms
	 */
	public ANDTerm(List<Term> terms) {
		this.terms = terms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer("(");
		s.append("AND");
		for (Term term : terms) {
			s.append(" ").append(term);
		}
		s.append(")");
		return s.toString();
	}

}
