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
/**
 * Provides the classes for the Boolean functions used to model the system. The
 * term structure follows the scheme given by the polish notation, i.e.,
 * {@code (operator operand1 ... operand2)}. When printed or should be read in
 * from string, the following formatting guidelines are strictly enforced:
 * <p>
 * For all terms containing an operator:<br>
 * (operator term1 ... termN)
 * </p>
 * <p>
 * For all terms without an operator (variables):<br>
 * "variable"
 * </p>
 * 
 */
package org.jreliability.booleanfunction;
