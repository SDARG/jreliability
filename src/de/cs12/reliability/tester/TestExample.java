/**
 * 
 */
package de.cs12.reliability.tester;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;
import de.cs12.reliability.javabdd.JBDDProviderFactory;

/**
 * The {@code TestExample} is a play example. It describes a water heating
 * system that consists of a heater and two pumps. To allow a correct
 * functionality, the system needs a working heater and at least one working
 * pump.
 * 
 * @author glass
 * 
 */
public class TestExample {

	/**
	 * The heater.
	 */
	protected final String heater = "Heater";
	/**
	 * The first pump.
	 */
	protected final String pump1 = "Pump 1";
	/**
	 * The second pump.
	 */
	protected final String pump2 = "Pump 2";

	/**
	 * Constructs a {@code TextExample}.
	 */
	public TestExample() {
		super();
	}

	/**
	 * Returns a {@code BDD} representation of the system structure.
	 * 
	 * @return a bdd representing the system structure
	 */
	public BDD<String> get() {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider();

		BDD<String> heaterBDD = bddProvider.get(heater);
		BDD<String> pump1BDD = bddProvider.get(pump1);
		BDD<String> pump2BDD = bddProvider.get(pump2);

		BDD<String> pumpSystem = bddProvider.zero();
		pumpSystem.orWith(pump1BDD);
		pumpSystem.orWith(pump2BDD);

		BDD<String> system = bddProvider.one();
		system.andWith(heaterBDD);
		system.andWith(pumpSystem);

		/*
		 * This little change can be used to show the BDDs.toDot() method.
		 * 
		 * BDD<String> a = heaterBDD.and(pump1BDD); BDD<String> aNot =
		 * heaterBDD.not().and(pump2BDD);
		 * 
		 * BDD<String> system = a.or(aNot);
		 */

		return system;

	}

}
