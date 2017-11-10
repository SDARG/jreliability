package org.jreliability.sl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SLProbabilityBitstream {
	
	protected final int arrayLength;
	protected int[] probabilityArray;
	
	public SLProbabilityBitstream(int arrayLength) {
		this.arrayLength = arrayLength;
		probabilityArray = new int[arrayLength];
		setProbabilityArray();
	}
	
	public SLProbabilityBitstream(int arrayLength, int pValue) {
		this.arrayLength = arrayLength;
		probabilityArray = new int[arrayLength];
		setProbabilityArray(pValue);
	}
	
	public SLProbabilityBitstream(int arrayLength, double pValue) {
		this.arrayLength = arrayLength;
		probabilityArray = new int[arrayLength];
		setProbabilityArray(pValue);
	}
	
	// Generate a probability (as an array of bitstream) randomly
	public void setProbabilityArray() {
		Random randomValue = new Random();
		int p = 5;
		for (int i = 0; i < probabilityArray.length; i++) {
			if (randomValue.nextInt(10) < p) {
				probabilityArray[i] = 1;
			} else {
				probabilityArray[i] = 0;
			}
		}
	}
	
	// Probability Value as Integer type...
	public void setProbabilityArray(int pValue) {
		ArrayList<Integer> randomIndex = new ArrayList<>();
		for (int i = 0; i < probabilityArray.length; i++) {
			randomIndex.add(i);
		}
		Collections.shuffle(randomIndex);
		
		for (int i = 0; i < pValue; i++) {
			probabilityArray[randomIndex.get(i)] = 1;
		}
	}
	
	// Probability Value as Double type...
	public void setProbabilityArray(double pValue) {
		int rValue = 0;
		
		if (probabilityArray.length % 10 == 0) {
			rValue = (int) Math.round(pValue * probabilityArray.length);
		}
		
		ArrayList<Integer> randomIndex = new ArrayList<>();
		for (int i = 0; i < probabilityArray.length; i++) {
			randomIndex.add(i);
		}
		Collections.shuffle(randomIndex);
		
		for (int i = 0; i < rValue; i++) {
			probabilityArray[randomIndex.get(i)] = 1;
		}
	}

	public int[] getProbabilityArray() {
		return probabilityArray;
	}
	
	public double getProbability() {
		int getProbabilityValue = 0;
		for (int element : probabilityArray) {
			if (element == 1) {
				getProbabilityValue++;
			}
		}
		
		return (double) getProbabilityValue / probabilityArray.length;
	}
	
	public void printProbabilityBitstream() {
		System.out.print("[");
		for (int i = 0; i < probabilityArray.length; i++) {
			System.out.print(i);
		}
		System.out.print("]");
	}
	
	public String toString() {		
		StringBuffer s = new StringBuffer();
		
//		s.append(getProbability()).append(" [");
//		for (int i = 0; i < probabilityArray.length; i++) {
//			s.append(probabilityArray[i]);
//		}
//		s.append("]");
		
		s.append(getProbability());
		
		return s.toString();
	}
	
}
