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

package org.jreliability.booleanfunction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;

/**
 * The {@link TermUtils} provides static methods for the {@link Term} class.
 * 
 * @author glass
 * 
 */
public class TermUtils {

	/**
	 * @throws InstantiationException
	 */
	private TermUtils() throws InstantiationException {
		throw new InstantiationException("Instances of this type are forbidden.");
	}

	/**
	 * Returns the variables included in a {@link Term}.
	 * 
	 * @param <T>
	 *            the type of the variables
	 * @param term
	 *            the term
	 * @return the variables included in the term
	 */
	public static <T> Set<T> getVariables(Term term) {
		Set<T> set = new HashSet<>();
		getVariables(term, set);
		return set;
	}

	/**
	 * Adds the variables included in a {@link Term} to a given set of
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
	 * Derives a {@link Term} from a given {@link String}.
	 * 
	 * @param string
	 *            the string encoding the term
	 * @return a term from a given string
	 */
	public static Term getTermFromString(String string) {
		Object object = toObject(new TermUtils.ParseString(string));
		return parse(object);
	}

	/**
	 * Parses a {@link Term} from a given helper {@link Object} that has been
	 * derived from the original {@link String} encoding the {@link Term}.
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
					throw new IllegalArgumentException("Too many arguments in NOT term: " + list);
				}
				NOTTerm term = new NOTTerm(parse(list.get(1)));
				return term;
			} else { // Linear
				Comparator comparator = null;
				if (operator.equalsIgnoreCase(LinearTerm.Comparator.EQUAL.toString())) {
					comparator = LinearTerm.Comparator.EQUAL;
				} else if (operator.equalsIgnoreCase(LinearTerm.Comparator.GREATER.toString())) {
					comparator = LinearTerm.Comparator.GREATER;
				} else if (operator.equalsIgnoreCase(LinearTerm.Comparator.GREATEREQUAL.toString())) {
					comparator = LinearTerm.Comparator.GREATEREQUAL;
				} else if (operator.equalsIgnoreCase(LinearTerm.Comparator.LESS.toString())) {
					comparator = LinearTerm.Comparator.LESS;
				} else if (operator.equalsIgnoreCase(LinearTerm.Comparator.LESSEQUAL.toString())) {
					comparator = LinearTerm.Comparator.LESSEQUAL;
				} else {
					throw new IllegalArgumentException("Unknown operator: " + operator);
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
			LiteralTerm<Object> term = new LiteralTerm<>(object);
			return term;
		}
	}

	/**
	 * Transforms a given {@link String} to a helper {@link Object} that
	 * represents {@link Term}s as {@link List}s of helper {@link Object}s and
	 * variables as {@link String}s.
	 * 
	 * @param string
	 *            the string encoding the {@link Term}
	 * @return a helper object to parse the given string
	 */
	protected static Object toObject(ParseString string) {
		string.skipSpaces();
		if (string.getCurrent() == '(') { // AND, OR, Linear, NOT
			string.next();
			List<Object> list = new ArrayList<>();

			// Operator
			StringBuffer operator = new StringBuffer();
			while (!string.isEnd() && string.getCurrent() != ' ' && string.getCurrent() != '\n') {
				operator.append(string.getCurrent());
				string.next();
			}
			list.add(operator.toString());

			// Arguments
			while (!string.isEnd()) {
				string.skipSpaces();
				Object obj = toObject(string);
				list.add(obj);
				string.skipSpaces();
				if (!string.isEnd() && string.getCurrent() == ')') {
					string.next();
					return list;
				}
			}
			throw new IllegalArgumentException(
					"String " + string + " is not in compliance with the regular expression to describe a Term."
							+ "Missing ')' before EOF.");
		} else if (string.getCurrent() == '"') {
			StringBuffer variable = new StringBuffer();
			string.next();
			while (!string.isEnd()) {
				variable.append(string.getCurrent());
				string.next();
				if (!string.isEnd() && string.getCurrent() == '"') {
					string.next();
					return variable.toString();
				}
			}
			throw new IllegalArgumentException("No closing \" in: " + variable.toString());
		} else {
			throw new IllegalArgumentException(
					"String " + string + " is not in compliance with the regular expression to describe a Term."
							+ "Operands must be enclosed in brackets (), variables must be enclosed in \"\".");
		}
	}

	/**
	 * The {@link ParseString} is a helper class to parse a helper
	 * {@link Object} from a given {@link String}.
	 * 
	 * @author glass
	 * 
	 */
	protected static class ParseString {
		/**
		 * The original {@link String}.
		 */
		protected String string;
		/**
		 * The pointer for the current position.
		 */
		protected int current = 0;

		/**
		 * Constructs a {@link ParseString} with a given {@link String}.
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
			next(1);
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
		 * Returns {@code true} if the end of the {@link String} is reached.
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
			while (!this.isEnd() && (this.getCurrent() == ' ' || this.getCurrent() == '\n')) {
				this.next();
			}
		}
	}
}
