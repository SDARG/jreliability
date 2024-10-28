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

package org.jreliability.bdd.javabdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;

import com.github.javabdd.BDDException;
import com.github.javabdd.BDDFactory;
import com.github.javabdd.JFactory;

/**
 * The {@link JBDDProvider} used to get {@link JBDD} BDDs.
 * 
 * @author glass, reimann
 * @param <T> the type of the variables
 */
public class JBDDProvider<T> implements BDDProvider<T> {
	/**
	 * The offset of the variables.
	 */
	protected int variableOffset = 0;

	/**
	 * The used {@link BDDFactory}.
	 */
	protected BDDFactory factory;

	/**
	 * A translation of the variable to an {@link Integer} for the real {@link BDD}.
	 */
	protected Map<T, Integer> variableToInt = new HashMap<>();
	/**
	 * A translation of the {@link Integer} in the real {@link BDD} to the variable.
	 */
	protected Map<Integer, T> intToVariable = new HashMap<>();

	/**
	 * The number of variables.
	 */
	protected int vars;

	/**
	 * The factor to extend the number of variables in case more variables are
	 * required.
	 */
	protected int variableGrowthFactor;

	/**
	 * Constructs a {@link JBDDProvider} with a given number of variables.
	 * 
	 * @param vars the number of variables
	 */
	public JBDDProvider(int vars) {
		this(vars, 2, 20000);
	}

	/**
	 * Constructs a {@link JBDDProvider} with a given number of variables, the
	 * growth rate of the number of variables, and the initial number of nodes.
	 * 
	 * @param vars                 the number of variables
	 * @param variableGrowthFactor the factor by which to extend the number of
	 *                             variables if required
	 * @param initialNumberofNodes the initial number of nodes reserved in the BDD
	 *                             factory
	 */
	public JBDDProvider(int vars, int variableGrowthFactor, int initialNumberofNodes) {
		factory = JFactory.init(initialNumberofNodes, initialNumberofNodes);
		factory.autoReorder(BDDFactory.REORDER_SIFT);

		factory.setVarNum(vars);
		this.vars = vars;
		this.variableGrowthFactor = variableGrowthFactor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProvider#add(java.util.List)
	 */
	@Override
	public void add(List<T> variables) {
		for (T variable : variables) {
			variableToInt.put(variable, variableOffset);
			intToVariable.put(variableOffset, variable);
			variableOffset++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProvider#add(T[])
	 */
	@Override
	public void add(@SuppressWarnings("unchecked") T... variables) {
		add(Arrays.asList(variables));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProvider#zero()
	 */
	@Override
	public BDD<T> zero() {
		com.github.javabdd.BDD bdd = factory.zero();
		return new JBDD<>(this, bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProvider#one()
	 */
	@Override
	public BDD<T> one() {
		return new JBDD<>(this, factory.one());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProvider#get(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public BDD<T> get(T variable) {
		if (!variableToInt.containsKey(variable)) {
			add(variable);
		} else {
			// Once a BDD is constructed, further changes in the reliability
			// attributes of implementations do not apply to its variables.
			// Therefore, to support multiple analysis of the same BDD with
			// different reliability values, we update the variables.
			Integer offset = variableToInt.get(variable);
			// Since the attributes of the input variable may differ from those
			// of the corresponding, existing variable, we overwrite it into the
			// maps to update attributes of the stored variable
			variableToInt.remove(variable);
			variableToInt.put(variable, offset);
			intToVariable.remove(offset);
			intToVariable.put(offset, variable);
		}

		if (variableToInt.size() > vars) {
			vars *= variableGrowthFactor;
			try {
				factory.setVarNum(vars);
			} catch (BDDException e) {
				throw new IndexOutOfBoundsException("Setting variable number to " + vars + " failed");
			}
		}

		int var = variableToInt.get(variable);

		com.github.javabdd.BDD bdd;
		try {
			bdd = factory.ithVar(var);
		} catch (BDDException e) {
			throw new IndexOutOfBoundsException("Unknown variable " + var + " (size: " + vars + ")");
		}

		return new JBDD<>(this, bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProvider#get(org.jreliability.bdd.BDD)
	 */
	@Override
	public T get(BDD<T> bdd) {
		return intToVariable.get(((JBDD<T>) bdd).bdd.var());
	}

	/**
	 * Returns the used {@link BDDFactory}.
	 * 
	 * @return the used bdd factory
	 */
	public BDDFactory getFactory() {
		return factory;
	}
}
