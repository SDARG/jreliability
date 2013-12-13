package org.jreliability.tester;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDs;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;

public class Klausur2013 {

	public static void main(String[] args) {
		BDDProvider<String> provider = new JBDDProviderFactory(Type.JDD).getProvider();
		provider.add("s0", "s1", "s2", "s3");
		BDD<String> e = provider.get("s0");
		BDD<String> g = provider.get("s1");
		BDD<String> s = provider.get("s2");
		BDD<String> d = provider.get("s3");

		BDD<String> bdd = e.copy().not();
		BDD<String> bdd2 = e.copy().not();
		BDD<String> bdd3 = e.copy().not();
		BDD<String> bdd4 = e.copy();

		bdd.andWith(g.copy().not());
		bdd.andWith(s.copy());
		bdd.andWith(d.copy().not());

		bdd2.andWith(g.copy().not());
		bdd2.andWith(s.copy().not());
		bdd2.andWith(d.copy());

		bdd3.andWith(g.copy());
		bdd3.andWith(s.copy().not());
		bdd3.andWith(d.copy().not());

		bdd4.andWith(g.copy().not());
		bdd4.andWith(s.copy().not());
		bdd4.andWith(d.copy().not());

		bdd.orWith(bdd2);
		bdd.orWith(bdd3);
		bdd.orWith(bdd4);
		System.out.println(BDDs.toDot(bdd));

	}
}
