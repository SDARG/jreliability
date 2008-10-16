/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.tester;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.javabdd.JBDDProviderFactory;

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
