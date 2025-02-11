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
package org.jreliability.sl;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LinearTerm;
import org.jreliability.booleanfunction.common.LinearTerm.Comparator;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ConstantFailureFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link SLTestTest} tests the {@link SL}.
 * 
 * @author glass, jlee
 *
 */
public class SLTest {

	protected String C1 = "Component C1";
	protected String C2 = "Component C2";
	protected String C3 = "Component C3";
	protected LiteralTerm<String> C1Literal = new LiteralTerm<>(C1);
	protected LiteralTerm<String> C2Literal = new LiteralTerm<>(C2);
	protected LiteralTerm<String> C3Literal = new LiteralTerm<>(C3);

	@Test
	public void testConstructor() {
		ANDTerm term = new ANDTerm();
		term.add(C1Literal);
		term.add(C2Literal);

		SL<String> SL = new SL<>(term); // Assume standard bitstreamLength is
										// set to suitable value (10k)
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 0.25, 0.01);
	}

	@Test
	public void testOperateAND() {
		ANDTerm term = new ANDTerm();
		term.add(C1Literal);
		term.add(C2Literal);

		SL<String> SL = new SL<>(term, 100000);
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 0.25, 0.01);
	}

	@Test
	public void testOperateOR() {
		ORTerm term = new ORTerm();
		term.add(C1Literal);
		term.add(C2Literal);

		SL<String> SL = new SL<>(term, 100000);
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 0.75, 0.01);
	}

	@Test
	public void testOperateNOT() {
		ORTerm orTermR = new ORTerm();
		orTermR.add(C1Literal);
		orTermR.add(C2Literal);
		NOTTerm term = new NOTTerm(orTermR);

		SL<String> SL = new SL<>(term, 100000);
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 0.25, 0.01);
	}

	@Test
	public void testOperateTrue() {
		TRUETerm term = new TRUETerm();

		SL<String> SL = new SL<>(term, 100000);
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 1.0, 0.01);
	}

	@Test
	public void testOperateFalse() {
		FALSETerm term = new FALSETerm();

		SL<String> SL = new SL<>(term, 100000);
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 0.0, 0.01);
	}

	@Test
	public void testMultipleOccurrence() {
		ANDTerm innerTerm = new ANDTerm();
		innerTerm.add(C1Literal);
		innerTerm.add(C1Literal);
		ANDTerm term = new ANDTerm();
		term.add(innerTerm);
		term.add(C1Literal);
		// This term must simply be equal to C1LiteralTerm!

		SL<String> SL = new SL<>(term, 100000);
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ConstantFailureFunction(0.5);
					}
				});
		Assertions.assertEquals(reliabilityFunction.getY(1.0), 0.5, 0.01);
	}

	@Test
	public void testUnsupportedTerms() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			LinearTerm term = new LinearTerm(Comparator.EQUAL, 1);
			term.add(C1Literal);
			term.add(C2Literal);

			SL<String> SL = new SL<>(term, 100000);
			SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
					new Transformer<String, ReliabilityFunction>() {
						@Override
						public ReliabilityFunction transform(String input) {
							return new ConstantFailureFunction(0.5);
						}
					});

			reliabilityFunction.getY(1.0);
		});
	}

}
