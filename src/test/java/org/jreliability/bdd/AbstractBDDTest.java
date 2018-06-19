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

import org.junit.Before;

/**
 * The {@link AbstractBDDTest} is the base class for all tests on {@link BDD}s.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class AbstractBDDTest {

	/**
	 * The used {@link BDDProvider}.
	 */
	protected BDDProviderFactory factory;

	/**
	 * Initialize the specific factory.
	 */
	@Before
	public abstract void init();

}
