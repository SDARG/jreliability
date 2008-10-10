package de.cs12.reliability.javabdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javabdd.BDDException;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BuDDyFactory;
import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code BuDDyProvider} used to get {@code BuDDy} BDDs.
 * 
 * @author glass, reimann, lukasiewycz
 * @param <T>
 *            the type of the variables
 */
public class BuDDyProvider<T> implements BDDProvider<T> {
	protected static int variableOffset = 0;

	protected static BDDFactory bddFactory;
	protected static boolean factoryInit = false;
	protected static int varNum;

	protected static Map<Object, Integer> variableToInt = new HashMap<Object, Integer>();
	protected static Map<Integer, Object> intToVariable = new HashMap<Integer, Object>();

	/**
	 * Constructs a {@code BuDDyProvider} with a given number of variables.
	 * 
	 * @param vars
	 *            the number of variables
	 */
	public BuDDyProvider(int vars) {
		if (!factoryInit) {
			factoryInit = true;
			bddFactory = BuDDyFactory.init(1000000, 1000000);
			bddFactory.setVarNum(vars);
			varNum = vars;
			bddFactory.autoReorder(BDDFactory.REORDER_SIFT);
		}

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
	public BuDDy<T> zero() {
		return new BuDDy<T>(this, bddFactory.zero());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#one()
	 */
	public BuDDy<T> one() {
		return new BuDDy<T>(this, bddFactory.one());
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
		if (variableToInt.size() > 1000) {
			throw new IndexOutOfBoundsException(
					"Maximal number of variables (1000) for BuDDy reached.");
		}

		if (variableToInt.size() > varNum) {
			varNum = Math.min(varNum * 2, 1000);
			try {
				bddFactory.setVarNum(varNum);
			} catch (BDDException e) {
				throw new IndexOutOfBoundsException("Setting variable number to " + varNum+ " failed");
			}
		}

		int var = variableToInt.get(variable);

		net.sf.javabdd.BDD bdd;
		try {

			bdd = bddFactory.ithVar(var);
		} catch (BDDException e) {
			throw new IndexOutOfBoundsException("Unknown variable " + var
					+ " (size: " + varNum + ")");
		}

		return new BuDDy<T>(this, bdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public T get(BDD<T> bdd) {
		return (T) intToVariable.get(((BuDDy<T>) bdd).bdd.var());
	}
}
