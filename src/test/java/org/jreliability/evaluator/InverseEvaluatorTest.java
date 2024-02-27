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

package org.jreliability.evaluator;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ExponentialReliabilityFunction;
import org.jreliability.sl.SL;
import org.jreliability.sl.SLReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link InverseEvaluatorTest} to test the {@link InverseEvaluator}.
 * 
 * @author glass
 *
 */
public class InverseEvaluatorTest {

	@Test
	public void testEvaluate() {
		ExponentialReliabilityFunction f = new ExponentialReliabilityFunction(0.005);
		InverseEvaluator evaluator = new InverseEvaluator();
		Assertions.assertEquals(evaluator.evaluate(f, 0.876), 26.4778, 1.0E-4);
	}

	/**
	 * If the analysis is not sufficiently accurate, bisection may run into an
	 * endless loop if accurracy is set too high. The evaluator should take care of
	 * this with the next reasonable value.
	 */
	@Test
	public void testEvaluateNoImprovement() {
		String C1 = "Component C1";
		LiteralTerm<String> C1Literal = new LiteralTerm<>(C1);
		ANDTerm term = new ANDTerm();
		term.add(C1Literal);

		SL<String> SL = new SL<>(term, 1000); // use low number of bits for low
												// accurracy
		SLReliabilityFunction<String> reliabilityFunction = new SLReliabilityFunction<>(SL,
				new Transformer<String, ReliabilityFunction>() {
					@Override
					public ReliabilityFunction transform(String input) {
						return new ExponentialReliabilityFunction(0.1);
					}
				});
		InverseEvaluator evaluator = new InverseEvaluator();
		Assertions.assertEquals(evaluator.evaluate(reliabilityFunction, 0.905), 1.0, 0.1);
	}

}
