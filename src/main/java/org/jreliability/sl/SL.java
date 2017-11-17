package org.jreliability.sl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.NOTTerm;

public class SL<T> {
	
	protected List<Term> term = new ArrayList<>();
	protected List<Integer> numberOfOperands = new ArrayList<>();	
	protected SLProbabilityBitstreamConverter<T> converter;
	
	protected Stack<SLProbabilityBitstream> operands = new Stack<>();
		
	public SL(List<Term> term, List<Integer> numberOfOperands, SLProbabilityBitstreamConverter<T> converter) {
		this.term = term;
		this.numberOfOperands = numberOfOperands;
		this.converter = converter;
	}
	
	public SLProbabilityBitstream operate() {	
		// To rearrange the oder of elements in List 'numberOfOperands'
		numberOfOperands.add(numberOfOperands.remove(0));
		
		for (Term element : term) {
			if (element instanceof LiteralTerm) {
				LiteralTerm<T> component = (LiteralTerm<T>) element;
				operands.add(converter.bitstreams.get(component.get()));
			} else if (element instanceof ANDTerm) {
				operateAND();
			} else if (element instanceof ORTerm) {
				operateOR();
			} else if (element instanceof NOTTerm) {
				operateNOT();
			}
			System.out.println("In Stack: " + operands);
		}
				
		// The last element of Stack 'operands' is the result value.
		return operands.pop();
	}
	
	public void operateAND() {
		// Operate AND
		List<SLProbabilityBitstream> operandsAND = new ArrayList<>();
		int index = numberOfOperands.remove(0);

		for (int i = 0; i < index; i++) {
			operandsAND.add(operands.pop());
		}
		
		SLProbabilityBitstream resultOfOperateAND = new SLProbabilityBitstream(operandsAND.get(0).arrayLength);
		for (int i = 0; i < resultOfOperateAND.probabilityArray.length; i++) {
			int checkBit = 0;
			for (SLProbabilityBitstream element : operandsAND) {
				checkBit += element.probabilityArray[i];
			}
			
			resultOfOperateAND.probabilityArray[i] 
					= (checkBit == operandsAND.size()) ? 1 : 0;
		}
				
		operands.push(resultOfOperateAND);
	}
	
	public void operateOR() {
		// Operate OR
		List<SLProbabilityBitstream> operandsOR = new ArrayList<>();
		int index = numberOfOperands.remove(0);
	
		for (int i = 0; i < index; i++) {
			operandsOR.add(operands.pop());
		}
	
		SLProbabilityBitstream resultOfOperateOR = new SLProbabilityBitstream(operandsOR.get(0).arrayLength);
		for (int i = 0; i < resultOfOperateOR.probabilityArray.length; i++) {
			int checkBit = 0;
			for (SLProbabilityBitstream element : operandsOR) {
				checkBit += element.probabilityArray[i];
			}
			
			resultOfOperateOR.probabilityArray[i] 
					= (checkBit == 0) ? 0 : 1;
		}
		
		operands.push(resultOfOperateOR);
	}
	
	public void operateNOT() {
		// Operate NOT
		SLProbabilityBitstream operandsNOT = operands.pop(); // because NOT is an unary operator.
		SLProbabilityBitstream resultOfOperateNOT = new SLProbabilityBitstream(operandsNOT.arrayLength);
		for (int i = 0; i < resultOfOperateNOT.probabilityArray.length; i++) {
			if (operandsNOT.probabilityArray[i] == 0) {
				resultOfOperateNOT.probabilityArray[i] = 1;
			} else if (operandsNOT.probabilityArray[i] == 1) {
				resultOfOperateNOT.probabilityArray[i] = 0;
			}
		}
		
		operands.push(resultOfOperateNOT);
	}
	
}