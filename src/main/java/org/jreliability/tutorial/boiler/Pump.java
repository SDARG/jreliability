package org.jreliability.tutorial.boiler;

/**
 * The {@link Pump} models a pump that is used to pump water from the {@link
 * Boiler} to its destination.
 * 
 * @author glass
 * 
 */
public class Pump extends BoilerComponent {

	/**
	 * Constructs a {@link Pump} with a given name.
	 * 
	 * @param name
	 *            the name of the pump
	 */
	public Pump(String name) {
		super(name);
	}

}
