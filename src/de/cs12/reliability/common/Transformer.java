package de.cs12.reliability.common;

/**
 * The {@code Transformer} transforms object {@code A} into object {@code B}.
 * 
 * @author glass
 * 
 * @param <A>
 *            the object a
 * @param <B>
 *            the object b
 */
public interface Transformer<A, B> {

	/**
	 * Returns object {@code B} for a given object {@code A}.
	 * 
	 * @param a
	 *            the object {@code A}
	 * @return object B for a given object A
	 */
	public B transform(A a);

}
