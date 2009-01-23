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

import org.jreliability.booleanfunction.ExistsTransformer;

/**
 * The {@code FalseExistsTransformer} is a default {@code ExistsTransformer}
 * that returns {@code false} for each element, i.e., no variable is an
 * exists-variable.
 * 
 * @author glass
 * 
 * @param
 * <P>
 * the type of variable
 */
public class FalseExistsTransformer<P> implements ExistsTransformer<P> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.ExistsTransformer#transform(java.lang.Object)
	 */
	public boolean transform(P p) {
		return false;
	}

}
