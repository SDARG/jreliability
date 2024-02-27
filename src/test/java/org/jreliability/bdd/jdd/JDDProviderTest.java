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

package org.jreliability.bdd.jdd;

import org.jreliability.bdd.AbstractBDDProviderTest;
import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.javabdd.JBDDProvider;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.bdd.javabdd.JBDDProviderFactory.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 * The {@link JDDProviderTest} is the {@link AbstractBDDProviderTest} for the
 * {@link JDD}.
 * 
 * @author lukasiewycz
 * 
 */
public class JDDProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.test.AbstractBDDProviderTest#init()
	 */
	@Override
	@BeforeEach
	public void init() {
		this.factory = new JBDDProviderFactory(Type.JDD);
	}

	@Test
	public void testVariableGrowthRate() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			BDDProvider<String> provider = new JBDDProvider<>(Type.JDD, 10, Integer.MAX_VALUE / 10, 20000);
			for (int i = 0; i < 200; i++) {
				@SuppressWarnings("unused")
				BDD<String> a = provider.get("" + i);
			}
		});
	}

	@Test
	public void testVariableNotFound() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
			BDDProvider<String> provider = new JBDDProvider<>(Type.JDD, 10, Integer.MAX_VALUE, 20000);
			for (int i = 0; i < 20; i++) {
				@SuppressWarnings("unused")
				BDD<String> a = provider.get("" + i);
			}
		});
	}

}
