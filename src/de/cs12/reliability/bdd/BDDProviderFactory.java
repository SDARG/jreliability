package de.cs12.reliability.bdd;

/**
 * The {@code BDDProviderFactory} creates {@code BDDProviders}.
 * 
 * @author reimann
 * 
 */
public interface BDDProviderFactory {

	/**
	 * Returns a {@code BDDProvider} with a given number of variables to
	 * allocate.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @param vars
	 *            the number of variables to allocate
	 * @return a BDDProvider
	 */
	public <T> BDDProvider<T> getProvider(int vars);
}
