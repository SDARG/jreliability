package org.jreliability.sl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.NOTTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.function.ReliabilityFunction;

import org.jreliability.sl.SLProbabilityBitstreamConverter;

public class SLReliabilityFunction<T> implements ReliabilityFunction {

	protected Term term;
	protected Transformer<T, ReliabilityFunction> transformer;
	protected int numberOfBits;

	// To re-arrange term for using Stack
	protected List<Term> termToPostfix = new ArrayList<>();
	protected List<Integer> numberOfOperands = new ArrayList<>();
	protected SLProbabilityBitstreamConverter<T> bitstreamConverter = new SLProbabilityBitstreamConverter<>();
	
	public SLReliabilityFunction(Term term, Transformer<T, ReliabilityFunction> transformer, int numberOfBits) {
		this.term = term;
		this.transformer = transformer;
		this.numberOfBits = numberOfBits;
	}
	
	@Override
	public double getY(double x) {
		termToPostfix = transform(term, x);
		
		SL<T> SL = new SL<>(termToPostfix, numberOfOperands, bitstreamConverter);
		SLProbabilityBitstream result = SL.operate();
		System.out.println("- Final Result:\n" + result);
		
		return 0.0;
	}
	
	public List<Term> transform(Term term, double x) {
		if (term instanceof ANDTerm) {
			ANDTerm andTerm = (ANDTerm) term;
			transformAND(andTerm, x);
		} else if (term instanceof ORTerm) {
			ORTerm orTerm = (ORTerm) term;
			transformOR(orTerm, x);
		} else if (term instanceof NOTTerm) {
			NOTTerm notTerm = (NOTTerm) term;
			transformNOT(notTerm, x);
		} else if (term instanceof LiteralTerm) {
			LiteralTerm<T> literalTerm = (LiteralTerm<T>) term;
			transformLiteral(literalTerm, x);
		}
		termToPostfix.add(term);
		
		return termToPostfix;
	}
	
	public void transformAND(ANDTerm term, double x) {
		List<Term> terms = term.getTerms();
		numberOfOperands.add(terms.size());
		for (Term element : terms) {
			transform(element, x);
		}
	}
	
	public void transformOR(ORTerm term, double x) {
		List<Term> terms = term.getTerms();
		numberOfOperands.add(terms.size());
		for (Term element : terms) {
			transform(element, x);
		}
	}
	
	public void transformNOT(NOTTerm term, double x) {		
		Term element = term.get();
		transform(element, x);
	}
		
	public void transformLiteral(LiteralTerm<T> term, double x) {		
		T component = term.get();
		ReliabilityFunction reliabilityFunction = transformer.transform(component);
		
		bitstreamConverter.convertToBitstream(component, numberOfBits, reliabilityFunction.getY(x));
	}

}
