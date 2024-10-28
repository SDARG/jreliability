/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/

package org.jreliability.bdd;

import java.util.LinkedList;
import java.util.List;

import org.jreliability.bdd.BDDConstraint.Literal;
import org.jreliability.bdd.BDDConstraint.Pair;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link BDDConstraintTest} tests the {@link BDDConstraint}.
 * 
 * @author reimann
 *
 */
public class BDDConstraintTest {

	@Test
	public void testGetA() {
		String a = "a";
		String b = "b";
		Pair<String, String> p = new Pair<>(a, b);

		Assertions.assertEquals(a, p.getA());
		Assertions.assertEquals(b, p.getB());
	}

	@Test
	public void testEquals() {
		String a = "a";
		String b = "b";
		String c = "c";
		Pair<String, String> p1 = new Pair<>(a, b);
		Pair<String, String> p2 = new Pair<>(a, b);
		Pair<String, String> p3 = new Pair<>(c, b);
		Pair<String, String> p4 = new Pair<>(a, c);

		Assertions.assertEquals(p1, p2);
		Assertions.assertNotEquals(p1, p3);
		Assertions.assertNotEquals(p1, p4);
	}

	@Test
	public void testEqualsNull() {
		String a = "a";
		String b = "b";
		Pair<String, String> p1 = new Pair<>(null, null);
		Pair<String, String> p2 = new Pair<>(null, null);
		Pair<String, String> p3 = new Pair<>(null, b);
		Pair<String, String> p4 = new Pair<>(a, null);

		Assertions.assertEquals(p1, p2);
		Assertions.assertNotEquals(p1, p3);
		Assertions.assertNotEquals(p1, p4);
		Assertions.assertNotEquals(p1, null);
		Assertions.assertNotEquals(p1, "anything");
	}

	@Test
	public void testHashCode() {
		Pair<String, String> p1 = new Pair<>(null, null);
		Pair<String, String> p2 = new Pair<>(null, null);

		Assertions.assertEquals(p1.hashCode(), p2.hashCode());
	}

	@Test
	public void testEqualsIdent() {
		String a = null;
		String b = null;
		Pair<String, String> p1 = new Pair<>(a, b);
		Assertions.assertEquals(p1, p1);
	}

	@Test
	public void testLiteralToString() {
		JBDDProviderFactory factory = new JBDDProviderFactory();
		BDDProvider<String> provider = factory.getProvider();
		BDD<String> a = provider.get("a");

		Literal<String> l = new Literal<>(1, a);
		Assertions.assertEquals("1*a", l.toString());
	}

	@Test
	public void testCheckAndAdd() {
		JBDDProviderFactory factory = new JBDDProviderFactory();
		BDDProvider<String> provider = factory.getProvider();

		Literal<String> la = new Literal<>(1, provider.get("a"));
		Literal<String> lb = new Literal<>(1, provider.get("b"));
		Literal<String> lc = new Literal<>(1, provider.get("d"));

		List<Literal<String>> lhsOne = new LinkedList<>();
		lhsOne.add(la);
		lhsOne.add(lb);
		lhsOne.add(lc);
		lhsOne.add(lb);
		BDDConstraint<String> constraintOne = new BDDConstraint<>(2, lhsOne);

		Literal<String> lbTwo = new Literal<>(2, provider.get("b"));
		List<Literal<String>> lhsTwo = new LinkedList<>();
		lhsTwo.add(la);
		lhsTwo.add(lbTwo);
		lhsTwo.add(lc);
		BDDConstraint<String> constraintTwo = new BDDConstraint<>(2, lhsTwo);

		Assertions.assertEquals(constraintOne.getLhs().toString(), constraintTwo.getLhs().toString());
	}
}
