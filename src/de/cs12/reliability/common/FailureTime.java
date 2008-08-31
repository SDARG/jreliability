package de.cs12.reliability.common;

/**
 * The {@code FailureTime} represents the occurrence of a failure of an object
 * and delivers the time of the failure as the information.
 * 
 * @author glass
 * 
 * @param <T>
 */
public class FailureTime<T> extends Occurrence<T, Double> implements
		Comparable<FailureTime<T>> {

	/**
	 * Constructs a {@code FailureTime} with a given object and a time.
	 * 
	 * @param t
	 *            the object
	 * @param i
	 *            the time
	 */
	public FailureTime(T t, Double i) {
		super(t, i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(FailureTime<T> o) {
		return i.compareTo(o.getInformation());
	}

}
