package edu.usfca.cs272;

import java.util.Scanner;

/**
 * Demonstrates different strategies for dealing with exceptions.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class SimpleGraderValidated {
	/**
	 * Demonstrates the code in this class.
	 *
	 * Note: Avoid main methods that throw an exception to the user!
	 *
	 * @param args (unused)
	 */
	@SuppressWarnings("resource") // for demonstration purposes only
	public static void main(String[] args) {
		// Must pre-declare these so they are accessible outside the if blocks
		int earned = 0;
		int possible = 0;
		int percentage = 0;

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter total points earned: ");

		if (scanner.hasNextInt()) {
			earned = scanner.nextInt();
		}
		else {
			// Use return instead of System.exit(), which gives the calling
			// method a chance to react if necessary.
			System.err.println("Please enter integer values.");
			return;
		}

		System.out.print("Enter total points possible: ");

		if (scanner.hasNextInt()) {
			possible = scanner.nextInt();
		}
		else {
			System.err.println("Please enter integer values.");
			return;
		}

		// Hmmm, does this belong here? Where else could we test for this that would
		// be a bit more reusable moving forward?
		if (earned < 0 || possible <= 0) {
			System.err.println("Please enter non-negative values.");
			return;
		}

		percentage = SimpleGrader.calcPercentage(earned, possible);
		SimpleGrader.printResult(earned, possible, percentage);

		// Uh oh, what happens if we returned because one of the issues
		// earlier? Will the Scanner be closed properly?
		scanner.close();

		// Bug will still throw uncaught IllegalStateException too...
		System.out.println(scanner.nextLine());

		System.out.println("[done]");
	}
}
