package de.cs12.reliability.function;

import de.cs12.reliability.common.Transformer;

/**
 * The {@code FunctionTransformer} transforms an object {@code T} into a
 * {@code Function}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the object t
 */
public interface FunctionTransformer<T> extends Transformer<T, Function> {

}
