package de.cs12.reliability.common;

/**
 * The {@code Occurrence} represents an occurrence or event, e.g., the failure
 * or repair of an event and an information.
 * 
 * @author glass
 * 
 * @param <T>
 *            the object that is effected by the occurrrence
 * @param <I>
 *            the information needed for the occurrence
 */
public abstract class Occurrence<T, I> {

	protected final T t;
	protected final I i;

	/**
	 * Constructs an {@code Occurrence} with a given object and the information
	 * needed.
	 * 
	 * @param t
	 *            the object
	 * @param i
	 *            the information
	 */
	public Occurrence(T t, I i) {
		this.t = t;
		this.i = i;
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
	 * Returns the information.
	 * 
	 * @return the information
	 */
	public I getInformation() {
		return i;
	}

}
