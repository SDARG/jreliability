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

import java.util.ArrayList;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.BDDs;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;

/**
 * The {@code ConstraintTester} can be used to test the {@code
 * BDDs.getConstraintBDD()} function.
 * 
 * @author glass
 * 
 */
public class ConstraintTester {

	/**
	 * Constructs a {@code ConstraintTester}.
	 * 
	 */
	public ConstraintTester() {
		super();
	}

	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<Object> bddProvider = bddProviderFactory.getProvider();

		Term x = new LiteralTerm("x");
		Term y = new LiteralTerm("y");
		Term z = new LiteralTerm("z");
		Term a = new LiteralTerm("a");
		Term b = new LiteralTerm("b");
		Term c = new LiteralTerm("c");
		Term d = new LiteralTerm("d");
		Term e = new LiteralTerm("e");
		Term f = new LiteralTerm("f");

		LinearTerm term = new LinearTerm(new ArrayList<Integer>(), new ArrayList<Term>(), LinearTerm.Comparator.LESSEQUAL, 8);
		term.add(1, x);
		term.add(1, y);
		term.add(2, z);
		term.add(2, a);
		term.add(3, b);
		term.add(3, c);
		term.add(3, d);
		term.add(3, e);
		term.add(7, f);		
		
		BDDTTRF transformer = new BDDTTRF(bddProvider);
		BDD<Object> bdd = transformer.convertToBDD(term);

		System.out.println(BDDs.toDot(bdd));
	}
}
