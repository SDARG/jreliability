/**
 * 
 */
package de.cs12.bdd.javabdd;

import de.cs12.bdd.BDDProvider;
import de.cs12.bdd.BDDProviderFactory;

/**
 * The {@code JBDDProviderFactory} is a {@code BDDProviderFactory} for the
 * JavaBDD library.
 * 
 * @author reimann
 * 
 */
public class JBDDProviderFactory implements BDDProviderFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.bdd.BDDProviderFactory#getProvider(int)
	 */
	public <T> BDDProvider<T> getProvider(int vars) {
		return new JBDDProvider<T>(vars);
	}

}
