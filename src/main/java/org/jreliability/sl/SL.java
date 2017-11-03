package org.jreliability.sl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.ORTerm;

public class SL<T> {
	
	protected List<Term> term = new ArrayList<>();
	protected List<Integer> numberOfOperands = new ArrayList<>();	
	protected SLProbabilityBitstreamConverter<T> converter;
	
	protected Stack<SLProbabilityBitstream> operands = new Stack<>();
	
	// for checking...
	protected int debugStage = 0;
	protected Stack operandsComponent = new Stack();
	
	public SL(List<Term> term, List<Integer> numberOfOperands, SLProbabilityBitstreamConverter<T> converter) {
		this.term = term;
		this.numberOfOperands = numberOfOperands;
		this.converter = converter;
	}
	
	public SLProbabilityBitstream operate() {
		System.out.println("- Probability of each component as bitstream: ");
		converter.printBitstreams();
		System.out.println();
		
		// To rearrange the oder of elements in List 'numberOfOperands'
		numberOfOperands.add(numberOfOperands.remove(0));
		
		for (Term element : term) {
			if (element instanceof LiteralTerm) {
				LiteralTerm<T> component = (LiteralTerm<T>) element;
				operands.add(converter.bitstreams.get(component.get()));
				operandsComponent.add(component); // for checking...
			} else if (element instanceof ANDTerm) {
				operateAND();
			} else if (element instanceof ORTerm) {
				operateOR();
			}
		}
				
		// The last element of Stack 'operands' is the result value.
		return operands.pop();
	}
	
	public void operateAND() {
//		// Operate AND
//		List<SLProbabilityBitstream> operandsAND = new ArrayList<>();
//		int index = numberOfOperands.remove(0);
//
//		for (int i = 0; i < index; i++) {
//			operandsAND.add(operands.pop());
//		}
		
		// for checking...
		List<SLProbabilityBitstream> operandsAND = new ArrayList<>();
		List operandsANDComponent = new ArrayList();
		int index = numberOfOperands.remove(0);

		for (int i = 0; i < index; i++) {
			operandsAND.add(operands.pop());
			operandsANDComponent.add(operandsComponent.pop());
		}

		debugStage++;
		System.out.print("- [Stage " + debugStage + "] Operate AND: " + operandsANDComponent + "\n");
		for (int i = 0; i < index; i++) {
			System.out.print(operandsANDComponent.get(i) + ": ");
			System.out.println(operandsAND.get(i));
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

		// for checking...
		operandsComponent.push("Temp" + debugStage);
		System.out.println("- [Stage " + debugStage + "] Result:");
		System.out.print(operandsComponent.peek() + ": ");
		System.out.println(resultOfOperateAND + "\n");
	}
	
	public void operateOR() {
//		// Operate OR
//		List<SLProbabilityBitstream> operandsOR = new ArrayList<>();
//		int index = numberOfOperands.remove(0);
//	
//		for (int i = 0; i < index; i++) {
//			operandsOR.add(operands.pop());
//		}
		
		// for checking...
		List<SLProbabilityBitstream> operandsOR = new ArrayList<>();
		List operandsORComponent = new ArrayList();
		int index = numberOfOperands.remove(0);

		for (int i = 0; i < index; i++) {
			operandsOR.add(operands.pop());
			operandsORComponent.add(operandsComponent.pop());
		}

		debugStage++;
		System.out.print("- [Stage " + debugStage + "] Operate OR: " + operandsORComponent + "\n");
		for (int i = 0; i < index; i++) {
			System.out.print(operandsORComponent.get(i) + ": ");
			System.out.println(operandsOR.get(i));
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
		
		// for checking...
		operandsComponent.push("Temp" + debugStage);
		System.out.println("- [Stage " + debugStage + "] Result:");
		System.out.print(operandsComponent.peek() + ": ");
		System.out.println(resultOfOperateOR + "\n");
	}
	
}