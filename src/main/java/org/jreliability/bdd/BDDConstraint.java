/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The {@link BDDConstraint} is used to model {@link greater-equal} constraints
 * with a left-hand-side ({@link lhs}) consisting of {@link Literals} and the
 * right-hand-side ({@link rhs}) being an {@link Integer}.
 * 
 * @author glass, lukasiewycz
 * 
 * @param <T>
 *            the type of variables
 */
class BDDConstraint<T> {

	/**
	 * The right-hand-side of the constraint.
	 */
	protected int rhs;
	/**
	 * The left-hand-side of the constraint as a {@link List} of
	 * {@link Literals}.
	 */
	protected List<Literal<T>> lhs = new ArrayList<>();
	/**
	 * A map to deal with {@link Literals} including the same variables.
	 */
	protected Set<BDD<T>> variables = new HashSet<>();

	/**
	 * Constructs a {@link BDDConstraint} with a given right-hand-side
	 * {@link rhs} and left-hand-side {@link lhs}.
	 * 
	 * @param rhs
	 *            the right-hand-side
	 * @param lhs
	 *            the left-hand-side as a {@link List} of {@link Literals}.
	 */
	public BDDConstraint(int rhs, List<Literal<T>> lhs) {
		this.rhs = rhs;
		initialize(lhs);
	}

	/**
	 * Initializes the {@link BDDConstraint} with the normalizing operations
	 * proposed by {@link Een & Soerrensson 2006} plus zero coefficient
	 * elimination.
	 * 
	 * @param literals
	 *            the literals
	 */
	protected void initialize(List<Literal<T>> literals) {
		for (Literal<T> literal : literals) {
			checkCoefficient(literal);
			checkAndAddVariable(literal);
		}
		trim();
		eliminateZeroCoefficients();
		gcd();
	}

	/**
	 * Ensures a positive {@link coefficient} of the {@link Literal} by a
	 * negotiation of the variable and an update of the {@link rhs}.
	 * 
	 * @param literal
	 *            the literal
	 */
	protected void checkCoefficient(Literal<T> literal) {
		int coefficient = literal.getCoefficient();
		BDD<T> variable = literal.getVariable();
		if (coefficient < 0) {
			coefficient *= -1;
			rhs += coefficient;
			variable = variable.not();
			literal.setCoefficient(coefficient);
			literal.setVariable(variable);
		}
	}

	/**
	 * Checks the {@link Literal} if it includes a variable that is already
	 * present in the {@link BDDConstraint} and adds it correctly.
	 * 
	 * @param literal
	 */
	protected void checkAndAddVariable(Literal<T> literal) {
		int coefficient = literal.getCoefficient();
		BDD<T> variable = literal.getVariable();
		if (variables.contains(variable)) {
			Literal<T> knownLiteral = null;
			for (Literal<T> tmpLiteral : lhs) {
				BDD<T> tmpCoefficient = tmpLiteral.getVariable();
				if (tmpCoefficient.equals(variable)) {
					knownLiteral = tmpLiteral;
					break;
				}
			}
			int newCoefficient = coefficient + knownLiteral.getCoefficient();
			knownLiteral.setCoefficient(newCoefficient);
		} else {
			variables.add(variable);
			lhs.add(literal);
		}

	}

	/**
	 * Trims all {@link coefficients} that are greater than the {@link rhs} to
	 * {@link rhs}.
	 */
	protected void trim() {
		for (Literal<T> literal : lhs) {
			int coefficient = literal.getCoefficient();
			if (coefficient > rhs) {
				literal.setCoefficient(rhs);
			}
		}

	}

	/**
	 * Eliminates variables on the {@link lhs} with a zero coefficient.
	 */
	protected void eliminateZeroCoefficients() {
		Set<Literal<T>> zeroCoefficients = new HashSet<>();
		for (Literal<T> literal : lhs) {
			int coefficient = literal.getCoefficient();
			if (coefficient == 0) {
				zeroCoefficients.add(literal);
			}
		}
		lhs.removeAll(zeroCoefficients);

	}

	/**
	 * Determines the greatest-common-divisor ({@link gcd}) of all
	 * {@link coefficients} of the {@link lhs} and the {@link rhs} and updates
	 * the values.
	 */
	protected void gcd() {
		if (!lhs.isEmpty()) {
			int gcd = lhs.get(0).getCoefficient();
			for (Literal<T> literal : lhs) {
				int coefficient = literal.getCoefficient();
				gcd = gcdRec(gcd, coefficient);
			}
			gcd = gcdRec(gcd, rhs);
			assert gcd != 0;
			rhs = rhs / gcd;

			for (Literal<T> literal : lhs) {
				int coefficient = literal.getCoefficient();
				int newCoefficient = coefficient / gcd;
				literal.setCoefficient(newCoefficient);
			}
		}
	}

	/**
	 * Returns the {@link gcd} of two {@link Integers} by a simple recursive
	 * procedure.
	 * 
	 * @param a
	 *            integer a
	 * @param b
	 *            integer b
	 * @return the gcd of two integers
	 */
	protected int gcdRec(int a, int b) {
		if (b == 0) {
			return a;
		}
		return gcdRec(b, a % b);
	}

	/**
	 * Returns the right-hand-side ({@link rhs}) of the constraint.
	 * 
	 * @return the rhs of the constraint
	 */
	public int getRhs() {
		return rhs;
	}

	/**
	 * Returns the left-hand-side ({@link lhs}) of the constraint as a
	 * {@link List} of {@link Literals}.
	 * 
	 * @return the lhs of the constraint
	 */
	public List<Literal<T>> getLhs() {
		return lhs;
	}

	/**
	 * The {@link Literal} represents a variable using a {@link BDD} and its
	 * {@link coefficient}.
	 * 
	 * @author glass
	 * 
	 * @param <T>
	 *            the type of variable
	 */
	static class Literal<T> {

		/**
		 * The coefficient.
		 */
		protected Integer coefficient = 0;

		/**
		 * The variable.
		 */
		protected BDD<T> variable = null;

		/**
		 * Constructs a {@link Literal} with a given coefficient and variable as
		 * a {@link BDD}.
		 * 
		 * @param coefficient
		 *            the coefficient
		 * @param variable
		 *            the variable
		 */
		public Literal(int coefficient, BDD<T> variable) {
			this.coefficient = coefficient;
			this.variable = variable;
		}

		/**
		 * Returns the coefficient.
		 * 
		 * @return the coefficient
		 */
		public int getCoefficient() {
			return coefficient;
		}

		/**
		 * Returns the variable as a {@link BDD}.
		 * 
		 * @return the variable
		 */
		public BDD<T> getVariable() {
			return variable;
		}

		/**
		 * Sets the coefficient.
		 * 
		 * @param coefficient
		 *            the coefficient to set
		 */
		public void setCoefficient(Integer coefficient) {
			this.coefficient = coefficient;
		}

		/**
		 * Sets the variable.
		 * 
		 * @param variable
		 *            the variable to set
		 */
		public void setVariable(BDD<T> variable) {
			this.variable = variable;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "" + coefficient + "*" + variable.var();
		}
	}

	/**
	 * The {@link Pair} represents a tuple of two {@link Objects} {@link A} and
	 * {@link B}.
	 * 
	 * @author glass
	 * 
	 * @param <A>
	 *            the object a
	 * @param <B>
	 *            the object b
	 */
	static class Pair<A, B> {
		/**
		 * Object a.
		 */
		protected final A a;
		/**
		 * Object b.
		 */
		protected final B b;

		/**
		 * Constructs a {@link Pair} with two objects {@link a} and {@link b}.
		 * 
		 * @param a
		 *            the object a
		 * @param b
		 *            the object b
		 */
		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}

		/**
		 * Returns the {@link A} object.
		 * 
		 * @return the a object
		 */
		public A getA() {
			return a;
		}

		/**
		 * Returns the {@link B} object.
		 * 
		 * @return the b object
		 */
		public B getB() {
			return b;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			return result;
		}

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
			@SuppressWarnings("rawtypes")
			Pair other = (Pair) obj;
			if (a == null) {
				if (other.a != null) {
					return false;
				}
			} else if (!a.equals(other.a)) {
				return false;
			}
			if (b == null) {
				if (other.b != null) {
					return false;
				}
			} else if (!b.equals(other.b)) {
				return false;
			}
			return true;
		}

	}

}
