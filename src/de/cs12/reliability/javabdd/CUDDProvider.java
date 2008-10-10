package de.cs12.reliability.javabdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.CUDDFactory;
import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code CUDDProvider} used to get {@code CUDD} BDDs.
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public class CUDDProvider<T> implements BDDProvider<T> {
	protected static int variableOffset = 0;

	protected static BDDFactory bddFactory;
	protected static boolean factoryInit = false;
	protected static int vars;

	protected static Map<Object, Integer> variableToInt = new HashMap<Object, Integer>();
	protected static Map<Integer, Object> intToVariable = new HashMap<Integer, Object>();

	/**
	 * Constructs a {@code CUDDProvider} with a given number of variables.
	 * 
	 * @param vars
	 *            the number of variables
	 */
	public CUDDProvider(int vars) {
		if (!factoryInit) {
			factoryInit = true;
			bddFactory = CUDDFactory.init(100000, 1000000);
			bddFactory.setVarNum(vars);
			CUDDProvider.vars = vars;
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
	public CUDD<T> zero() {
		return new CUDD<T>(this, bddFactory.zero());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#one()
	 */
	public CUDD<T> one() {
		return new CUDD<T>(this, bddFactory.one());
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
		
		return new CUDD<T>(this, bddFactory.ithVar(variableToInt.get(variable)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(de.cs12.reliability.bdd.BDD)
	 */
	@SuppressWarnings("unchecked")
	public T get(BDD<T> bdd) {
		return (T)intToVariable.get(((CUDD<T>) bdd).bdd.var());
	}
}
