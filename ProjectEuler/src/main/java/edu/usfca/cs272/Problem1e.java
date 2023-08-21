package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Project Euler Problem 1 is stated as follows:
 *
 * <blockquote> If we list all the natural numbers below 10 that are multiples
 * of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the
 * sum of all the multiples of 3 or 5 below 1000. </blockquote>
 *
 * This example illustrates the reusable and generalized approach, but this time
 * using streams and lambda functions that were introduced in Java 8.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class Problem1e {
	/**
	 * Returns all multiples of a value less than a maximum.
	 *
	 * @param value multiple to calculate
	 * @param max maximum value
	 * @return multiples of {@code value} less than {@code max}
	 */
	public static Set<Integer> getMultiples(int value, int max) {
		return IntStream.iterate(value, i -> i + value)
				.limit(Math.floorDiv(max - 1, value))
				.boxed()
				.collect(Collectors.toSet());
	}

	/**
	 * Sums all multiples of the values, up until the maximum value specified.
	 * Takes into account numbers that may be multiples of several values. For
	 * example, 15 is a multiple of 3 and 5.
	 *
	 * @param values multiples to calculate
	 * @param max maximum value
	 * @return multiples of {@code values} less than {@code max}
	 */
	public static int sumMultiples(Collection<Integer> values, int max) {
		// collect all multiples into set
		var multiples = new TreeSet<Integer>();
		values.forEach(i -> multiples.addAll(getMultiples(i, max)));

		// sum values in set
		return multiples.parallelStream().mapToInt(i -> i.intValue()).sum();
	}

	/**
	 * Prints the sum of multiples less than a maximum value to the console. All
	 * values must be provided via command-line parameters.
	 *
	 * @param args the first value specifies the maximum and all following values
	 *   specify the multiples
	 */
	public static void main(String[] args) {
		int max = 0;
		List<Integer> values = new ArrayList<>();

		try {
			max = Integer.parseInt(args[0]);

			if (max < 0) {
				throw new NumberFormatException("Integer value must be non-negative.");
			}

			for (int i = 1; i < args.length; i++) {
				int value = Integer.parseInt(args[i]);

				if (value < 0) {
					throw new NumberFormatException("Integer value must be non-negative.");
				}

				values.add(value);
			}

			int result = sumMultiples(values, max);
			String format = "The sum of multiples of %s less than %d is %d.";
			System.out.printf(format, values.toString(), max, result);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("At least two values must be provided.");
		}
		catch (NumberFormatException e) {
			System.err.println("All values must be non-negative integers.");
		}
	}

	/*
	 * This example is the same as before, except this time showing off the new
	 * features in Java. We will slowly introduce these features throughout the
	 * semester.
	 */
}
