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
/**
 * JReliability is free software: you can redistribute it OR/or modify it under
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
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.jreliability.booleanfunction.common;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.booleanfunction.AbstractHierarchicalTerm;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.TermUtils;

/**
 * The {@link ORTerm} is a {@link Term} that interrelates its embedded
 * {@link TermUtils} with the OR operator.<br>
 * {@code (OR term1 ... termN)}
 * 
 * @author glass
 * 
 */
public class ORTerm extends AbstractHierarchicalTerm {

	/**
	 * Constructs an {@link ORTerm}.
	 * 
	 */
	public ORTerm() {
		this(new ArrayList<Term>());
	}

	/**
	 * Constructs an {@link ORTerm} with a given list of embedded {@link TermUtils}.
	 * 
	 * @param terms
	 *            the embedded terms
	 */
	public ORTerm(List<Term> terms) {
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
		s.append("OR");
		for (Term term : terms) {
			s.append(" ").append(term);
		}
		s.append(")");
		return s.toString();
	}
}
