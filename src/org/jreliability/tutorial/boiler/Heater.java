package org.jreliability.tutorial.boiler;

/**
 * The {@code Heater} models an actor element of the boiler that is responsible
 * for increasing the water temperature.
 * 
 * @author glass
 * 
 */
public class Heater extends BoilerElement {

	/**
	 * Constructs a {@code Heater} with a given name.
	 * 
	 * @param name
	 *            the name of the heater
	 */
	public Heater(String name) {
		super(name);
	}

}
