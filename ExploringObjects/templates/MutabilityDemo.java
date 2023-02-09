package edu.usfca.cs272;

import java.util.Arrays;

public class MutabilityDemo {
	public static void main(String[] args) {
		int[] a = new int[] { 0, 1, 2 };

		System.out.println("a: " + Arrays.toString(a));
		System.out.println();
	}

	public static void incrementFirst(int[] z) {
		z[0]++;
		System.out.println("z: " + Arrays.toString(z));
	}

	public static void incrementPrimitive(int z) {
		z++;
		System.out.println("z: " + z);
	}
}
