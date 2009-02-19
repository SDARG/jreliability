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

import org.jreliability.booleanfunction.AbstractTerm;

/**
 * The {@code LiteralTerm} represents a {@code Literal}, i.e. the used variable.
 * 
 * @author glass
 * 
 * @param <T>
 *            the type of the variable
 */
public class LiteralTerm<T> extends AbstractTerm {

	/**
	 * The variable or literal.
	 */
	protected final T variable;

	/**
	 * Constructs a {@code LiteralTerm} with a given variable.
	 * 
	 * @param variable
	 *            the variable
	 */
	public LiteralTerm(T variable) {
		this(true, variable);
	}

	/**
	 * Constructs a {@code LiteralTerm} with a given sign and variable.
	 * 
	 * @param sign
	 *            the sign
	 * @param variable
	 *            the variable
	 */
	public LiteralTerm(boolean sign, T variable) {
		this.sign = sign;
		this.variable = variable;
	}

	/**
	 * Returns the variable.
	 * 
	 * @return the variable
	 */
	public T get() {
		return variable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		if (!sign) {
			s += "~";
		}
		s += variable;
		return s;
	}
}
