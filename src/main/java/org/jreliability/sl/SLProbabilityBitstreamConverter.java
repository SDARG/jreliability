package org.jreliability.sl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jreliability.sl.SLProbabilityBitstream;

public class SLProbabilityBitstreamConverter<T> {
	
	Map<T, SLProbabilityBitstream> bitstreams = new HashMap<>();
	
	// Generate a probability (as an array of bitstream) randomly
	public void convertToBitstream(T component, int arrayLength) {
		SLProbabilityBitstream bitstream = new SLProbabilityBitstream(arrayLength);
		bitstreams.put(component, bitstream);
	}
	
	public void convertToBitstream(T component, int arrayLength, int pValue) {
		SLProbabilityBitstream bitstream = new SLProbabilityBitstream(arrayLength, pValue);
		bitstreams.put(component, bitstream);		
	}
	
	public void convertToBitstream(T component, int arrayLength, double pValue) {
		SLProbabilityBitstream bitstream = new SLProbabilityBitstream(arrayLength, pValue);
		bitstreams.put(component, bitstream);
	}
	
	public void printBitstreams() {
		Set<T> keySet = bitstreams.keySet();
		for(T tempKey : keySet) {
			System.out.println(tempKey + " = " + bitstreams.get(tempKey));
		}
	}

}

