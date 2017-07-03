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
package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code BDDConstraint} is used to model {@code greater-equal} constraints
 * with a left-hand-side ({@code lhs}) consisting of {@code Literals} and the
 * right-hand-side ({@code rhs}) being an {@code Integer}.
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
	 * The left-hand-side of the constraint as a {@code List} of {@code
	 * Literals}.
	 */
	protected List<Literal<T>> lhs = new ArrayList<Literal<T>>();
	/**
	 * A map to deal with {@code Literals} including the same variables.
	 */
	protected Set<BDD<T>> variables = new HashSet<BDD<T>>();

	/**
	 * Constructs a {@code BDDConstraint} with a given right-hand-side
	 * {@code rhs} and left-hand-side {@code lhs}.
	 * 
	 * @param rhs
	 *            the right-hand-side
	 * @param lhs
	 *            the left-hand-side as a {@code List} of {@code Literals}.
	 */
	public BDDConstraint(int rhs, List<Literal<T>> lhs) {
		this.rhs = rhs;
		initialize(lhs);
	}

	/**
	 * Initializes the {@code BDDConstraint} with the normalizing operations
	 * proposed by {@code Een & Soerrensson 2006} plus zero coefficient
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
	 * Ensures a positive {@code coefficient} of the {@code Literal} by a
	 * negotiation of the variable and an update of the {@code rhs}.
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
	 * Checks the {@code Literal} if it includes a variable that is already
	 * present in the {@code BDDConstraint} and adds it correctly.
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
	 * Trims all {@code coefficients} that are greater than the {@code rhs} to
	 * {@code rhs}.
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
	 * Eliminates variables on the {@code lhs} with a zero coefficient.
	 */
	protected void eliminateZeroCoefficients() {
		Set<Literal<T>> zeroCoefficients = new HashSet<Literal<T>>();
		for (Literal<T> literal : lhs) {
			int coefficient = literal.getCoefficient();
			if (coefficient == 0) {
				zeroCoefficients.add(literal);
			}
		}
		lhs.removeAll(zeroCoefficients);

	}

	/**
	 * Determines the greatest-common-divisor ({@code gcd}) of all {@code
	 * coefficients} of the {@code lhs} and the {@code rhs} and updates the
	 * values.
	 */
	protected void gcd() {
		if(!lhs.isEmpty()){
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
	 * Returns the {@code gcd} of two {@code Integers} by a simple recursive
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
	 * Returns the right-hand-side ({@code rhs}) of the constraint.
	 * 
	 * @return the rhs of the constraint
	 */
	public int getRhs() {
		return rhs;
	}

	/**
	 * Returns the left-hand-side ({@code lhs}) of the constraint as a {@code
	 * List} of {@code Literals}.
	 * 
	 * @return the lhs of the constraint
	 */
	public List<Literal<T>> getLhs() {
		return lhs;
	}

	/**
	 * The {@code Literal} represents a variable using a {@code BDD} and its
	 * {@code coefficient}.
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
		Integer coefficient = 0;

		/**
		 * The variable.
		 */
		BDD<T> variable = null;

		/**
		 * Constructs a {@code Literal} with a given coefficient and variable as
		 * a {@code BDD}.
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
		 * Returns the variable as a {@code BDD}.
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
	 * The {@code Pair} represents a tuple of two {@code Objects} {@code A} and
	 * {@code B}.
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
		 * Constructs a {@code Pair} with two objects {@code a} and {@code b}.
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
		 * Returns the {@code A} object.
		 * 
		 * @return the a object
		 */
		public A getA() {
			return a;
		}

		/**
		 * Returns the {@code B} object.
		 * 
		 * @return the b object
		 */
		public B getB() {
			return b;
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
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Pair<A, B> other = (Pair<A, B>) obj;

			return a.equals(other.a) && b.equals(other.b);
		}

	}

}
