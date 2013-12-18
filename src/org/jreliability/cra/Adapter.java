package org.jreliability.cra;

/**
 * The {@link Adapter} takes exactly one input model of type {@code I} and
 * transforms it to an output model of type {@code O}.
 * 
 * @author reimann
 * 
 * @param <I>
 *            input type
 * @param <O>
 *            output type
 */
public interface Adapter<I, O> extends Node {
	/**
	 * Transform the input to the output.
	 * 
	 * @param i
	 * @return
	 */
	public O transform(I i);
}
