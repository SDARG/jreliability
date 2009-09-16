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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;

/**
 * The {@code BDDTopEvent} allows the fast calculation of the top event for a
 * given {@code BDD}. Here, the {@code BDD} is copied to an internal structure
 * such that the {@code free} method of the {@code BDD} does not interfere with
 * this class.
 * 
 * @author lukasiewycz
 * 
 * @param <T>
 *            the type of variables
 */
public class BDDTopEvent<T> {

	/**
	 * 
	 * The {@code Var} is an internal variable class.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class Var {

		/**
		 * The variable.
		 */
		protected final T variable;

		/**
		 * The vale of the variable.
		 */
		protected double value;

		/**
		 * Constructs a {@code Var}.
		 * 
		 * @param variable
		 *            the original variable
		 */
		public Var(T variable) {
			super();
			this.variable = variable;
		}

		/**
		 * Returns the current value of this variable.
		 * 
		 * @return the current value
		 */
		public double getValue() {
			return value;
		}

		/**
		 * Sets the current value of this variable.
		 * 
		 * @param value
		 *            the value to be set
		 */
		public void setValue(double value) {
			this.value = value;
		}
	}

	/**
	 * The {@code Node} is an internal BDD node.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	interface Node {

		/**
		 * Returns the current value of this node.
		 * 
		 * @return the
		 */
		public double getValue();

	}

	/**
	 * The {@code VarNode} is an internal variable BDD node.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class VarNode implements Node {

		/**
		 * The variable represented by this node.
		 */
		protected final Var var;
		/**
		 * The value of the node.
		 */
		protected double value;
		/**
		 * The successor node at the high edge.
		 */
		protected Node hi = null;
		/**
		 * The successor node at the low edge.
		 */
		protected Node lo = null;

		/**
		 * Constructs a {@code VarNode}.
		 * 
		 * @param var
		 *            the variable of this node
		 */
		public VarNode(Var var) {
			this.var = var;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jreliability.bdd.BDDTopEvent.Node#getValue()
		 */
		public double getValue() {
			return value;
		}

		/**
		 * Sets the current value of this node.
		 * 
		 * @param value
		 *            the value to be set
		 */
		public void setValue(double value) {
			this.value = value;
		}

		/**
		 * Returns the {@code Node} of the high-branch.
		 * 
		 * @return the {@code Node} of the high-branch
		 */
		public Node getHi() {
			return hi;
		}

		/**
		 * Sets the {@code Node} of the high-branch.
		 * 
		 * @param hi
		 *            the {@code Node} of the high-branch
		 */
		public void setHi(Node hi) {
			this.hi = hi;
		}

		/**
		 * Returns the {@code Node} of the low-branch.
		 * 
		 * @return the {@code Node} of the low-branch
		 */
		public Node getLo() {
			return lo;
		}

		/**
		 * Sets the {@code Node} of the low-branch.
		 * 
		 * @param lo
		 *            the {@code Node} of the low-branch
		 */
		public void setLo(Node lo) {
			this.lo = lo;
		}

		/**
		 * Returns the variable of this node.
		 * 
		 * @return the variable of this node
		 */
		public Var getVar() {
			return var;
		}

	}

	/**
	 * The one node.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class NodeOne implements Node {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jreliability.bdd.BDDTopEvent.Node#getValue()
		 */
		public double getValue() {
			return 1;
		}
	}

	/**
	 * The zero node.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	class NodeZero implements Node {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jreliability.bdd.BDDTopEvent.Node#getValue()
		 */
		public double getValue() {
			return 0;
		}
	}

	/**
	 * Map of the original variables to the internal structure of the variables.
	 */
	Map<T, Var> variables = new HashMap<T, Var>();

	/**
	 * A list of all variables nodes (ordered from bottom to the root).
	 */
	List<VarNode> nodes = new ArrayList<VarNode>();

	/**
	 * The instance of the zero node.
	 */
	Node zero = new NodeZero();

	/**
	 * The instance of the one node.
	 */
	Node one = new NodeOne();

	/**
	 * The root of the bdd.
	 */
	Node root;

	/**
	 * Constructs the {@code BDDTopEvent} calculator for a given {@code BDD}.
	 * 
	 * @param bdd
	 *            the given bdd
	 */
	public BDDTopEvent(BDD<T> bdd) {

		Map<BDD<T>, VarNode> map = new HashMap<BDD<T>, VarNode>();
		root = build(bdd, map);
		for (BDD<T> b : map.keySet()) {
			b.free();
		}

		// System.out.println("Size "+nodes.size()+" | "+variables.values());

	}

	/**
	 * Calculates the top event for the values given by the functionTransformer.
	 * 
	 * @param transformer
	 *            the transformer from the variables to the values
	 * @return the top event as a {@code double} value
	 */
	public double calculate(Transformer<T, Double> transformer) {

		for (Entry<T, Var> entry : variables.entrySet()) {
			T t = entry.getKey();
			Var var = entry.getValue();
			double value = transformer.transform(t);
			var.setValue(value);
		}

		for (VarNode node : nodes) {

			double r = node.getVar().getValue();

			double low = node.getLo().getValue();
			double high = node.getHi().getValue();

			// Shannon decomposition
			double y = r * high + (1 - r) * low;
			node.setValue(y);
		}

		return root.getValue();
	}

	/**
	 * Returns the internal variable for a bdd.
	 * 
	 * @param bdd
	 *            the bdd
	 * @return the corresponding internal variable
	 */
	protected Var getVariable(BDD<T> bdd) {
		T t = bdd.var();
		Var var = variables.get(t);
		if (var == null) {
			var = new Var(t);
			variables.put(t, var);
		}
		return var;

	}

	/**
	 * Builds the internal node from a bdd node.
	 * 
	 * @param bdd
	 *            the original bdd node
	 * @param map
	 *            a map from the internal node to the bdd node
	 * @return the internal node
	 */
	protected Node build(BDD<T> bdd, Map<BDD<T>, VarNode> map) {
		if (bdd.isOne()) {
			return one;
		} else if (bdd.isZero()) {
			return zero;
		} else {
			VarNode node = map.get(bdd);
			if (node != null) {
				return node;
			}
		}
		VarNode node = new VarNode(getVariable(bdd));
		map.put(bdd.copy(), node);

		BDD<T> high = bdd.high();
		node.setHi(build(high, map));
		high.free();
		BDD<T> low = bdd.low();
		node.setLo(build(low, map));
		low.free();

		nodes.add(node);

		return node;
	}

}
