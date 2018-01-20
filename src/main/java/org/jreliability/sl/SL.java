package org.jreliability.sl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.FALSETerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.TRUETerm;

/**
 * @author glass, jlee
 *
 * @param <T>
 */
public class SL<T> {

	protected Term term;
	protected final int bitStreamLength;

	// Order the terms to enable stack-based processing
	protected Map<Term, Integer> numberOfOperands = new HashMap<>();
	protected List<Term> termsForStackProcessing = new ArrayList<>();

	protected Stack<BitSet> operandsStack = new Stack<>();
	protected Map<Term, BitSet> termCache = new HashMap<>();

	public SL(Term term) {
		this(term, 10000);
	}

	public SL(Term term, int bitStreamLength) {
		this.term = term;
		this.bitStreamLength = bitStreamLength;
		this.initialize(term);
	}

	protected void initialize(Term term) {
		if (term instanceof NOTTerm) {
			initialize(((NOTTerm) term).get());
			numberOfOperands.put(term, 1);
		} else {
			List<Term> terms = new ArrayList<Term>();
			if (term instanceof ANDTerm) {
				terms = ((ANDTerm) term).getTerms();
				numberOfOperands.put(term, terms.size());
			} else if (term instanceof ORTerm) {
				terms = ((ORTerm) term).getTerms();
				numberOfOperands.put(term, terms.size());
			}
			for (Term subTerm : terms) {
				initialize(subTerm);
			}
		}
		termsForStackProcessing.add(term);
	}

	public double getProbabiliy(Transformer<T, Double> transformer) {
		operandsStack.clear();
		termCache.clear();
		evaluate(transformer);
		BitSet bitstream = operandsStack.pop();
		double probability = (double) bitstream.cardinality() / bitstream.size();
		return probability;
	}

	protected void evaluate(Transformer<T, Double> transformer) {
		for (Term term : termsForStackProcessing) {
			if (term instanceof LiteralTerm) {
				BitSet bitstream = termCache.get(term);
				if (bitstream == null) {
					LiteralTerm<T> component = (LiteralTerm<T>) term;
					bitstream = generateRandomBitstream(transformer.transform(component.get()));
					termCache.put(term, bitstream);
				}
				operandsStack.push(bitstream);
			} else if (term instanceof FALSETerm) {
				BitSet bitstream = new BitSet(bitStreamLength);
				bitstream.clear();
				operandsStack.push(bitstream);
			} else if (term instanceof TRUETerm) {
				BitSet bitstream = new BitSet(bitStreamLength);
				bitstream.set(0, bitstream.size(), true);
				operandsStack.push(bitstream);
			} else if (term instanceof ANDTerm) {
				evaluateAND(term);
			} else if (term instanceof ORTerm) {
				evaluateOR(term);
			} else if (term instanceof NOTTerm) {
				evaluateNOT(term);
			}
		}
	}

	protected void evaluateAND(Term term) {
		int myNumberOfOperands = numberOfOperands.get(term);
		List<BitSet> operands = new ArrayList<>();
		while (myNumberOfOperands > 0) {
			operands.add(operandsStack.pop());
			myNumberOfOperands--;
		}

		// Initialize result with a TRUE bitstream and then AND
		BitSet result = new BitSet(bitStreamLength);
		result.set(0, result.size(), true);
		for (BitSet operand : operands) {
			result.and(operand);
		}
		operandsStack.push(result);
	}

	protected void evaluateOR(Term term) {
		int myNumberOfOperands = numberOfOperands.get(term);
		List<BitSet> operands = new ArrayList<>();
		while (myNumberOfOperands > 0) {
			operands.add(operandsStack.pop());
			myNumberOfOperands--;
		}

		// Initialize result with a FALSE bitstream and then OR
		BitSet result = new BitSet(bitStreamLength);
		result.clear();
		for (BitSet operand : operands) {
			result.or(operand);
		}
		operandsStack.push(result);
	}

	protected void evaluateNOT(Term term) {
		BitSet operand = operandsStack.pop();
		BitSet result = (BitSet) operand.clone();
		result.flip(0, result.size());
		operandsStack.push(result);
	}

	protected BitSet generateRandomBitstream(double probability) {
		BitSet bitstream = new BitSet(bitStreamLength);
		int rValue = (int) Math.round(probability * bitstream.size());

		ArrayList<Integer> randomIndex = new ArrayList<>();
		for (int i = 0; i < bitstream.size(); i++) {
			randomIndex.add(i);
		}
		Collections.shuffle(randomIndex);

		for (int i = 0; i < rValue; i++) {
			bitstream.set(randomIndex.get(i), true);
		}
		return bitstream;
	}

}