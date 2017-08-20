/**
 * JReliability is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.jreliability.bdd.javabdd;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.bdd.BDD;
import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;

/**
 * The {@link JBDDProviderFactory} is a {@link BDDProviderFactory} for the JavaBDD library.
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class JBDDProviderFactory implements BDDProviderFactory {

	/**
	 * The {@link Type} of real {@link BDD} implementation.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum Type {
		/**
		 * Use JavaBDD.
		 */
		JAVABDD,
		/**
		 * Use JDD.
		 */
		JDD;
	}

	/**
	 * The used {@link Type} of real {@link BDD} implementation.
	 */
	protected final Type type;
	/**
	 * The number of initially allocated variables.
	 */
	protected static final int INITIAL_VARIABLES = 10;
	/**
	 * A map that provides each requested {@link Type} of real {@link BDD} implementation with its specific
	 * {@link JBDDProvider}.
	 */
	protected static Map<Type, JBDDProvider<?>> staticProviders = new HashMap<>();

	/**
	 * Constructs a {@link JBDDProviderFactory}.
	 */
	public JBDDProviderFactory() {
		this(Type.JAVABDD);
	}

	/**
	 * Constructs a {@link JBDDProviderFactory}.
	 * 
	 * @param type
	 *            the type of bdd library
	 */
	public JBDDProviderFactory(Type type) {
		super();
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jreliability.bdd.BDDProviderFactory#getProvider()
	 */
	@Override
	public <T> BDDProvider<T> getProvider() {
		final JBDDProvider<T> provider = new JBDDProvider<>(type, INITIAL_VARIABLES);

		return provider;
	}

}
