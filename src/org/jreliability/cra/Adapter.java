package org.jreliability.cra;

public interface Adapter<I, O> extends Node {
	public O transform(I i);
}
