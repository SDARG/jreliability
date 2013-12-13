package org.jreliability.tester;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDs;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;

public class Klausur2012 {
	public static void main(String[] args) {
		BDDProvider<String> s = new JBDDProviderFactory(Type.JDD).getProvider();
		s.add("a", "b", "c", "d");
		BDD<String> a = s.get("a");
		BDD<String> b = s.get("b");
		BDD<String> c = s.get("c");
		BDD<String> d = s.get("d");

		BDD<String> f = a.copy();
		f.andWith("b");
		BDD<String> bddc = c.copy();
		bddc.orWith("d");
		f.orWith(bddc);
		System.out.println(BDDs.toDot(f));

		// BDD<String> g1 = a.copy().not();
		// g1.andWith(b.copy());
		// g1.andWith(d.copy().not());
		//
		// BDD<String> g2 = a.copy();
		// g2.andWith(b.copy().not());
		// g2.andWith(c.copy().not());
		//
		// BDD<String> g3 = g2.and(d);
		//
		// BDD<String> g = g1.copy();
		// g.orWith(g2);
		// g.orWith(g3);
		//
		// g.orWith(a.copy().and(c.not()));
		// System.out.println(BDDs.toDot(g));
		//
		// System.out.println(BDDs.toDot(g.or(f)));
		// System.out.println(BDDs.toDot(g.or(f.not())));

		System.out.println(BDDs.toDot(f.forAll("d")));

		System.out.println(BDDs.toDot(f.and(d)));
		System.out.println(BDDs.toDot(f.and(d.not())));
	}
}
