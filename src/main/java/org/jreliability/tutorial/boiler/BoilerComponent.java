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
package org.jreliability.tutorial.boiler;

/**
 * The abstract {@link BoilerComponent} is the basic class that models each of
 * the components used in the model of the {@link Boiler}.
 * 
 * @author glass
 * 
 */
public abstract class BoilerComponent {

	/**
	 * The name of the component.
	 */
	protected final String name;

	/**
	 * Constructs a {@link BoilerComponent} with a given name.
	 * 
	 * @param name
	 *            the name of the component
	 */
	public BoilerComponent(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the component.
	 * 
	 * @return the name of the component
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name;
	}

}
