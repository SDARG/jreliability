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
 * The {@link Controller} models a controlling component that is used for
 * activating the pumps and controlling the water temperature.
 * 
 * @author glass
 * 
 */
public class Controller extends BoilerComponent {

	/**
	 * Constructs a {@link Controller} with a given name.
	 * 
	 * @param name
	 *            the name of the controller
	 */
	public Controller(String name) {
		super(name);
	}

}
