package de.cs12.reliability.javabdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javabdd.BDDException;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BuDDyFactory;
import net.sf.javabdd.CALFactory;
import net.sf.javabdd.CUDDFactory;
import net.sf.javabdd.JDDFactory;
import net.sf.javabdd.JFactory;
import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.javabdd.JBDDProviderFactory.Type;

/**
 * The {@code JBDDProvider} used to get {@code JBDD} BDDs.
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public class JBDDProvider<T> implements BDDProvider<T> {
	protected int variableOffset = 0;

	protected Type type;

	protected BDDFactory factory;

	protected Map<T, Integer> variableToInt = new HashMap<T, Integer>();
	protected Map<Integer, T> intToVariable = new HashMap<Integer, T>();
	protected int vars;

	/**
	 * Constructs a {@code JDDProvider} with a given number of variables.
	 * 
	 * @param vars
	 *            the number of variables
	 */
	public JBDDProvider(Type type, int vars) {
		this.type = type;

		switch (type) {
		case BUDDY:
			factory = BuDDyFactory.init(1000000, 1000000);
			factory.autoReorder(BDDFactory.REORDER_SIFT);
			break;
		case CUDD:
			factory = CUDDFactory.init(1000000, 1000000);
			break;
		case CAL:
			factory = CALFactory.init(1000000, 1000000);
			break;
		case JDD:
			factory = JDDFactory.init(200000, 200000);
			break;
		default:
			factory = JFactory.init(200000, 200000);
			factory.autoReorder(BDDFactory.REORDER_SIFT);
		}

		factory.setVarNum(vars);
		this.vars = vars;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#add(java.util.List)
	 */
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
	 * @see de.cs12.reliability.bdd.BDDProvider#add(T[])
	 */
	public void add(T... variables) {
		add(Arrays.asList(variables));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#zero()
	 */
	public BDD<T> zero() {
		net.sf.javabdd.BDD bdd = factory.zero();
		return new JBDD<T>(this, bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#one()
	 */
	public BDD<T> one() {
		return new JBDD<T>(this, factory.one());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> get(T variable) {
		if (!variableToInt.containsKey(variable)) {
			add(variable);
		}

		if (type == Type.BUDDY) {
			if (variableToInt.size() > 1000) {
				throw new IndexOutOfBoundsException(
						"Maximal number of variables (1000) for BuDDy reached.");
			}
		}

		if (variableToInt.size() > vars) {
			vars *= 2;
			if (type == Type.BUDDY) {
				vars = Math.min(vars, 1000);
			}
			try {
				factory.setVarNum(vars);
			} catch (BDDException e) {
				throw new IndexOutOfBoundsException(
						"Setting variable number to " + vars + " failed");
			}
		}

		int var = variableToInt.get(variable);

		net.sf.javabdd.BDD bdd;
		try {

			bdd = factory.ithVar(var);
		} catch (BDDException e) {
			throw new IndexOutOfBoundsException("Unknown variable " + var
					+ " (size: " + vars + ")");
		}

		return new JBDD<T>(this, bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(de.cs12.reliability.bdd.BDD)
	 */
	public T get(BDD<T> bdd) {
		return intToVariable.get(((JBDD<T>) bdd).bdd.var());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.javabdd.JBDDProvider#getFactory()
	 */
	public BDDFactory getFactory() {
		return factory;
	}
}
