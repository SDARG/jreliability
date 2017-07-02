package org.jreliability.booleanfunction.common;

import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.junit.Assert;
import org.junit.Test;

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
