package de.cs12.reliability.gui;

import de.cs12.reliability.evaluator.IntegralEvaluator;
import de.cs12.reliability.function.Function;

/**
 * The {@code FailureRateAspect}
 * 
 * @author glass
 * 
 */
public class FailureRateAspect extends AbstractAspect {

	/**
	 * Constructs a {@code FailureRateAspect}.
	 */
	public FailureRateAspect() {
		super("Failure Rate", "time t", "failure rate lambda(t)");
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
		double deltaT = 0.00000001;
		double y = function.getY(x);
		double yPrime = function.getY(x + deltaT);
		double density = (y - yPrime) / deltaT;
		double lambda = density / y;
		return lambda;
	}

}
