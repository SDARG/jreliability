package org.jreliability.sl;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.function.ReliabilityFunction;

public class SimpleSystem {

	protected SimpleComponent C1 = new SimpleComponent("Component1");
	protected SimpleComponent C2 = new SimpleComponent("Component2");
	protected SimpleComponent C3 = new SimpleComponent("Component3");
	protected SimpleComponent C4 = new SimpleComponent("Component4");
	protected SimpleComponent C5 = new SimpleComponent("Component5");
	protected SimpleComponent C6 = new SimpleComponent("Component6");
	protected SimpleComponent C7 = new SimpleComponent("Component7");
	protected SimpleComponent C8 = new SimpleComponent("Component8");
	protected SimpleComponent C9 = new SimpleComponent("Component9");
		
	protected List<SimpleComponent> components = new ArrayList<>();

	protected SimpleSystemTransformer transformer;
	 	
	public SimpleSystem() {
		super();
		initialize();
		transformer = new SimpleSystemTransformer(this);
	}
	
	private void initialize() {
		components.add(C1);
		components.add(C2);
		components.add(C3);
		components.add(C4);
		components.add(C5);
		components.add(C6);
		components.add(C7);
		components.add(C8);
		components.add(C9);
	}
	
	public Term getTerm() {
		LiteralTerm<SimpleComponent> C1Literal = new LiteralTerm<>(C1);
		LiteralTerm<SimpleComponent> C2Literal = new LiteralTerm<>(C2);
		LiteralTerm<SimpleComponent> C3Literal = new LiteralTerm<>(C3);
		LiteralTerm<SimpleComponent> C4Literal = new LiteralTerm<>(C4);
		LiteralTerm<SimpleComponent> C5Literal = new LiteralTerm<>(C5);
		LiteralTerm<SimpleComponent> C6Literal = new LiteralTerm<>(C6);
		LiteralTerm<SimpleComponent> C7Literal = new LiteralTerm<>(C7);
		LiteralTerm<SimpleComponent> C8Literal = new LiteralTerm<>(C8);
		LiteralTerm<SimpleComponent> C9Literal = new LiteralTerm<>(C9);
		
		ANDTerm subSystem1 = new ANDTerm();
		subSystem1.add(C1Literal);
		subSystem1.add(C2Literal);
		
		ANDTerm subSystem2 = new ANDTerm();
		subSystem2.add(C3Literal);
		subSystem2.add(C4Literal);
		
		ANDTerm subSystem3 = new ANDTerm();
		subSystem3.add(C5Literal);
		subSystem3.add(C6Literal);
		subSystem3.add(C7Literal);
		
		ORTerm system = new ORTerm();
		system.add(subSystem1);
		system.add(subSystem2);
		system.add(subSystem3);
		system.add(C8Literal);
		system.add(C9Literal);
		
		return system;
	}
		
	public List<SimpleComponent> getComponents() {
		return components;
	}
		
	public SimpleSystemTransformer getTransformer() {
		return transformer;
	}
}
