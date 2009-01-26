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

import java.util.HashSet;
import java.util.Set;

import org.jreliability.booleanfunction.common.LiteralTerm;

/**
 * The {@code Terms}
 * 
 * @author glass
 * 
 */
public class Terms {

	public static <T> Set<T> getVariables(Term term) {
		Set<T> set = new HashSet<T>();
		getVariables(term, set);
		return set;
	}

	@SuppressWarnings("unchecked")
	public static <T> void getVariables(Term term, Set<T> set) {
		if (term instanceof AbstractHierarchicalTerm) {
			AbstractHierarchicalTerm hierarchicalTerm = (AbstractHierarchicalTerm) term;
			for (Term t : hierarchicalTerm.getTerms()) {
				getVariables(t, set);
			}
		} else if (term instanceof LiteralTerm) {
			LiteralTerm<T> literalTerm = (LiteralTerm<T>) term;
			set.add(literalTerm.get());
		}
	}

}
