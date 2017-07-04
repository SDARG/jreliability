/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.BDDConstraint.Literal;
import org.jreliability.bdd.BDDConstraint.Pair;
import org.jreliability.booleanfunction.common.LinearTerm;

/**
 * The {@code BDDs} contains common reliabilityFunctions for/on {@code BDD}s.
 * 
 * @author glass, reimann
 * 
 */
public abstract class BDDs {

	/**
	 * The platform-independent newline symbol.
	 */
	protected static String newline = System.getProperty("line.separator");

	/**
	 * Returns all variables (elements) {@code T} included in the {@code BDD}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @return all variables T included in the bdd
	 */
	public static <T> Set<T> getVariables(BDD<T> bdd) {
		Set<T> variables = new HashSet<>();
		collectVariables(bdd, variables, new HashSet<BDD<T>>());
		return variables;
	}

	/**
	 * Returns all nodes of the {@code BDD} that represent the variable {@code
	 * T}.
	 * 
	 * @param <T>
	 *            the type of variable
	 * @param t
	 *            the variable
	 * @param bdd
	 *            the bdd
	 * @return all nodes in the bdd that represent variable t
	 */
	public static <T> Set<BDD<T>> getNodes(T t, BDD<T> bdd) {
		Set<BDD<T>> nodes = new HashSet<>();
		Set<BDD<T>> considered = new HashSet<>();
		collectNodes(bdd, t, considered, nodes);
		return nodes;
	}

	/**
	 * Converts a linear constraint to a BDD.
	 * 
	 * @param <T>
	 *            the type of variables of the BDD
	 * @param coeffs
	 *            the coefficients
	 * @param vars
	 *            the variables
	 * @param comp
	 *            the comparator ("&lt;","&lt;=","=","&gt;=","&gt;")
	 * @param rhs
	 *            the right hand side value
	 * @return the BDD representing this linear constraint
	 */
	public static <T> BDD<T> getBDD(List<Integer> coeffs, List<BDD<T>> vars, LinearTerm.Comparator comp, int rhs,
			BDDProvider<T> provider) {
		assert (coeffs.size() == vars.size());

		BDD<T> result = null;

		if (comp == org.jreliability.booleanfunction.common.LinearTerm.Comparator.EQUAL) {
			BDD<T> ge = getBDD(coeffs, vars, LinearTerm.Comparator.GREATEREQUAL, rhs, provider);
			BDD<T> le = getBDD(coeffs, vars, LinearTerm.Comparator.LESSEQUAL, rhs, provider);
			ge.andWith(le);
			result = ge;
		} else if (comp == org.jreliability.booleanfunction.common.LinearTerm.Comparator.GREATER) {
			result = getBDD(coeffs, vars, LinearTerm.Comparator.GREATEREQUAL, rhs + 1, provider);
		} else if (comp == org.jreliability.booleanfunction.common.LinearTerm.Comparator.GREATEREQUAL) {
			List<Literal<T>> lits = new ArrayList<>();
			for (int i = 0; i < coeffs.size(); i++) {
				int c = coeffs.get(i);
				BDD<T> v = vars.get(i);
				lits.add(new Literal<>(c, v));
			}

			BDDConstraint<T> constraint = new BDDConstraint<>(rhs, lits);

			/*
			 * Handle the case that the lhs is empty and is, thus, 0! If 0 >=
			 * rhs, return true BDD; else return false BDD
			 */
			if (constraint.getLhs().isEmpty()) {
				if (0 >= constraint.getRhs()) {
					result = provider.one();
				} else {
					result = provider.zero();
				}
			} else {
				result = getConstraintBDD(constraint, provider);
			}
		} else if (comp == org.jreliability.booleanfunction.common.LinearTerm.Comparator.LESS) {
			result = getBDD(coeffs, vars, LinearTerm.Comparator.LESSEQUAL, rhs - 1, provider);
		} else if (comp == org.jreliability.booleanfunction.common.LinearTerm.Comparator.LESSEQUAL) {
			List<Integer> negativeCoeffs = new ArrayList<>();
			for (int c : coeffs) {
				negativeCoeffs.add(-c);
			}
			result = getBDD(negativeCoeffs, vars, LinearTerm.Comparator.GREATEREQUAL, -rhs, provider);
		}

		return result;
	}

	/**
	 * Returns a {@code greater-equal} constraint represented as a {@code BDD}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param constraint
	 *            the greater-equal constraint
	 * @return the bdd representation of the given constraint
	 */
	protected static <T> BDD<T> getConstraintBDD(BDDConstraint<T> constraint, BDDProvider<T> provider) {
		List<Literal<T>> literals = constraint.getLhs();

		Collections.sort(literals, new Comparator<Literal<T>>() {
			@Override
			public int compare(Literal<T> o1, Literal<T> o2) {
				return o2.getCoefficient() - o1.getCoefficient();
			}
		});

		int materialLeft = 0;
		for (Literal<T> literal : literals) {
			int coefficient = literal.getCoefficient();
			materialLeft += coefficient;
		}

		int rhs = constraint.getRhs();
		Map<Pair<Integer, Integer>, BDD<T>> memo = new HashMap<>();
		BDD<T> bdd = buildConstraintBDD(literals, rhs, literals.size(), 0, materialLeft, memo, provider);
		return bdd;
	}

	/**
	 * Returns a graphical representation of the {@code BDD} in the {@code DOT}
	 * input format.
	 * 
	 * @param <T>
	 *            the type of variable
	 * @param bdd
	 *            the bdd
	 * @return the bdd as a DOT input string
	 */
	public static <T> String toDot(BDD<T> bdd) {
		StringBuffer dot = new StringBuffer();
		Set<T> elements = getVariables(bdd);
		Map<BDD<T>, String> variables = new HashMap<>();
		Map<T, Integer> counters = new HashMap<>();
		Map<T, String> markers = new HashMap<>();
		for (T t : elements) {
			counters.put(t, 0);
		}
		dot.append("digraph bdd {" + newline);
		collectDotMarkers(bdd, dot, markers);
		collectDotNodes(bdd, dot, variables, counters);
		Set<BDD<T>> considered = new HashSet<>();
		collectDotEdges(bdd, dot, variables, considered);
		collectDotRanks(bdd, dot, variables, markers);
		dot.append("}" + newline);
		return dot.toString();
	}

	/**
	 * Calculates the top event of the {@code BDD} based on a
	 * functionTransformer that delivers for each variable {@code T} a double
	 * value.
	 * 
	 * @param <T>
	 *            the type of variable
	 * @param bdd
	 *            the bdd
	 * @param transformer
	 *            the transformer that returns a double value for each variable
	 * @return the top event of the bdd
	 */
	public static <T> double calculateTop(BDD<T> bdd, Transformer<T, Double> transformer) {
		if (bdd.isOne()) {
			return 1.0;
		}
		if (bdd.isZero()) {
			return 0.0;
		}

		Set<BDD<T>> upSort = new LinkedHashSet<>();
		traverseBDD(bdd, upSort);
		double top = evaluate(bdd, transformer, upSort);
		for (BDD<T> ups : upSort) {
			ups.free();
		}
		return top;
	}

	/**
	 * Traverses the BDD to sort the nodes.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param upSort
	 *            the sorted bdd ndes
	 */
	protected static <T> void traverseBDD(BDD<T> bdd, Set<BDD<T>> upSort) {
		if (bdd.isOne() || bdd.isZero() || upSort.contains(bdd)) {
			return;
		}
		BDD<T> high = bdd.high();
		traverseBDD(high, upSort);
		high.free();
		BDD<T> low = bdd.low();
		traverseBDD(low, upSort);
		low.free();

		upSort.add(bdd.copy());
	}

	/**
	 * Evaluates the BDD to determine the top event.
	 * 
	 * @param <T>
	 *            the type of the variables
	 * @param bdd
	 *            the bdd
	 * @param transformer
	 *            the transformer
	 * @param upSort
	 *            the sorted bdd nodes
	 * @return the top event
	 */
	protected static <T> double evaluate(BDD<T> bdd, Transformer<T, Double> transformer, Set<BDD<T>> upSort) {
		Map<T, Double> values = new HashMap<>();
		HashMap<BDD<T>, Double> bddToDouble = new HashMap<>();

		for (BDD<T> tmpBdd : upSort) {

			T t = tmpBdd.var();
			Double r = values.get(t);
			if (r == null) {
				r = transformer.transform(t);
				values.put(t, r);
			}

			double high;
			double low;
			BDD<T> highBdd = tmpBdd.high();
			if (highBdd.isOne()) {
				high = 1.0;
			} else if (highBdd.isZero()) {
				high = 0.0;
			} else {
				high = bddToDouble.get(highBdd);
			}
			highBdd.free();

			BDD<T> lowBdd = tmpBdd.low();
			if (lowBdd.isOne()) {
				low = 1.0;
			} else if (lowBdd.isZero()) {
				low = 0.0;
			} else {
				low = bddToDouble.get(lowBdd);
			}
			lowBdd.free();

			// Shannon decomposition
			double y = r * high + (1 - r) * low;

			bddToDouble.put(tmpBdd, y);
		}

		double x = bddToDouble.get(bdd);

		for (BDD<T> freeBdd : bddToDouble.keySet()) {
			freeBdd.free();
		}

		return x;
	}

	/**
	 * Returns a {@code greater-equal} constraint represented as a {@code BDD}
	 * via a recursive procedure proposed by {@code Een & Soerrensson 2006}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param literals
	 *            the literals
	 * @param rhs
	 *            the right hand side of the constraint
	 * @param index
	 *            the index
	 * @param sum
	 *            the current sum
	 * @param materialLeft
	 *            the material that is potentially left to be added to the sum
	 * @param memo
	 *            the memo maps each point in the recursion to its bdd
	 * @param provider
	 *            the used bdd provider
	 * @return the bdd representation of the given constraint
	 */
	protected static <T> BDD<T> buildConstraintBDD(List<Literal<T>> literals, int rhs, int index, int sum,
			int materialLeft, Map<Pair<Integer, Integer>, BDD<T>> memo, BDDProvider<T> provider) {
		if (sum >= rhs) {
			return provider.one();
		} else if (sum + materialLeft < rhs) {
			return provider.zero();
		}

		Pair<Integer, Integer> key = new Pair<>(index, sum);
		if (!memo.containsKey(key)) {
			index--;
			int coefficient = literals.get(index).getCoefficient();
			materialLeft -= coefficient;
			int hiSum = sum + coefficient;
			int loSum = sum;
			BDD<T> hiBDD = buildConstraintBDD(literals, rhs, index, hiSum, materialLeft, memo, provider);
			BDD<T> loBDD = buildConstraintBDD(literals, rhs, index, loSum, materialLeft, memo, provider);
			BDD<T> ifBDD = literals.get(index).getVariable();
			BDD<T> resultBDD = ifBDD.ite(hiBDD, loBDD);
			memo.put(key, resultBDD);
		}

		return memo.get(key);
	}

	/**
	 * Traverses the {@code BDD} to collects all nodes for the {@code DOT}
	 * representation.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param dot
	 *            the dot input
	 * @param variables
	 *            the used variables
	 * @param counters
	 *            the used counters for each element
	 */
	protected static <T> void collectDotNodes(BDD<T> bdd, StringBuffer dot, Map<BDD<T>, String> variables,
			Map<T, Integer> counters) {
		if (variables.containsKey(bdd)) {
			return;
		} else if (bdd.isOne()) {
			dot.append(
					"one [label = \"1\", rank = sink, shape = box, style = filled, color = black, fontcolor = white];"
							+ newline);
			variables.put(bdd, "one");
			return;
		} else if (bdd.isZero()) {
			dot.append(
					"zero [label = \"0\", rank = sink, shape = box, style = filled, color = black, fontcolor = white];"
							+ newline);
			variables.put(bdd, "zero");
			return;
		}

		T t = bdd.var();
		int count = counters.get(t);
		counters.put(t, count + 1);
		String id = t.toString();
		id = id.replaceAll(" |-", "_");
		String variable = "n" + id + count;
		variables.put(bdd, variable);
		dot.append(variable + " [label = \"" + t.toString() + "\", style = filled, fillcolor = gray95, color = black];"
				+ newline);

		collectDotNodes(bdd.high(), dot, variables, counters);
		collectDotNodes(bdd.low(), dot, variables, counters);
	}

	/**
	 * Traverses the {@code BDD} to collects all edges for the {@code DOT}
	 * representation.
	 * 
	 * @param <T>
	 *            the type of variable
	 * @param bdd
	 *            the bdd
	 * @param dot
	 *            the dot input string
	 * @param variables
	 *            the used variables
	 * @param considered
	 *            the marker for already considered bdds
	 */
	protected static <T> void collectDotEdges(BDD<T> bdd, StringBuffer dot, Map<BDD<T>, String> variables,
			Set<BDD<T>> considered) {
		if (considered.contains(bdd) || bdd.isOne() || bdd.isZero()) {
			return;
		}

		BDD<T> high = bdd.high();
		BDD<T> low = bdd.low();

		String variable = variables.get(bdd);
		String highVariable = variables.get(high);
		String lowVariable = variables.get(low);

		dot.append(variable + " -> " + highVariable + " [style = solid, arrowsize = 0.8];" + newline);
		dot.append(variable + " -> " + lowVariable + " [style = dashed, arrowsize = 0.8];" + newline);

		considered.add(bdd);

		collectDotEdges(bdd.high(), dot, variables, considered);
		collectDotEdges(bdd.low(), dot, variables, considered);
	}

	/**
	 * Traverses the {@code BDD} to setup the correct ranks of all nodes
	 * belonging to the same variable.
	 * 
	 * @param <T>
	 *            the type of variable
	 * @param bdd
	 *            the bdd
	 * @param dot
	 *            the dot input string
	 * @param markers
	 *            the marker variables for each variable
	 */
	protected static <T> void collectDotMarkers(BDD<T> bdd, StringBuffer dot, Map<T, String> markers) {
		List<T> elements = new ArrayList<>();
		collectVariablesSorted(bdd, elements);
		List<T> tmpList = new ArrayList<>();
		for (int i = 0; i < elements.size(); i++) {
			T t = elements.get(i);
			if (t != null) {
				String id = t.toString();
				id = id.replaceAll(" |-", "_");
				String variable = "marker" + id;
				dot.append(variable + " [label = \"" + t.toString() + "\", shape = plaintext];" + newline);
				tmpList.add(t);
				markers.put(t, variable);
			}
		}

		Iterator<T> iterator = tmpList.iterator();
		T current = iterator.next();
		while (iterator.hasNext()) {
			T next = iterator.next();
			String currentVariable = markers.get(current);
			String nextVariable = markers.get(next);
			dot.append(currentVariable + " -> " + nextVariable + " [style = invis];" + newline);
			current = next;
		}
	}

	/**
	 * Traverses the {@code BDD} to setup the correct ranks of all nodes
	 * belonging to the same variable.
	 * 
	 * @param <T>
	 *            the type of variable
	 * @param bdd
	 *            the bdd
	 * @param dot
	 *            the dot input
	 * @param variables
	 *            the used variables
	 * @param markers
	 *            the marker variables
	 */
	protected static <T> void collectDotRanks(BDD<T> bdd, StringBuffer dot, Map<BDD<T>, String> variables,
			Map<T, String> markers) {

		for (Entry<T, String> entry : markers.entrySet()) {
			T t = entry.getKey();
			String variable = entry.getValue();
			Set<BDD<T>> nodes = getNodes(t, bdd);

			String tmpDot = "{ rank = same; " + variable + "; ";
			for (BDD<T> nodeBDD : nodes) {
				String nodeVariable = variables.get(nodeBDD);
				tmpDot += nodeVariable + "; ";
			}

			tmpDot += "}" + newline;
			dot.append(tmpDot);
		}
	}

	/**
	 * Traverses the {@code BDD} to collect all variables.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param variables
	 *            the variables
	 * @param considered
	 *            the already considered bdds
	 */
	protected static <T> void collectVariables(BDD<T> bdd, Set<T> variables, Set<BDD<T>> considered) {
		if (bdd.isOne() || bdd.isZero()) {
			return;
		} else if (considered.contains(bdd)) {
			return;
		}
		BDD<T> high = bdd.high();
		collectVariables(high, variables, considered);
		high.free();

		BDD<T> low = bdd.low();
		collectVariables(low, variables, considered);
		low.free();

		variables.add(bdd.var());
		considered.add(bdd);

		// TODO Take care of the BDDs in considered (free)
	}

	/**
	 * Traverses the {@code BDD} to collect all variables in the current
	 * variable order of the {@code BDD}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param variables
	 *            the variables
	 */
	protected static <T> void collectVariablesSorted(BDD<T> bdd, List<T> variables) {
		if (bdd.isOne() || bdd.isZero() || variables.contains(bdd.var())) {
			return;
		}
		int level = bdd.level();
		T t = bdd.var();
		collectVariablesSorted(bdd.high(), variables);
		collectVariablesSorted(bdd.low(), variables);
		int size = variables.size();
		if (level > size) {
			for (int i = size; i <= level; i++) {
				variables.add(null);
			}
		}
		variables.add(level, t);
	}

	/**
	 * Traverses the {@code BDD} to collect all nodes for a given variable
	 * {@code T}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param bdd
	 *            the bdd
	 * @param t
	 *            the variable
	 * @param considered
	 *            the already considered bdds
	 * @param nodes
	 *            the found nodes
	 */
	protected static <T> void collectNodes(BDD<T> bdd, T t, Set<BDD<T>> considered, Set<BDD<T>> nodes) {
		if (bdd.isOne() || bdd.isZero() || considered.contains(bdd)) {
			return;
		}
		considered.add(bdd);
		collectNodes(bdd.high(), t, considered, nodes);
		collectNodes(bdd.low(), t, considered, nodes);
		T nodeT = bdd.var();
		if (nodeT.equals(t)) {
			nodes.add(bdd);
		}
	}

}
