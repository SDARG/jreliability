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
 * The {@link SL} uses the concept of stochastic logic [A] to evaluate a given
 * {@link Term}.
 * 
 * [A] Aliee, H. and Zarandi, H.R.. Fault tree analysis using stochastic logic:
 * A reliable and high speed computing. In Proceedings of the Annual Reliability
 * and Maintainability Symposium (RAMS), 2011.
 * 
 * @author glass, jlee
 *
 * @param <T>
 *            the type of the variables
 */
public class SL<T> {

	/**
	 * The {@link Term} to be evaluated.
	 */
	protected Term term;
	/**
	 * The length of the used bit streams for stochastic logic.
	 */
	protected final int bitStreamLength;
	/**
	 * A list that orders the terms in such a way that they can later be
	 * sequentially evaluated using a stack.
	 */
	protected List<Term> termsForStackProcessing = new ArrayList<>();
	/**
	 * The number of operands for each term which are then popped from the
	 * stack.
	 */
	protected Map<Term, Integer> numberOfOperands = new HashMap<>();
	/**
	 * The stack to process the terms.
	 */
	protected Stack<BitSet> operandsStack = new Stack<>();
	/**
	 * In stochastic logic, equal variables have to be modeled using exactly the
	 * same bit stream (since they are actually the same!). This is realized by
	 * the term cache.
	 */
	protected Map<Term, BitSet> termCache = new HashMap<>();

	/**
	 * Constructs an {@link SL} with a given {@link Term}.
	 * 
	 * @param term
	 *            the term to evaluate
	 */
	public SL(Term term) {
		this(term, 10000);
	}

	/**
	 * Constructs an {@link SL} with a given {@link Term} and a given length of
	 * the bit streams to use.
	 * 
	 * @param term
	 *            the term to evaluate
	 * @param bitStreamLength
	 *            the length of the bit streams
	 */
	public SL(Term term, int bitStreamLength) {
		this.term = term;
		this.bitStreamLength = bitStreamLength;
		this.initialize(term);
	}

	/**
	 * Initializes the {@link SL} by ordering the {@link Term} for the stack
	 * processing.
	 * 
	 * @param term
	 *            the term to initialize
	 */
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

	/**
	 * Calculates the probability of the {@link Term} (i.e. the top event) based
	 * on a given probabilities of the basic events.
	 * 
	 * @param transformer
	 *            the probabilities of the basic events
	 * @return the probability of the top event
	 */
	public double getProbabiliy(Transformer<T, Double> transformer) {
		operandsStack.clear();
		termCache.clear();
		evaluate(transformer);
		// Evaluate leaves the final bit stream of the top event on the stack
		BitSet bitstream = operandsStack.pop();
		double probability = (double) bitstream.cardinality() / bitstream.size();
		return probability;
	}

	/**
	 * The evaluation performs the actual stochastic logic calculations by a
	 * sequential processing of the different terms according to their order and
	 * using a stack. Note that this function leaves its current result on the
	 * stack - eventually, the result for the top event will remain on the
	 * stack!
	 * 
	 * @param transformer
	 *            the probability of the basic events
	 */
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
			} else {
				throw new IllegalArgumentException("SL does not support terms of class "+term.getClass());
			}
		}
	}

	/**
	 * The evaluation of an {@link ANDTerm} with respective AND operation on the
	 * bit streams of the operands.
	 * 
	 * @param term
	 *            the ANDTerm to evaluate
	 */
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

	/**
	 * The evaluation of an {@link ORTerm} with respective OR operation on the
	 * bit streams of the operands.
	 * 
	 * @param term
	 *            the ORTerm to evaluate
	 */
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

	/**
	 * The evaluation of an {@link NOTTerm} with respective flip of the bit
	 * streams of the operand.
	 * 
	 * @param term
	 *            the NOTTerm to evaluate
	 */
	protected void evaluateNOT(Term term) {
		BitSet operand = operandsStack.pop();
		BitSet result = (BitSet) operand.clone();
		result.flip(0, result.size());
		operandsStack.push(result);
	}

	/**
	 * Generates a {@link BitSet} representing the bit stream where the ratio of
	 * 1s and 0s resembles the given probability. This implementation does not
	 * apply this probability on a per-bit basis but considers the bit length
	 * with respective rounding effects!
	 * 
	 * @param probability
	 *            the probability to model with the bit stream
	 * @return the bit stream
	 */
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