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
package org.jreliability.booleanfunction;

import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link TermsTest} test the {@link Terms}.
 * 
 * @author reimann
 *
 */
public class TermsTest {

	@Test
	public void testParseAndLiteral() {
		String s = "(AND \"sensor1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(t.toString(), s);
	}

	@Test
	public void testParseOrLiteral() {
		String s = "(OR \"sensor1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(t.toString(), s);
	}

	@Test
	public void testParseNotLiteral() {
		String s = "(AND \"sensor1\" (NOT \"sensor2\"))";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(t.toString(), s);
	}

	@Test(expected = RuntimeException.class)
	public void testParseIllegalNotLiteral() {
		String s = "(NOT \"sensor1\" \"sensor2\")";
		Terms.getTermFromString(s);
	}

	@Test(expected = RuntimeException.class)
	public void testParseIllegalTerm() {
		String s = "(= 1 \"sensor1\")";
		Terms.getTermFromString(s);
	}
}
