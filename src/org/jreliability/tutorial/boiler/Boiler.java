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
 * {@code ((Sensor1 OR Sensor2) AND Controller) AND (Controller AND Heater) AND
 * (Controller AND (Pump1 OR Pump2))}
 * 
 * @author glass
 * 
 */
public class Boiler {

	protected Sensor sensor1 = new Sensor("Sensor1");
	protected Sensor sensor2 = new Sensor("Sensor2");
	protected Controller controller = new Controller("Controller");
	protected Heater heater = new Heater("Heater");
	protected Pump pump1 = new Pump("Pump1");
	protected Pump pump2 = new Pump("Pump2");

	protected List<BoilerElement> elements = new ArrayList<BoilerElement>();
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
	 * Initializes the list of elements of the {@code Boiler}.
	 */
	private void initialize() {
		elements.add(sensor1);
		elements.add(sensor2);
		elements.add(controller);
		elements.add(heater);
		elements.add(pump1);
		elements.add(pump2);
	}

	/**
	 * Returns a model of the {@code Boiler} as a {@code BDD}.
	 * 
	 * @return the bdd representation of the boiler
	 */
	public BDD<BoilerElement> get() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<BoilerElement> bddProvider = bddProviderFactory
				.getProvider();

		BDD<BoilerElement> sensor1BDD = bddProvider.get(sensor1);
		BDD<BoilerElement> sensor2BDD = bddProvider.get(sensor2);
		BDD<BoilerElement> controllerBDD = bddProvider.get(controller);
		BDD<BoilerElement> heaterBDD = bddProvider.get(heater);
		BDD<BoilerElement> pump1BDD = bddProvider.get(pump1);
		BDD<BoilerElement> pump2BDD = bddProvider.get(pump2);

		BDD<BoilerElement> sensorSubSystem = sensor1BDD.and(sensor2BDD);
		BDD<BoilerElement> senControlSubSystem = sensorSubSystem
				.and(controllerBDD);

		BDD<BoilerElement> heatingSubSystem = heaterBDD.and(controllerBDD);

		BDD<BoilerElement> pumpSubSystem = pump1BDD.or(pump2BDD);
		BDD<BoilerElement> pumpControlSubSystem = pumpSubSystem
				.and(controllerBDD);

		BDD<BoilerElement> boilerBDD = senControlSubSystem
				.and(heatingSubSystem);
		// Important: With-operators consume (destroy!) the BDD that is the
		// argument i.e. the pumpControlSubSystem is destroyed after this
		// operation, while
		// BDD<BoilerElement> boilerBDD = boilerBDD.and(pumpControlSubSystem);
		// would not destroy anything
		boilerBDD.andWith(pumpControlSubSystem);

		return boilerBDD;
	}

	/**
	 * Returns the elements of the {@code Boiler}.
	 * 
	 * @return the elements
	 */
	public List<BoilerElement> getElements() {
		return elements;
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
