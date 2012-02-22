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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;

/**
 * The {@code Terms} provides static methods for the {@code Term} class.
 * 
 * @author glass
 * 
 */
public class Terms {

	/**
	 * Constructs a {@code Terms}.
	 * 
	 */
	public Terms() {
		super();
	}

	/**
	 * Returns the variables included in a {@code Term}.
	 * 
	 * @param <T>
	 *            the type of the variables
	 * @param term
	 *            the term
	 * @return the variables included in the term
	 */
	public static <T> Set<T> getVariables(Term term) {
		Set<T> set = new HashSet<T>();
		getVariables(term, set);
		return set;
	}

	/**
	 * Adds the variables included in a {@code Term} to a given set of
	 * variables.
	 * 
	 * @param <T>
	 *            the type of the variables
	 * @param term
	 *            the term
	 * @param set
	 *            the set of variables
	 */
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

	/**
	 * Derives a {@code Term} from a given {@code String}.
	 * 
	 * @param string
	 *            the string encoding the term
	 * @return a term from a given string
	 */
	public static Term getTermFromString(String string) {
		Object object = toObject(new Terms.ParseString(string));
		return parse(object);
	}

	/**
	 * Parses a {@code Term} from a given helper {@code Object} that has been
	 * derived from the original {@code String} encoding the {@code Term}.
	 * 
	 * @param object
	 *            the helper object
	 * @return a term from a given helper object
	 */
	protected static Term parse(Object object) {
		if (object instanceof List<?>) { // AND, OR, Linear, NOT
			List<?> list = (List<?>) object;
			String operator = list.get(0).toString();
			if ("AND".equalsIgnoreCase(operator)) {
				ANDTerm term = new ANDTerm();
				for (int i = 1; i < list.size(); i++) {
					Term subTerm = parse(list.get(i));
					term.add(subTerm);
				}
				return term;
			} else if ("OR".equalsIgnoreCase(operator)) {
				ORTerm term = new ORTerm();
				for (int i = 1; i < list.size(); i++) {
					Term subTerm = parse(list.get(i));
					term.add(subTerm);
				}
				return term;
			} else if ("NOT".equalsIgnoreCase(operator)) {
				if (list.size() > 2) {
					throw new RuntimeException(
							"Too many arguments in NOT term: " + list);
				}
				NOTTerm term = new NOTTerm(parse(list.get(1)));
				return term;
			} else { // Linear
				Comparator comparator = null;
				if (operator.equalsIgnoreCase(LinearTerm.Comparator.EQUAL
						.toString())) {
					comparator = LinearTerm.Comparator.EQUAL;
				} else if (operator
						.equalsIgnoreCase(LinearTerm.Comparator.GREATER
								.toString())) {
					comparator = LinearTerm.Comparator.GREATER;
				} else if (operator
						.equalsIgnoreCase(LinearTerm.Comparator.GREATEREQUAL
								.toString())) {
					comparator = LinearTerm.Comparator.GREATEREQUAL;
				} else if (operator.equalsIgnoreCase(LinearTerm.Comparator.LESS
						.toString())) {
					comparator = LinearTerm.Comparator.LESS;
				} else if (operator
						.equalsIgnoreCase(LinearTerm.Comparator.LESSEQUAL
								.toString())) {
					comparator = LinearTerm.Comparator.LESSEQUAL;
				} else {
					throw new RuntimeException("Unknown operator: " + operator);
				}

				int rhs = Integer.parseInt(list.get(1).toString());
				LinearTerm term = new LinearTerm(comparator, rhs);

				for (int i = 2; i < list.size(); i = i + 2) {
					int coefficient = Integer.parseInt(list.get(i).toString());
					Term subTerm = parse(list.get(i + 1));
					term.add(coefficient, subTerm);
				}
				return term;
			}
		} else { // Literal
			LiteralTerm term = new LiteralTerm(object);
			return term;
		}
	}

	/**
	 * Transforms a given {@code String} to a helper {@code Object} that
	 * represents {@code Terms} as {@code Lists} of helper {@code Objects} and
	 * variables as {@code Strings}.
	 * 
	 * @param string
	 *            the string encoding the {@code Term}
	 * @return a helper object to parse the given string
	 */
	protected static Object toObject(ParseString string) {
		string.skipSpaces();
		if (string.getCurrent() == '(') { // AND, OR, Linear, NOT
			string.next();
			List<Object> list = new ArrayList<Object>();

			// Operator
			String operator = "";
			while (!string.isEnd() && string.getCurrent() != ' '
					&& string.getCurrent() != '\n') {
				operator += string.getCurrent();
				string.next();
			}
			list.add(operator);

			// Arguments
			while (!string.isEnd()) {
				string.skipSpaces();
				Object obj = toObject(string);
				list.add(obj);
				string.skipSpaces();
				if (string.getCurrent() == ')') {
					string.next();
					return list;
				}
			}
			throw new RuntimeException(
					"String is not in compliance with the regular expression to describe a Term."
							+ "Missing ')' before EOF.");
		} else if (string.getCurrent() == '"') {
			String variable = "";
			string.next();
			while (!string.isEnd()) {
				variable += string.getCurrent();
				string.next();
				if (string.getCurrent() == '"') {
					string.next();
					return variable;
				}
			}
			throw new RuntimeException("No closing \" in: " + variable);
		} else {
			throw new RuntimeException(
					"String is not in compliance with the regular expression to describe a Term."
							+ "Operands must be enclosed in brackets (), variables must be enclosed in \"\".");
		}
	}

	/**
	 * The {@code ParseString} is a helper class to parse a helper {@code
	 * Object} from a given {@code String}.
	 * 
	 * @author glass
	 * 
	 */
	protected static class ParseString {
		/**
		 * The original {@code String}.
		 */
		protected String string;
		/**
		 * The pointer for the current position.
		 */
		protected int current = 0;

		/**
		 * Constructs a {@code ParseString} with a given {@code String}.
		 * 
		 * @param string
		 *            the original string
		 * 
		 */
		public ParseString(String string) {
			this.string = string;
		}

		/**
		 * Returns the current character.
		 * 
		 * @return the current character
		 */
		public char getCurrent() {
			return string.charAt(current);
		}

		/**
		 * Moves the pointer to the next character.
		 */
		public void next() {
			current++;
		}

		/**
		 * Moves the pointer to the n-th next character.
		 * 
		 * @param n
		 *            the n-th next
		 */
		public void next(int n) {
			current += n;
		}

		/**
		 * Returns {@code true} if the end of the {@code String} is reached.
		 * 
		 * @return true if the end of the string is reached
		 */
		public boolean isEnd() {
			return (current >= string.length());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return string.substring(current);
		}

		/**
		 * Moves the pointer to the next character that is not a blank or
		 * newline character.
		 */
		public void skipSpaces() {
			while (!this.isEnd()
					&& (this.getCurrent() == ' ' || this.getCurrent() == '\n')) {
				this.next();
			}
		}
	}
}