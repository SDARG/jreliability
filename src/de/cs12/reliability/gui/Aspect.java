package de.cs12.reliability.gui;

import java.util.SortedMap;
import java.util.TreeMap;

import de.cs12.reliability.common.Sample;
import de.cs12.reliability.common.Samples;

/**
 * The {@code Aspect} allows to deal with a certain aspect included in a {@code
 * Sample}.
 * 
 * @author glass
 * 
 */
public class Aspect {

	protected final Aspects aspect;
	Samples samples;
	protected final String name, y;
	protected final String x = "time t";

	/**
	 * The {@code Aspects} that are available.
	 * 
	 * @author glass
	 * 
	 */
	public enum Aspects {
		/**
		 * The distribution function R(t).
		 */
		DISTRIBUTION,
		/**
		 * The density function f(t).
		 */
		DENSITY,
		/**
		 * The failure rate lambda(t).
		 */
		LAMBDA;
	}

	/**
	 * Constructs an {@code Aspect} with a a certain {@code Aspects} and the
	 * {@code Samples}.
	 * 
	 * @param aspect
	 *            the current aspect
	 * @param samples
	 *            the samples
	 */
	public Aspect(Aspects aspect, Samples samples) {
		this.aspect = aspect;
		this.samples = samples;
		switch (this.aspect) {
		case DISTRIBUTION:
			this.name = "Distribution";
			this.y = "distribution R(t)";
			break;
		case DENSITY:
			this.name = "Density";
			this.y = "density f(t)";
			break;
		case LAMBDA:
			this.name = "Failure Rate";
			this.y = "failure rate lambda(t)";
			break;
		default:
			this.name = "Undefined Aspect";
			this.y = "undefined";
			break;
		}
	}

	/**
	 * Returns the {@code Samples}.
	 * 
	 * @return the samples
	 */
	public Samples getSamples() {
		return samples;
	}

	/**
	 * Returns the values that are plotted for the current {@code Aspect}.
	 * 
	 * @return the values that are plotted for the current aspect
	 */
	public SortedMap<Double, Double> getValues() {
		SortedMap<Double, Double> values = new TreeMap<Double, Double>();
		for (Sample sample : samples) {
			double time = sample.getTime();
			double value;
			switch (aspect) {
			case DISTRIBUTION:
				value = sample.getDistribution();
				break;
			case DENSITY:
				value = sample.getDensity();
				break;
			case LAMBDA:
				value = sample.getLambda();
				break;
			default:
				value = 0.0;
				break;
			}
			values.put(time, value);
		}
		return values;
	}

	/**
	 * Returns the name of the {@code Aspect}.
	 * 
	 * @return the name of the aspect
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the label for the {@code x-axis}.
	 * 
	 * @return the label for the x-axis
	 */
	public String getX() {
		return x;
	}

	/**
	 * Returns the label for the {@code x-axis}.
	 * 
	 * @return the label for the y-axis
	 */
	public String getY() {
		return y;
	}

}
