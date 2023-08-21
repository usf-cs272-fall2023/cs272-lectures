package edu.usfca.cs272;

import java.util.Scanner;

/**
 * Demonstrates different strategies for dealing with exceptions.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class SimpleGraderCaught {
	/**
	 * A simple method to calculate the percentage given the total points earned
	 * versus the total points possible.
	 *
	 * Note we explicitly declare the thrown exceptions, which helps make sure other
	 * developers are aware they can be thrown.
	 *
	 * @param earned points earned
	 * @param possible points possible
	 * @return percentage
	 *
	 * @throws ArithmeticException if possible is 0
	 * @throws IllegalArgumentException if earned or possible is negative
	 */
	public static int calcPercentage(int earned, int possible) throws ArithmeticException, IllegalArgumentException {
		if (earned < 0 || possible <= 0) {
			throw new IllegalArgumentException("Values must be non-negative.");
		}

		return 100 * earned / possible;
	}

	/**
	 * Demonstrates the code in this class.
	 *
	 * @param args (unused)
	 */
	@SuppressWarnings("resource") // for demonstration purposes only
	public static void main(String[] args) {
		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);

			System.out.print("Enter total points earned: ");
			int earned = scanner.nextInt();

			System.out.print("Enter total points possible: ");
			int possible = scanner.nextInt();

			int percentage = calcPercentage(earned, possible); // New version
			SimpleGrader.printResult(earned, possible, percentage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			scanner.close();
		}

		// Bug will still throw uncaught IllegalStateException...
		System.out.println(scanner.nextLine());

		System.out.println("[done]");
	}

	/*
	 * This is better... the code inside the try block is back to the easy-to- read
	 * and understand version. However, the output when things go wrong is the same,
	 * we will have to make sure the close the scanner, and we still have a bug that
	 * can throw an exception.
	 */
}
