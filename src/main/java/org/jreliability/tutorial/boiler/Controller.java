package org.jreliability.tutorial.boiler;

/**
 * The {@link Controller} models a controlling component that is used for
 * activating the pumps and controlling the water temperature.
 * 
 * @author glass
 * 
 */
public class Controller extends BoilerComponent {

	/**
	 * Constructs a {@link Controller} with a given name.
	 * 
	 * @param name
	 *            the name of the controller
	 */
	public Controller(String name) {
		super(name);
	}

}
