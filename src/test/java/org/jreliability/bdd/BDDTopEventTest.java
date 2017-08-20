package org.jreliability.bdd;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link BDDTopEventTest} test the {@link BDDTopEvent}.
 * 
 * @author reimann
 *
 */
public class BDDTopEventTest {
	/**
	 * The used {@link BDDProvider}.
	 */
	protected BDDProvider<String> provider;

	/**
	 * Initialize the provider.
	 */
	@Before
	public void initProvider() {
		BDDProviderFactory factory = new JBDDProviderFactory(Type.JAVABDD);
		provider = factory.getProvider();
	}

	@Test
	public void testCalculate() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> and = a.and(b);

		BDDTopEvent<String> event = new BDDTopEvent<>(and);
		double result = event.calculate(new Transformer<String, Double>() {
			@Override
			public Double transform(String input) {
				return 0.5;
			}
		});

		Assert.assertEquals(0.25, result, 0.000001);
	}
	
	@Test
	public void testCalculateSeriesParallel() {
		BDD<String> a = provider.get("a");
		BDD<String> b = provider.get("b");
		BDD<String> bdd = a.or(b);
		BDD<String> c = provider.get("c");
		bdd = bdd.and(c);
		
		BDDTopEvent<String> event = new BDDTopEvent<>(bdd);
		double result = event.calculate(new Transformer<String, Double>() {
			@Override
			public Double transform(String input) {
				return 0.9;
			}
		});

		Assert.assertEquals(0.891, result, 0.000001);
	}
}
