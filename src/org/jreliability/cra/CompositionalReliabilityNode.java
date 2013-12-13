package org.jreliability.cra;

public interface CompositionalReliabilityNode<Output> extends Node {
	public void requires();

	public void provides();

	public void set(Object input);

	public Output get();
}
