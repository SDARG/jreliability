package org.jreliability.sl;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;
import org.jreliability.function.ReliabilityFunction;
import org.jreliability.function.common.ConstantFailureFunction;
import org.junit.Assert;
import org.junit.Test;

/**
 * The The {@link SLTestTest} tests the {@link SL}.
 * 
 * @author glass, jlee
 *
 */
public class SLTest {

	String C1 = "Component C1";
	String C2 = "Component C2";
	String C3 = "Component C3";
	LiteralTerm<String> C1Literal = new LiteralTerm<>(C1);
	LiteralTerm<String> C2Literal = new LiteralTerm<>(C2);
	LiteralTerm<String> C3Literal = new LiteralTerm<>(C3);

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
		Assert.assertEquals(reliabilityFunction.getY(1.0), 0.25, 0.01);
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
		Assert.assertEquals(reliabilityFunction.getY(1.0), 0.25, 0.01);
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
		Assert.assertEquals(reliabilityFunction.getY(1.0), 0.75, 0.01);
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
		Assert.assertEquals(reliabilityFunction.getY(1.0), 0.25, 0.01);
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
		Assert.assertEquals(reliabilityFunction.getY(1.0), 1.0, 0.01);
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
		Assert.assertEquals(reliabilityFunction.getY(1.0), 0.0, 0.01);
	}

}
