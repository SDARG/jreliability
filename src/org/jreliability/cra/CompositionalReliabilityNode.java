package org.jreliability.cra;

public interface CompositionalReliabilityNode<Model, Output> extends Node {
	public Output convert(Model model);
}
