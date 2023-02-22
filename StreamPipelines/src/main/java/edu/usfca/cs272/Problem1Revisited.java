package edu.usfca.cs272;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Project Euler Problem 1 is stated as follows:
 *
 * <blockquote> If we list all the natural numbers below 10 that are multiples
 * of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the
 * sum of all the multiples of 3 or 5 below 1000. </blockquote>
 *
 * This example illustrates the most straight-forward approach to solve this
 * problem and then the stream version of this solution.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class Problem1Revisited {
	/**
	 * Prints the sum of multiples of 3 or 5 less than 1000 to the console.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// traditional approach
		int max = 1000;
		int sum = 0;

		for (int i = 0; i < max; i++) {
			if (i % 3 == 0 || i % 5 == 0) {
				sum += i;
			}
		}

		System.out.println(sum);

		// stream approach
		int result = IntStream.range(0, max)
				.filter(i -> i % 3 == 0 || i % 5 == 0)
				.sum();

		System.out.println(result);

		// using generic method
		int[] multiples = { 3, 5 };
		System.out.println(sumMultiples(max, multiples));
	}

	/*
	 * An advanced way to create a generalized solution of this problem using
	 * lambdas and streams.
	 */

	/**
	 * Returns the sum of multiples less than the max value.
	 *
	 * @param max the maximum value
	 * @param multiples the multiples
	 * @return the sum
	 */
	public static int sumMultiples(int max, int[] multiples) {
		// create one predicate for multiples using streams
		IntPredicate predicate = Arrays.stream(multiples)
				// convert an integer into a predicate
				.mapToObj(multiple -> {
					IntPredicate p = i -> i % multiple == 0;
					return p;
				})
				// start with the predicate i -> false, and combine others using "or"
				.reduce(i -> false, IntPredicate::or);

		// calculate sum using streams
		return IntStream.range(0, max)
				.filter(predicate)
				.sum();
	}
}
