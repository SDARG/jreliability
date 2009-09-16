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

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code TTRF} converts a {@code Term} to a {@code ReliabilityFunction}.
 * 
 * @author glass
 * 
 */
public interface TTRF {

	/**
	 * Converts a {@code Term} to a {@code ReliabilityFunction}.
	 * 
	 * @param term
	 *            the term to transform
	 * @param functionTransformer
	 *            the used function functionTransformer
	 * @return the reliabilityFunction deduced from the term
	 */
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer);

	/**
	 * Converts a {@code Term} to a {@code ReliabilityFunction} and excludes the
	 * {@code exists}-variables.
	 * 
	 * @param term
	 *            the term to transform
	 * @param functionTransformer
	 *            the used function functionTransformer
	 * @param existsPredicate
	 *            the exists-variable existsPredicate
	 * @return the reliabilityFunction deduced from the term
	 */
	public ReliabilityFunction convert(Term term,
			Transformer<Object, ReliabilityFunction> functionTransformer,
			Predicate<Object> existsPredicate);

}
