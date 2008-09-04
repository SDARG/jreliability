package de.cs12.reliability.common;

/**
 * The {@code Failure} represents the occurrence of a failure of an object
 * and delivers the time of the failure as the information.
 * 
 * @author glass
 * 
 * @param <T>
 */
public class Failure<T> extends Occurrence<T> {

	/**
	 * Constructs a {@code Failure} with a given object and a time.
	 * 
	 * @param t
	 *            the object
	 * @param time
	 *            the time
	 */
	public Failure(T t, double time) {
		super(t, time);
	}

}
