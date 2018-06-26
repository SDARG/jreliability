/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/
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
