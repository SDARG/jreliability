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
 * The {@code AbstractHierarchicalTerm} is the basic class for {@code Terms}
 * that consist of other {@code Terms}.
 * 
 * @author glass
 * 
 */
public abstract class AbstractHierarchicalTerm implements Term {

	/**
	 * Constructs an {@code AbstractHierarchicalTerm}.
	 * 
	 */
	public AbstractHierarchicalTerm() {
		super();
	}

	/**
	 * The embedded {@code Terms}.
	 */
	protected List<Term> terms;

	/**
	 * Returns a list of the embedded {@code Terms}.
	 * 
	 * @return a list of the embedded terms
	 */
	public List<Term> getTerms() {
		return terms;
	}

	/**
	 * Adds a {@code Term} to the list of embedded {@code Terms}.
	 * 
	 * @param term
	 *            the term to add
	 */
	public void add(Term term) {
		terms.add(term);
	}

	/**
	 * Returns the number of embedded {@code Terms}.
	 * 
	 * @return the number of embedded terms
	 */
	public int size() {
		return terms.size();
	}

}
