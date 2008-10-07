/**
 * 
 */
package de.cs12.reliability.tester;

import java.util.HashMap;
import java.util.Map;

import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;
import de.cs12.reliability.distribution.Distribution;
import de.cs12.reliability.distribution.ExponentialDistribution;
import de.cs12.reliability.distribution.WeibullDistribution;
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

	protected final String heater = "Heater";
	protected final String pump1 = "Pump 1";
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
		BDDProvider<String> bddProvider = bddProviderFactory.getProvider(3);

		BDD<String> heaterBDD = bddProvider.get(heater);
		BDD<String> pump1BDD = bddProvider.get(pump1);
		BDD<String> pump2BDD = bddProvider.get(pump2);

		BDD<String> pumpSystem = bddProvider.zero();
		pumpSystem.orWith(pump1BDD);
		pumpSystem.orWith(pump2BDD);

		BDD<String> system = bddProvider.one();
		system.andWith(heaterBDD);
		system.andWith(pumpSystem);

		return system;

	}

	/**
	 * Returns the {@code Distribution} of each component of the system.
	 * 
	 * @return the distribution of each component of the system
	 */
	public Map<String, Distribution> getDistributions() {
		Map<String, Distribution> distributions = new HashMap<String, Distribution>();
		Distribution heaterDistribution = new ExponentialDistribution(0.1);
		Distribution pump1Distribution = new WeibullDistribution(3, 5);
		Distribution pump2Distribution = new WeibullDistribution(1, 3);

		distributions.put(heater, heaterDistribution);
		distributions.put(pump1, pump1Distribution);
		distributions.put(pump2, pump2Distribution);

		return distributions;
	}

}
