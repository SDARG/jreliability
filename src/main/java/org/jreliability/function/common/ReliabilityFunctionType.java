package org.jreliability.function.common;

/**
 * determines which kind of functions the {@link IntervalFunctionTransformer} is
 * to initialize the resources with
 * 
 * @author brand
 *
 */

public enum ReliabilityFunctionType {

	/**
	 * Use Constant (time independent) Reliability Functions
	 */
	CONSTANT,

	/**
	 * Use Exponential Reliability Functions
	 */
	EXPONENTIAL,

	/**
	 * Use Hjorth Reliability Functions
	 */
	HJORTH,

	/**
	 * Use Weibull Reliability Functions
	 */
	WEIBULL,

}
