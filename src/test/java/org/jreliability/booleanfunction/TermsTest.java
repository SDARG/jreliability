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

import java.util.Set;

import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
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
		Assert.assertEquals(s, t.toString());
	}

	@Test
	public void testParseOrLiteral() {
		String s = "(OR \"sensor1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test
	public void testParseNotLiteral() {
		String s = "(AND \"sensor1\" (NOT \"sensor2\"))";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalNotLiteral() {
		String s = "(NOT \"sensor1\" \"sensor2\")";
		Terms.getTermFromString(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalTerm() {
		String s = "(= 1 \"sensor1\")";
		Terms.getTermFromString(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseMissingBracket() {
		String s = "(AND \"sensor1\" \"sensor2\"";
		Terms.getTermFromString(s);
	}

	@Test
	public void testParseWithNewline() {
		String s = "(AND\n\"sensor1\" \"sensor2\")";
		Terms.getTermFromString(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseIllegalEndAfterOperator() {
		String s = "(AND";
		Terms.getTermFromString(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseMissingQuote() {
		String s = "(AND \"sensor1\" \"sensor2)";
		Terms.getTermFromString(s);
	}

	@Test
	public void testGetVariables() {
		String s = "(AND \"sensor1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Set<String> vars = Terms.getVariables(t);

		Assert.assertEquals(2, vars.size());
		Assert.assertTrue(vars.contains("sensor1"));
		Assert.assertTrue(vars.contains("sensor2"));
	}

	@Test
	public void testGetVariablesTRUETerm() {
		Term t = new TRUETerm();
		Assert.assertTrue(Terms.getVariables(t).isEmpty());
	}

	@Test
	public void testGetVariablesLIteralTerm() {
		Term t = new LiteralTerm<String>("sensor");
		Assert.assertTrue(Terms.getVariables(t).contains("sensor"));
	}

	@Test
	public void testParseTermEqual() {
		String s = "(= \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermGT() {
		String s = "(>= \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermLT() {
		String s = "(<= \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermG() {
		String s = "(> \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermL() {
		String s = "(< \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseTermUnknown() {
		String s = "(? \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = Terms.getTermFromString(s);
		Assert.assertEquals(s, t.toString());
	}
}
