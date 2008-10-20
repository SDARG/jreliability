package org.jreliability.tutorial.boiler;

/**
 * The abstract {@code BoilerElement} is the basic element that models each of
 * the elements regarded in the model of the {@code Boiler}.
 * 
 * @author glass
 * 
 */
public abstract class BoilerElement {

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

}
