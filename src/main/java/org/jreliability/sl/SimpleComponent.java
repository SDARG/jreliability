package org.jreliability.sl;

public class SimpleComponent {
	
	protected final String name;
	
	public SimpleComponent(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
}
