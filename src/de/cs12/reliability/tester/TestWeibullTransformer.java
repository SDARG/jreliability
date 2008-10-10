package de.cs12.reliability.tester;

import java.util.HashMap;
import java.util.Map;

import de.cs12.reliability.function.Distribution;
import de.cs12.reliability.function.Function;
import de.cs12.reliability.function.FunctionTransformer;
import de.cs12.reliability.function.WeibullDistribution;

/**
 * The {@code TestWeibullTransformer}
 * 
 * @author glass
 * 
 */
public class TestWeibullTransformer implements FunctionTransformer<String> {

	protected final String heater = "Heater";
	protected final String pump1 = "Pump 1";
	protected final String pump2 = "Pump 2";

	protected Map<String, Function> functions = new HashMap<String, Function>();
	Distribution heaterDistribution = new WeibullDistribution(0.1, 2);
	Distribution pump1Distribution = new WeibullDistribution(1, 3);
	Distribution pump2Distribution = new WeibullDistribution(1.2, 3);

	/**
	 * Constructs a {@code TestWeibullTransformer}.
	 * 
	 */
	public TestWeibullTransformer() {
		functions.put(heater, heaterDistribution);
		functions.put(pump1, pump1Distribution);
		functions.put(pump2, pump2Distribution);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.common.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Function transform(String a) {
		return functions.get(a);
	}

}
