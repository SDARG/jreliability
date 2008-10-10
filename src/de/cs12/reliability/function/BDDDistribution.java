package de.cs12.reliability.function;

import de.cs12.reliability.bdd.BDD;

/**
 * The {@code BDDDistribution}
 * 
 * @author glass
 * 
 */
public class BDDDistribution<T> extends AbstractDistribution {

	/**
	 * The BDD representing the {@code Distribution}.
	 */
	protected final BDD<T> bdd;

	/**
	 * The used {@code FunctionTransformer} to get the {@code Function} of each
	 * element of the {@code BDD}.
	 */
	protected final FunctionTransformer<T> transformer;

	/**
	 * Constructs a {@code BDDDistribution} with a given {@code BDD} and
	 * {@code FunctionTransformer}.
	 * 
	 * @param bdd
	 *            the bdd representing the distribution
	 * 
	 * @param transformer
	 *            the transformer to transform bdd elements to function
	 */
	public BDDDistribution(BDD<T> bdd, FunctionTransformer<T> transformer) {
		super();
		this.bdd = bdd;
		this.transformer = transformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.function.Function#getY(double)
	 */
	@Override
	public double getY(double x) {
		return Top.calculateTop(bdd, transformer, x);
	}

	/**
	 * Returns the {@code BDD}.
	 * 
	 * @return the bdd
	 */
	public BDD<T> getBdd() {
		return bdd;
	}

	/**
	 * Returns the {@code Transformer}.
	 * 
	 * @return the transformer
	 */
	public FunctionTransformer<T> getTransformer() {
		return transformer;
	}

}
