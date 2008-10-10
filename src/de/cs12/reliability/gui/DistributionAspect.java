package de.cs12.reliability.gui;

import de.cs12.reliability.evaluator.IntegralEvaluator;
import de.cs12.reliability.function.Function;

/**
 * The {@code DistributionAspect} represents the distribution of a
 * {@code Function}.
 * 
 * @author glass
 * 
 */
public class DistributionAspect extends AbstractAspect {

	/**
	 * Constructs a {@code DistributionAspect}.
	 * 
	 */
	public DistributionAspect() {
		super("Distribution", "time t", "distribution function R(t)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getUpper(de.cs12.reliability.function.Function)
	 */
	@Override
	public double getUpper(Function function) {
		IntegralEvaluator evaluator = new IntegralEvaluator();
		return evaluator.getUpperBound(function);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.gui.Aspect#getY(double,
	 *      de.cs12.reliability.function.Function)
	 */
	@Override
	public double getY(double x, Function function) {
		return function.getY(x);
	}

}
