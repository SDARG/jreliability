package org.jreliability.tutorial.boiler;

/**
 * The {@code BoilerElement} is the abstract element that models each of the
 * boiler element.
 * 
 * @author glass
 * 
 */
public abstract class BoilerElement implements Comparable<BoilerElement> {

	/**
	 * The name of the element.
	 */
	protected final String name;

	/**
	 * Constructs a {@code BoilerElement} with a given name.
	 * 
	 * @param name
	 *            the name of the element
	 */
	public BoilerElement(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the element.
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BoilerElement element) {
		int comp = name.compareTo(element.getName());
		return comp;
	}

}
