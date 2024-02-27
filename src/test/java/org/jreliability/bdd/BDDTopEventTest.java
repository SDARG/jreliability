/*******************************************************************************
 * JReliability is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JReliability. If not, see http://www.gnu.org/licenses/.
 *******************************************************************************/
package org.jreliability.bdd;

import org.apache.commons.collections15.Transformer;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	@BeforeEach
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

		Assertions.assertEquals(0.25, result, 0.000001);
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

		Assertions.assertEquals(0.891, result, 0.000001);
	}
}
