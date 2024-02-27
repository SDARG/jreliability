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

package org.jreliability.booleanfunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link TermsTest} test the {@link TermUtils}.
 * 
 * @author reimann, glass
 *
 */
public class TermsTest {

	@Test
	public void testIllagelConstructor() {
		final Class<?> termsClass = TermUtils.class;
		final Constructor<?> termsConstructor = termsClass.getDeclaredConstructors()[0];
		termsConstructor.setAccessible(true);
		Throwable targetException = null;
		try {
			termsConstructor.newInstance((Object[]) null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			targetException = e;
		} catch (InvocationTargetException e) {
			targetException = ((InvocationTargetException) e).getTargetException();
		}

		Assertions.assertNotNull(targetException);
		Assertions.assertEquals(InstantiationException.class, targetException.getClass());
	}

	@Test
	public void testParseAndLiteral() {
		String s = "(AND \"sensor1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseOrLiteral() {
		String s = "(OR \"sensor1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseNotLiteral() {
		String s = "(AND \"sensor1\" (NOT \"sensor2\"))";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseIllegalNotLiteral() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String s = "(NOT \"sensor1\" \"sensor2\")";
			TermUtils.getTermFromString(s);
		});
	}

	@Test
	public void testParseIllegalTerm() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String s = "(= 1 \"sensor1\")";
			TermUtils.getTermFromString(s);
		});
	}

	@Test
	public void testParseMissingBracket() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String s = "(AND \"sensor1\" \"sensor2\"";
			TermUtils.getTermFromString(s);
		});
	}

	@Test
	public void testParseWithNewline() {
		Assertions.assertDoesNotThrow(() -> {
			String s = "(AND\n\"sensor1\" \"sensor2\")";
			TermUtils.getTermFromString(s);
		});
	}

	@Test
	public void testParseIllegalEndAfterOperator() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String s = "(AND";
			TermUtils.getTermFromString(s);
		});
	}

	@Test
	public void testParseMissingQuote() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String s = "(AND \"sensor1\" \"sensor2)";
			TermUtils.getTermFromString(s);
		});
	}

	@Test
	public void testGetVariables() {
		String s = "(AND \"sensor1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Set<String> vars = TermUtils.getVariables(t);

		Assertions.assertEquals(2, vars.size());
		Assertions.assertTrue(vars.contains("sensor1"));
		Assertions.assertTrue(vars.contains("sensor2"));
	}

	@Test
	public void testGetVariablesTRUETerm() {
		Term t = new TRUETerm();
		Assertions.assertTrue(TermUtils.getVariables(t).isEmpty());
	}

	@Test
	public void testGetVariablesLIteralTerm() {
		Term t = new LiteralTerm<String>("sensor");
		Assertions.assertTrue(TermUtils.getVariables(t).contains("sensor"));
	}

	@Test
	public void testParseTermEqual() {
		String s = "(= \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermGT() {
		String s = "(>= \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermLT() {
		String s = "(<= \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermG() {
		String s = "(> \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermL() {
		String s = "(< \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
		Term t = TermUtils.getTermFromString(s);
		Assertions.assertEquals(s, t.toString());
	}

	@Test
	public void testParseTermUnknown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			String s = "(? \"1\" \"1\" \"sensor1\" \"1\" \"sensor2\")";
			Term t = TermUtils.getTermFromString(s);
			Assertions.assertEquals(s, t.toString());
		});
	}
}
