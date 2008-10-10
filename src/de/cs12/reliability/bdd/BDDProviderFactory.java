package de.cs12.reliability.bdd;

/**
 * The {@code BDDProviderFactory} creates {@code BDDProviders}.
 * 
 * @author reimann, lukasiewycz
 * 
 */
public interface BDDProviderFactory {

	/**
	 * Returns a {@code BDDProvider}.
	 * 
	 * @param <T>
	 *            the type of variables
	 * @return a BDDProvider
	 */
	public <T> BDDProvider<T> getProvider();

}
