/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd;

import org.jreliability.bdd.BDDConstraint.Literal;
import org.jreliability.bdd.BDDConstraint.Pair;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@code BDDConstraintTest} tests the {@code BDDConstraint}.
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

		Assert.assertEquals(a, p.getA());
		Assert.assertEquals(b, p.getB());
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

		Assert.assertEquals(p1, p2);
		Assert.assertNotEquals(p1, p3);
		Assert.assertNotEquals(p1, p4);
	}

	@Test
	public void testEqualsNull() {
		String a = "a";
		String b = "b";
		Pair<String, String> p1 = new Pair<>(null, null);
		Pair<String, String> p2 = new Pair<>(null, null);
		Pair<String, String> p3 = new Pair<>(null, b);
		Pair<String, String> p4 = new Pair<>(a, null);

		Assert.assertEquals(p1, p2);
		Assert.assertNotEquals(p1, p3);
		Assert.assertNotEquals(p1, p4);
		Assert.assertNotEquals(p1, null);
		Assert.assertNotEquals(p1, "anything");
	}

	@Test
	public void testHashCode() {
		Pair<String, String> p1 = new Pair<>(null, null);
		Pair<String, String> p2 = new Pair<>(null, null);

		Assert.assertEquals(p1.hashCode(), p2.hashCode());
	}

	@Test
	public void testEqualsIdent() {
		String a = null;
		String b = null;
		Pair<String, String> p1 = new Pair<>(a, b);
		Assert.assertEquals(p1, p1);
	}

	@Test
	public void testLiteralToString() {
		JBDDProviderFactory factory = new JBDDProviderFactory(Type.JAVABDD);
		BDDProvider<String> provider = factory.getProvider();
		BDD<String> a = provider.get("a");

		Literal<String> l = new Literal<>(1, a);
		Assert.assertEquals("1*a", l.toString());
	}
}
