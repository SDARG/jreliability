/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.common;

/**
 * The {@link Occurrence} represents an occurrence or event, e.g., the failure
 * or repair of an object at a given time.
 * 
 * @author glass
 * 
 * @param <T>
 *            the object that is effected by the occurrence
 */
public abstract class Occurrence<T> implements Comparable<Occurrence<T>> {

	/**
	 * The element {@link T}.
	 */
	protected final T t;
	/**
	 * The time of the occurrence.
	 */
	protected final Double time;

	/**
	 * Constructs an {@link Occurrence} with a given object and the time.
	 * 
	 * @param t
	 *            the object
	 * @param time
	 *            the time
	 */
	public Occurrence(T t, double time) {
		this.t = t;
		this.time = time;
	}

	/**
	 * Returns the object.
	 * 
	 * @return the object
	 */
	public T getObject() {
		return t;
	}

	/**
	 * Returns the time.
	 * 
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Occurrence<T> other) {
		return time.compareTo(other.time);
	}

}
