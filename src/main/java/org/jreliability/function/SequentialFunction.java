/**
 * 
 */
package org.jreliability.function;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SequentialFunction} is an abstract implementation for all
 * {@link Function}s that will calculate {@code y} values sequentially in case a
 * list of {@code x} values is given.
 * 
 * @author glass
 *
 */
public abstract class SequentialFunction implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.function.Function#getY(java.util.List)
	 */
	@Override
	public List<Double> getY(List<Double> xs) {
		List<Double> ys = new ArrayList<Double>();
		for (Double x : xs) {
			ys.add(getY(x));
		}
		return ys;
	}

}
