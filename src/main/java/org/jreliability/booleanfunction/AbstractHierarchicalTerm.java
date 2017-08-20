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
package org.jreliability.booleanfunction;

import java.util.List;

/**
 * The {@link AbstractHierarchicalTerm} is the basic class for {@link Terms} that consist of other {@link Terms}.
 * 
 * @author glass
 * 
 */
public abstract class AbstractHierarchicalTerm implements Term {

	/**
	 * The embedded {@link Terms}.
	 */
	protected List<Term> terms;

	/**
	 * Returns a list of the embedded {@link Terms}.
	 * 
	 * @return a list of the embedded terms
	 */
	public List<Term> getTerms() {
		return terms;
	}

	/**
	 * Adds a {@link Term} to the list of embedded {@link Terms}.
	 * 
	 * @param term
	 *            the term to add
	 */
	public void add(Term term) {
		terms.add(term);
	}

	/**
	 * Adds {@link Term}s to the list of embedded {@link Terms}.
	 * 
	 * @param terms
	 *            the terms to add
	 */
	public void add(Term... terms) {
		for (Term term : terms) {
			this.terms.add(term);
		}
	}

	/**
	 * Returns the number of embedded {@link Terms}.
	 * 
	 * @return the number of embedded terms
	 */
	public int size() {
		return terms.size();
	}

}
