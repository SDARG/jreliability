/**
 * 
 */
package de.cs12.reliability.javabdd;

import de.cs12.reliability.bdd.BDDProvider;
import de.cs12.reliability.bdd.BDDProviderFactory;

/**
 * The {@code BuDDyProviderFactory} is a {@code BDDProviderFactory} for the
 * BuDDy library.
 * 
 * @author lukasiewycz
 * 
 */
public class BuDDyProviderFactory implements BDDProviderFactory {

	protected static int INITIAL_VARIABLES = 100;

	/**
	 * Constructs a {@code BuDDyProviderFactory}.
	 */
	public BuDDyProviderFactory() {
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
		return new BuDDyProvider<T>(vars);
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
