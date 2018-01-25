package org.jreliability.sl;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.Distribution;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@link SLReliabilityFunction} represents the {@link ReliabilityFunction}
 * using stochastic logic as implemented by {@link SL}.
 * 
 * @author glass, jlee
 * 
 * @param <T>
 *            the type of variable
 */
public class SLReliabilityFunction<T> implements ReliabilityFunction {

	/**
	 * The {@link SL} representing the {@link Distribution}.
	 */
	protected final SL<T> stochasticLogic;

	/**
	 * The used {@link Transformer} to get the {@link ReliabilityFunction} of
	 * each element of the {@link SL}.
	 */
	protected final Transformer<T, ReliabilityFunction> functionTransformer;

	/**
	 * Constructs a {@link SLReliabilityFunction} with a given {@link SL} and
	 * {@link Transformer}.
	 * 
	 * @param stochasticLogic
	 *            the stochastic logic representing the reliabilityFunction
	 * @param functionTransformer
	 *            the functionTransformer to transform stochastic logic elements
	 *            to reliabilityFunction
	 */
	public SLReliabilityFunction(SL<T> stochasticLogic, Transformer<T, ReliabilityFunction> functionTransformer) {
		this.stochasticLogic = stochasticLogic;
		this.functionTransformer = functionTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(double)
	 */
	@Override
	public double getY(final double x) {

		final Transformer<T, Double> t = new Transformer<T, Double>() {
			/**
			 * Default {@link Transformer}.
			 * 
			 * @param a
			 *            parameter a
			 * @return the double value of a
			 */
			@Override
			public Double transform(T a) {
				return functionTransformer.transform(a).getY(x);
			}
		};

		return stochasticLogic.getProbabiliy(t);
	}

}
