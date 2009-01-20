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
package org.jreliability.function;

/**
 * The {@code PhiReliabilityFunction} is an interface for
 * {@code ReliabilityFunctions} that do not only contain a
 * {@code ReliabilityFunction} but also a structure function {@code Phi}.
 * 
 * @author glass
 * 
 */
public interface PhiReliabilityFunction extends ReliabilityFunction {

	/**
	 * Returns the structure function {@code Phi}.
	 * 
	 * @return the structure function phi
	 */
	public Phi phi();
	
	/**
	 * Returns the {@code FunctionTransformer}.
	 * 
	 * @return the function transformer
	 */
	public FunctionTransformer<?> getTransformer();

}
