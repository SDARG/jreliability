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
package org.jreliability.booleanfunction.common;

import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link LinearTermTest} test the {@link LinearTerm}.
 * 
 * @author reimann
 *
 */
public class LinearTermTest {
	@Test
	public void testAdd() {
		Term literal = new LiteralTerm<>("a");
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);

		t1.add(literal);

		Assert.assertEquals(t1.getCoefficients().size(), 1);
		Assert.assertTrue(t1.getCoefficients().contains(1));
		Assert.assertEquals(t1.getTerms().size(), 1);
		Assert.assertTrue(t1.getTerms().contains(literal));
	}

	@Test
	public void testGetRhs() {
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);
		Assert.assertEquals(t1.getRHS(), 1);
	}

	@Test
	public void testGetComparator() {
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);
		Assert.assertEquals(t1.getComparator(), Comparator.EQUAL);
	}

	@Test
	public void testToString() {
		Term literal = new LiteralTerm<>("a");
		LinearTerm t1 = new LinearTerm(Comparator.EQUAL, 1);

		t1.add(literal);

		Assert.assertEquals(t1.toString(), "(= \"1\" \"1\" \"a\")");
	}
}
