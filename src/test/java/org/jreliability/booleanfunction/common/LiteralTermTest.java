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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link LiteralTermTest} test the {@link LinearTerm}.
 * 
 * @author reimann
 *
 */
public class LiteralTermTest {
	@Test
	public void testEquals() {
		String var = "1";
		LiteralTerm<String> s1 = new LiteralTerm<>(var);
		LiteralTerm<String> s2 = new LiteralTerm<>("2");
		LiteralTerm<String> s3 = new LiteralTerm<>(var);
		LiteralTerm<String> s4 = new LiteralTerm<>(null);
		LiteralTerm<String> s5 = new LiteralTerm<>(null);

		LiteralTerm<String> s6 = null;
		String s7 = "something";

		Assertions.assertTrue(s1.equals(s1));
		Assertions.assertTrue(s1.equals(s3));
		Assertions.assertTrue(s4.equals(s5));
		Assertions.assertFalse(s1.equals(s2));
		Assertions.assertFalse(s1.equals(s6));
		Assertions.assertFalse(s1.equals(s7));
		Assertions.assertFalse(s4.equals(s1));
	}

	@Test
	public void testHashCode() {
		String var = "1";
		LiteralTerm<String> s1 = new LiteralTerm<>(var);
		LiteralTerm<String> s2 = new LiteralTerm<>(s1.get());

		Assertions.assertEquals(s1.hashCode(), s2.hashCode());
	}

	@Test
	public void testHashCodeNull() {
		LiteralTerm<String> s1 = new LiteralTerm<>(null);
		LiteralTerm<String> s2 = new LiteralTerm<>(s1.get());

		Assertions.assertEquals(s1.hashCode(), s2.hashCode());
	}
}
