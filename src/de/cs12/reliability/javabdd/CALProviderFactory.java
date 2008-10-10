/**
 * 
 */
package de.cs12.reliability.javabdd;

import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;

/**
 * The {@code CALProviderFactory} is a {@code BDDProviderFactory} for the
 * CAL library.
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class CALProviderFactory implements BDDProviderFactory {

	protected static int INITIAL_VARIABLES = 100;

	/**
	 * Constructs a {@code JDDProviderFactory}.
	 */
	public CALProviderFactory() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.cs12.reliability.evaluator.bdd.BDDProviderFactory#getProvider(int)
	 */
	public <T> BDDProvider<T> getProvider(int vars) {
		if (vars <= 0) {
			throw new IllegalArgumentException(
					"The initial number of variables has to be minimal 1");
		}
		return new CALProvider<T>(vars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProviderFactory#getProvider()
	 */
	@Override
	public <T> BDDProvider<T> getProvider() {
		return getProvider(INITIAL_VARIABLES);
	}

}
