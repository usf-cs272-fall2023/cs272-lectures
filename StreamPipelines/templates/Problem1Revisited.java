package edu.usfca.cs272;

public class Problem1Revisited {
	public static void main(String[] args) {
		int max = 1000;
		int sum = 0;

		for (int i = 0; i < max; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
			}
		}

		System.out.println(sum);

		// TODO
	}

	public static int sumMultiples(int max, int[] multiples) {
		// TODO
		return -1;
	}
}
