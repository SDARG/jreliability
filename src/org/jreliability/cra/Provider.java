package org.jreliability.cra;

public interface Provider<T> extends Node {
	public T get();
}
