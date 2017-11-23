package org.jreliability.sl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;

public class SLTest {	

	List<Term> term = new ArrayList<>();
	List<Integer> numberOfOperands = new ArrayList<>();
	SLProbabilityBitstreamConverter<String> converter = new SLProbabilityBitstreamConverter<>();
	
	@Before
	public void init() {				
		// Initialize Components and Term
		String C1 = "Component C1";
		String C2 = "Component C2";
		LiteralTerm<String> C1Literal = new LiteralTerm<>(C1);
		LiteralTerm<String> C2Literal = new LiteralTerm<>(C2);
		
		// Initialize SLProbabilityBitstreamConverter;
		converter.convertToBitstream(C1, 100, 0.1);
		converter.convertToBitstream(C2, 100, 0.2);

		term.add(C1Literal);
		term.add(C2Literal);
	}
	
	@Test
	public void testOperateAND() {
		term.add(new ANDTerm());
		numberOfOperands.add(2);

		SL<String> SL = new SL<>(term, numberOfOperands, converter);		
		SL.operate();
		
		double result = SL.operateAND();
		System.out.println("Result Of Operate AND: " + result);
		
		Assert.assertEquals(0.02, result, 0.05);
	}
	
	@Test
	public void testOperateOR() {
		term.add(new ORTerm());
		numberOfOperands.add(2);

		SL<String> SL = new SL<>(term, numberOfOperands, converter);		
		SL.operate();
		
		double result = SL.operateOR();
		System.out.println("Result Of Operate OR : " + result);
		
		Assert.assertEquals(0.30, result, 0.05);
	}

	@Test
	public void testOperateNOT() {
		// because NOT is an unary operator.
		term.clear();
		numberOfOperands.clear();
		
		String C3 = "Component C3";
		LiteralTerm<String> C3Literal = new LiteralTerm<>(C3);
		converter.convertToBitstream(C3, 100, 0.3);
		
		term.add(C3Literal);
		term.add(new NOTTerm(C3Literal));
		numberOfOperands.add(1);

		SL<String> SL = new SL<>(term, numberOfOperands, converter);		
		SL.operate();
		
		double result = SL.operateNOT();
		System.out.println("Result Of Operate NOT: " + result);
		
		Assert.assertEquals(0.70, result, 0);
	}
	
}
