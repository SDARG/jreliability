package org.jreliability.sl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.function.ReliabilityFunction;

import org.jreliability.sl.SLProbabilityBitstreamConverter;

public class SLTTRF<T> {

	protected Transformer<T, ReliabilityFunction> transformer;
	protected SLProbabilityBitstreamConverter<T> bitstreamConverter = new SLProbabilityBitstreamConverter<>();
	protected List<Term> termToPostfix = new ArrayList<>();
	protected List<Integer> numberOfOperands = new ArrayList<>();
	
	public SLTTRF(Transformer<T, ReliabilityFunction> transformer) {
		this.transformer = transformer;
	}
	
	public void convertToSL(Term term) {
		termToPostfix = transform(term);
		
		SL<T> SL = new SL<>(termToPostfix, numberOfOperands, bitstreamConverter);
		SLProbabilityBitstream result = SL.operate();
		System.out.println("- Final Result:\n" + result);
	}
	
//	public void convertToSL(Term term, Transformer<T, ReliabilityFunction> transformer) {
//		// Transformer<T, ReliabilityFunction> transformer): In this case, getY = getProbability
//	}
	
	public List<Term> transform(Term term) {
		if (term instanceof ANDTerm) {
			ANDTerm andTerm = (ANDTerm) term;
			transformAND(andTerm);
		} else if (term instanceof ORTerm) {
			ORTerm orTerm = (ORTerm) term;
			transformOR(orTerm);
		} else if (term instanceof LiteralTerm) {
			LiteralTerm<T> literalTerm = (LiteralTerm<T>) term;
			transformLiteral(literalTerm, transformer);			
		}
		termToPostfix.add(term);
		
		return termToPostfix;
	}
	
	public void transformAND(ANDTerm term) {
		List<Term> terms = term.getTerms();
		numberOfOperands.add(terms.size());
		for (Term element : terms) {
			transform(element);
		}
	}
	
	public void transformOR(ORTerm term) {
		List<Term> terms = term.getTerms();
		numberOfOperands.add(terms.size());
		for (Term element : terms) {
			transform(element);
		}
	}
	
	public void transformLiteral(LiteralTerm<T> term, Transformer<T, ReliabilityFunction> transformer) {
		T component = term.get();
		ReliabilityFunction reliabilityFunction = transformer.transform(component);
		
		// bitstreamConverter.convertToBitstream(component, arrayLength, pValue)
		bitstreamConverter.convertToBitstream(component, 100, reliabilityFunction.getY(0.5));
	}
	
}
