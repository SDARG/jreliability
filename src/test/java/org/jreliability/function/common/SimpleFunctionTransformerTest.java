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

package org.jreliability.function.common;

import java.util.HashMap;
import java.util.Map;

import org.jreliability.function.ReliabilityFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The {@link SimpleFunctionTransformerTest} to test the
 * {@link SimpleFunctionTransformer}.
 * 
 * @author glass
 *
 */
public class SimpleFunctionTransformerTest {

	@Test
	public void testSet() {
		SimpleFunctionTransformer<String> transformer = new SimpleFunctionTransformer<>();
		String string = "EVENT";
		ReliabilityFunction f = new ExponentialReliabilityFunction(0.005);
		transformer.set(string, f);
		Assertions.assertSame(f, transformer.transform(string));
	}

	@Test
	public void testTransform() {
		Map<String, ReliabilityFunction> map = new HashMap<>();
		String string = "EVENT";
		ReliabilityFunction f = new ExponentialReliabilityFunction(0.005);
		map.put(string, f);
		String stringTwo = "EVENT2";
		ReliabilityFunction fTwo = new WeibullReliabilityFunction(0.004, 2);
		map.put(stringTwo, fTwo);

		SimpleFunctionTransformer<String> transformer = new SimpleFunctionTransformer<>(map);
		Assertions.assertSame(f, transformer.transform(string));
		Assertions.assertSame(fTwo, transformer.transform(stringTwo));
	}

}
