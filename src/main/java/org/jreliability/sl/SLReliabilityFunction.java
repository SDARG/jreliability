package org.jreliability.sl;

import org.apache.commons.collections15.Transformer;
import org.jreliability.function.ReliabilityFunction;

/**
 * @author jlee, glass
 *
 * @param <T>
 */
public class SLReliabilityFunction<T> implements ReliabilityFunction {

	protected final SL<T> stochasticLogic;
	protected final Transformer<T, ReliabilityFunction> functionTransformer;

	public SLReliabilityFunction(SL<T> stochasticLogic, Transformer<T, ReliabilityFunction> functionTransformer) {
		this.stochasticLogic = stochasticLogic;
		this.functionTransformer = functionTransformer;
	}

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
