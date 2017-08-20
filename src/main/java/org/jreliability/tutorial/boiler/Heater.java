package org.jreliability.tutorial.boiler;

/**
 * The {@link Heater} models an actor component of the boiler that is
 * responsible for increasing the water temperature.
 * 
 * @author glass
 * 
 */
public class Heater extends BoilerComponent {

	/**
	 * Constructs a {@link Heater} with a given name.
	 * 
	 * @param name
	 *            the name of the heater
	 */
	public Heater(String name) {
		super(name);
	}

}
