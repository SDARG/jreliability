package org.jreliability.tutorial.boiler;

/**
 * The {@code Sensor} models a sensor component in the boiler that measures and
 * collects the data of the water temperature.
 * 
 * @author glass
 * 
 */
public class Sensor extends BoilerComponent {

	/**
	 * Constructs a {@code Sensor} with a given name.
	 * 
	 * @param name
	 *            the name of the sensor
	 */
	public Sensor(String name) {
		super(name);
	}

}
