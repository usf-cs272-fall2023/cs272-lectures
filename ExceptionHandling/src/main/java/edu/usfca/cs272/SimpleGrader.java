package edu.usfca.cs272;

import java.util.Scanner;

/**
 * Demonstrates different strategies for dealing with exceptions.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class SimpleGrader {
	/**
	 * A simple method to calculate the percentage given the total points earned
	 * versus the total points possible.
	 *
	 * @param earned points earned
	 * @param possible points possible
	 * @return percentage
	 */
	public static int calcPercentage(int earned, int possible) {
		return 100 * earned / possible;
	}

	/**
	 * A simple helper method for outputting the results in a nice format. Not the
	 * focus of this example.
	 *
	 * @param earned points earned
	 * @param possible points possible
	 * @param percentage the calculated percentage
	 */
	public static void printResult(int earned, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", earned, possible, percentage);
	}

	/**
	 * Demonstrates the code in this class.
	 *
	 * @param args (unused)
	 */
	@SuppressWarnings("resource") // for demonstration purposes only
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter total points earned: ");
		int earned = scanner.nextInt();

		System.out.print("Enter total points possible: ");
		int possible = scanner.nextInt();

		int percentage = calcPercentage(earned, possible);
		printResult(earned, possible, percentage);

		scanner.close();
		System.out.println("[done]");
	}

	/*
	 * What can go wrong?
	 *
	 * - InputMismatchException if non-integer input
	 *
	 * - ArithmeticException if possible is 0
	 *
	 * - Negative grades if any number is negative
	 *
	 * All of the above exceptions can get thrown even though main() does not
	 * explicitly throw any exceptions. That is because those are RuntimeException
	 * types that do not have to be explicitly caught and handled.
	 *
	 * Furthermore, the following can also go wrong:
	 *
	 * - NoSuchElementException if add before create Scanner: System.in.close();
	 *
	 * - IllegalStateException if add bug after scanner.close():
	 * System.out.println(scanner.nextLine());
	 *
	 * Generally, we want reusable methods to throw exceptions. This allows other
	 * programmers to decide how to react, making the code more generalized.
	 *
	 * However, a user-friendly main() method should not throw exceptions. That
	 * results in a stack trace, which is only useful for the developer while
	 * debugging (not the user ).
	 */
}
