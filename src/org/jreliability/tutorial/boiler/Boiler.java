package org.jreliability.tutorial.boiler;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.javabdd.JBDDProviderFactory;

/**
 * The {@code Boiler} models a boiler that is responsible for keeping the water
 * in a tank at the desired temperature and pumping it to a destination if
 * needed.
 * <p>
 * The {@code Boiler} consists of two {@code Sensors} that measure the water
 * temperature, a {@code Controller} that activates and deactivates a {@code
 * Heater} to control the water temperature as well as it activates and
 * deactivates one of two available {@code Pumps} to pump the water to its
 * destination if needed.
 * <p>
 * The non-minimized boolean function that describes whether this system works
 * correctly (evaluates to {@code 1}) or fails (evaluates to {@code 0}) is as
 * follows:
 * <p>
 * {@code ((Sensor1 AND Sensor2) AND Controller) AND (Controller AND Heater) AND
 * (Controller AND (Pump1 OR Pump2))}
 * 
 * @author glass
 * 
 */
public class Boiler {

	/**
	 * The first temperature sensor.
	 */
	protected Sensor sensor1 = new Sensor("Sensor1");
	/**
	 * The second temperature sensor.
	 */
	protected Sensor sensor2 = new Sensor("Sensor2");
	/**
	 * The controller.
	 */
	protected Controller controller = new Controller("Controller");
	/**
	 * The water heater.
	 */
	protected Heater heater = new Heater("Heater");
	/**
	 * The first pump.
	 */
	protected Pump pump1 = new Pump("Pump1");
	/**
	 * The second pump.
	 */
	protected Pump pump2 = new Pump("Pump2");

	/**
	 * All components that are included in the {@code Boiler}.
	 */
	protected List<BoilerComponent> components = new ArrayList<BoilerComponent>();
	/**
	 * The used {@code FunctionTransformer}.
	 */
	protected BoilerTransformer transformer;

	/**
	 * Constructs a {@code Boiler}.
	 * 
	 */
	public Boiler() {
		super();
		initialize();
		transformer = new BoilerTransformer(this);
	}

	/**
	 * Initializes the list of components of the {@code Boiler}.
	 */
	private void initialize() {
		components.add(sensor1);
		components.add(sensor2);
		components.add(controller);
		components.add(heater);
		components.add(pump1);
		components.add(pump2);
	}

	/**
	 * Returns a model of the {@code Boiler} as a {@code BDD}.
	 * 
	 * @return the bdd representation of the boiler
	 */
	public BDD<BoilerComponent> get() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<BoilerComponent> bddProvider = bddProviderFactory
				.getProvider();

		BDD<BoilerComponent> sensor1BDD = bddProvider.get(sensor1);
		BDD<BoilerComponent> sensor2BDD = bddProvider.get(sensor2);
		BDD<BoilerComponent> controllerBDD = bddProvider.get(controller);
		BDD<BoilerComponent> heaterBDD = bddProvider.get(heater);
		BDD<BoilerComponent> pump1BDD = bddProvider.get(pump1);
		BDD<BoilerComponent> pump2BDD = bddProvider.get(pump2);

		BDD<BoilerComponent> sensorSubSystem = sensor1BDD.and(sensor2BDD);
		BDD<BoilerComponent> senControlSubSystem = sensorSubSystem
				.and(controllerBDD);

		BDD<BoilerComponent> heatingSubSystem = heaterBDD.and(controllerBDD);

		BDD<BoilerComponent> pumpSubSystem = pump1BDD.or(pump2BDD);
		BDD<BoilerComponent> pumpControlSubSystem = pumpSubSystem
				.and(controllerBDD);

		BDD<BoilerComponent> boilerBDD = senControlSubSystem
				.and(heatingSubSystem);
		// Important: With-operators consume (destroy!) the BDD that is the
		// argument i.e. the pumpControlSubSystem is destroyed after this
		// operation, while
		// BDD<BoilerComponent> boilerBDD = boilerBDD.and(pumpControlSubSystem);
		// would not destroy anything
		boilerBDD.andWith(pumpControlSubSystem);

		return boilerBDD;
	}

	/**
	 * Returns the components of the {@code Boiler}.
	 * 
	 * @return the components
	 */
	public List<BoilerComponent> getComponents() {
		return components;
	}

	/**
	 * Returns the {@code BoilerTransformer}.
	 * 
	 * @return the transformer
	 */
	public BoilerTransformer getTransformer() {
		return transformer;
	}

}
