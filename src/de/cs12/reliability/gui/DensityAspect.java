package de.cs12.reliability.gui;

import de.cs12.reliability.evaluator.IntegralEvaluator;
import de.cs12.reliability.function.Function;

/**
 * The {@code DensityAspect} represents the density of a {@code Function}.
 * 
 * @author glass
 * 
 */
public class DensityAspect extends AbstractAspect {

	/**
	 * Constructs a {@code DensityAspect}.
	 */
	public DensityAspect() {
		super("Density", "time t", "density function f(t)");
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
		return density;
	}

}
