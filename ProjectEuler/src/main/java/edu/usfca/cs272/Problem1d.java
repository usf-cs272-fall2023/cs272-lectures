package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Project Euler Problem 1 is stated as follows:
 *
 * <blockquote> If we list all the natural numbers below 10 that are multiples
 * of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23. Find the
 * sum of all the multiples of 3 or 5 below 1000. </blockquote>
 *
 * This example illustrates a more reusable and generalized approach, also
 * allowing the multiples to be specified by the user as well. This is just one
 * of many approaches, all with different space and time requirements.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class Problem1d {
	/**
	 * Returns all multiples of a value less than a maximum.
	 *
	 * @param value multiple to calculate
	 * @param max maximum value
	 *
	 * @return multiples of {@code value} less than {@code max}
	 */
	public static Set<Integer> getMultiples(int value, int max) {
		Set<Integer> multiples = new TreeSet<>();
		addMultiples(value, max, multiples);
		return multiples;
	}

	/**
	 * Adds all multiples of a value less than a maximum to the provided set of
	 * multiples. (This is an example of a method with a side-effect.)
	 *
	 * @param value multiple to calculate
	 * @param max maximum value
	 * @param multiples set to add multiples of {@code value} less than {@code max}
	 */
	public static void addMultiples(int value, int max, Set<Integer> multiples) {
		for (int i = value; i < max; i += value) {
			multiples.add(i);
		}
	}

	/**
	 * Sums all multiples of the values, up until the maximum value specified. Takes
	 * into account numbers that may be multiples of several values. For example, 15
	 * is a multiple of 3 and 5.
	 *
	 * @param values multiples to calculate
	 * @param max maximum value
	 * @return multiples of {@code values} less than {@code max}
	 */
	public static int sumMultiples(Collection<Integer> values, int max) {
		Set<Integer> multiples = new TreeSet<>();

		// add multiples to set to avoid duplicates
		for (int value : values) {
			// multiples.addAll(getMultiples(value, max));
			addMultiples(value, max, multiples);
		}

		// loop through unique values and add together
		int sum = 0;

		for (int value : multiples) {
			sum += value;
		}

		return sum;
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
	 * Now we can specify multiples too! But, we removed the ability for users to
	 * enter those from the console since that would take quite a bit of code to
	 * validate.
	 *
	 * So, you can see how much object-oriented design can increase the size of
	 * programs. Why in the world would we want to do this?
	 *
	 * It is only useful when (a) you have large and complex problems, and (b) there
	 * is ample opportunity to reuse your solutions to those problems. The more
	 * often you reuse your code, the more you get out of the object-oriented
	 * design. But, when have you EVER reused code so far?
	 *
	 * Guess what... you will now! Almost all of the classes you create in this
	 * class you will reuse across homework and project assignments. And, we have a
	 * problem that is complex and large enough to warrant object-oriented design: a
	 * search engine.
	 */
}
