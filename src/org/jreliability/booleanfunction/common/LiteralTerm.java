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

import org.jreliability.booleanfunction.Term;

/**
 * The {@code LiteralTerm} represents a {@code Literal}, i.e. the used variable.
 * A {@link LiteralTerm} equals another {@link LiteralTerm} if their variables
 * are equal.
 * 
 * @author glass, reimann
 * 
 * @param <T>
 *            the type of the variable
 */
public class LiteralTerm<T> implements Term {

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
		String s = "\"";
		s += variable;
		s += "\"";
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LiteralTerm<?> other = (LiteralTerm<?>) obj;
		if (variable == null) {
			if (other.variable != null) {
				return false;
			}
		} else if (!variable.equals(other.variable)) {
			return false;
		}
		return true;
	}
}
