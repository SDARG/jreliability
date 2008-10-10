package de.cs12.reliability.javabdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.CALFactory;
import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code CALProvider} used to get {@code CAL} BDDs.
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public class CALProvider<T> implements BDDProvider<T> {
	protected static int variableOffset = 0;

	protected static BDDFactory bddFactory;
	protected static boolean factoryInit = false;
	protected static int vars;

	protected static Map<Object, Integer> variableToInt = new HashMap<Object, Integer>();
	protected static Map<Integer, Object> intToVariable = new HashMap<Integer, Object>();

	/**
	 * Constructs a {@code CALProvider} with a given number of variables.
	 * 
	 * @param vars
	 *            the number of variables
	 */
	public CALProvider(int vars) {
		if (!factoryInit) {
			factoryInit = true;
			bddFactory = CALFactory.init(100000, 1000000);
			bddFactory.setVarNum(vars);
			CALProvider.vars = vars;
			//bddFactory.autoReorder(BDDFactory.REORDER_SIFT);
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
	public CAL<T> zero() {
		return new CAL<T>(this, bddFactory.zero());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#one()
	 */
	public CAL<T> one() {
		return new CAL<T>(this, bddFactory.one());
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
		
		if(vars < variableToInt.size()){
			vars *= 2;
			bddFactory.setVarNum(vars);
		}
		
		return new CAL<T>(this, bddFactory.ithVar(variableToInt.get(variable)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public T get(BDD<T> bdd) {
		return (T)intToVariable.get(((CAL<T>) bdd).bdd.var());
	}
}
