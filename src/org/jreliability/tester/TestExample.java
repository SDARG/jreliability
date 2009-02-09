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

import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ORTerm;

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
	 * Returns the {@code Term} representation of the {@code TestExample}.
	 * 
	 * @return the term representation of the test example
	 */
	public Term get() {
		LiteralTerm<String> heaterLiteral = new LiteralTerm<String>(true, heater);
		LiteralTerm<String> pump1Literal = new LiteralTerm<String>(true, pump1);
		LiteralTerm<String> pump2Literal = new LiteralTerm<String>(true, pump2);

		ORTerm orTerm = new ORTerm();
		orTerm.add(pump1Literal);
		orTerm.add(pump2Literal);

		ANDTerm andTerm = new ANDTerm();
		andTerm.add(orTerm);
		andTerm.add(heaterLiteral);

		return andTerm;
	}

}
