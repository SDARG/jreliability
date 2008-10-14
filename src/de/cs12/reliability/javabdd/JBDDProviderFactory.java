/**
 * 
 */
package de.cs12.reliability.javabdd;

import java.util.HashMap;
import java.util.Map;

import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;

/**
 * The {@code JDDProviderFactory} is a {@code BDDProviderFactory} for the
 * JavaBDD library.
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class JBDDProviderFactory implements BDDProviderFactory {

	/**
	 * The {@code Type} of real {@code BDD} implementation.
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
		JDD,
		/**
		 * Use BuDDy.
		 */
		BUDDY,
		/**
		 * Use CUDD.
		 */
		CUDD,
		/**
		 * Use CAL.
		 */
		CAL;
	}

	/**
	 * The used {@code Type} of real {@code BDD} implementation.
	 */
	protected final Type type;
	/**
	 * The number of initially allocated variables.
	 */
	protected static int INITIAL_VARIABLES = 10;
	/**
	 * A map that provides each requested {@code Type} of real {@code BDD}
	 * implementation with its specific {@code JBDDProvider}.
	 */
	protected static Map<Type, JBDDProvider<?>> staticProviders = new HashMap<Type, JBDDProvider<?>>();

	/**
	 * Constructs a {@code JDDProviderFactory}.
	 */
	public JBDDProviderFactory() {
		this(Type.JAVABDD);
	}

	/**
	 * Constructs a {@code JDDProviderFactory}.
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
	 * @see de.cs12.reliability.bdd.BDDProviderFactory#getProvider()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> BDDProvider<T> getProvider() {
		final boolean useNative;

		switch (type) {
		case BUDDY:
		case CUDD:
		case CAL:
			useNative = true;
			break;
		default:
			useNative = false;
		}

		final JBDDProvider<T> provider;

		if (useNative) {
			if (!staticProviders.containsKey(type)) {
				provider = new JBDDProvider<T>(type, INITIAL_VARIABLES);
				staticProviders.put(type, provider);
			} else {
				provider = (JBDDProvider<T>) staticProviders.get(type);
			}
		} else {
			provider = new JBDDProvider<T>(type, INITIAL_VARIABLES);
		}

		return provider;
	}

}
