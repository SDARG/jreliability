package org.jreliability.tutorial.boiler;

/**
 * The abstract {@link BoilerComponent} is the basic class that models each of
 * the components used in the model of the {@link Boiler}.
 * 
 * @author glass
 * 
 */
public abstract class BoilerComponent {

	/**
	 * The name of the component.
	 */
	protected final String name;

	/**
	 * Constructs a {@link BoilerComponent} with a given name.
	 * 
	 * @param name
	 *            the name of the component
	 */
	public BoilerComponent(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the component.
	 * 
	 * @return the name of the component
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
