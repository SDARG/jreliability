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

import org.jreliability.function.FunctionTransformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code TermToReliabilityFunction} converts a {@code Term} to a
 * {@code ReliabilityFunction}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of variable
 */
public interface TermToReliabilityFunction<T> {

	/**
	 * Converts a {@code Term} to a {@code ReliabilityFunction}.
	 * 
	 * @param term
	 *            the term to transform
	 * @param functionTransformer
	 *            the used function transformer
	 * @return the reliabilityFunction deduced from the term
	 */
	public ReliabilityFunction convert(Term term, FunctionTransformer<T> functionTransformer);

	/**
	 * Converts a {@code Term} to a {@code ReliabilityFunction} and excludes the
	 * {@code exists}-variables.
	 * 
	 * @param term
	 *            the term to transform
	 * @param functionTransformer
	 *            the used function transformer
	 * @param existsTransformer
	 *            the exists-variable existsTransformer
	 * @return the reliabilityFunction deduced from the term
	 */
	public ReliabilityFunction convert(Term term, FunctionTransformer<T> functionTransformer,
			ExistsTransformer<T> existsTransformer);

}
