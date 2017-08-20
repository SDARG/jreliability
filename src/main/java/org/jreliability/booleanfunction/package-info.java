/**
 * Provides the classes for the Boolean functions used to model the system. The term structure follows the scheme given
 * by the polish notation, i.e., {@code (operator operand1 ... operand2)}. When printed or should be read in from
 * string, the following formatting guidelines are strictly enforced:
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
