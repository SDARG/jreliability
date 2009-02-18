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

/**
 * The {@code AbstractTerm} is the basic class for all {@code Terms}.
 * 
 * @author glass
 * 
 */
public abstract class AbstractTerm implements Term {

	/**
	 * The sign of the whole term.
	 */
	protected boolean sign;

	/**
	 * Constructs an {@code AbstractTerm}.
	 * 
	 */
	public AbstractTerm() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.booleanfunction.Term#isNegated()
	 */
	public boolean sign() {
		return sign;
	}

}
