package edu.usfca.cs272;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Demonstrates different strategies for dealing with exceptions.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class SimpleGraderResourceful {
	/**
	 * Demonstrates the code in this class.
	 *
	 * @param args (unused)
	 */
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in);) {
			System.out.print("Enter total points earned: ");
			int earned = scanner.nextInt();

			System.out.print("Enter total points possible: ");
			int possible = scanner.nextInt();

			int percentage = SimpleGraderCaught.calcPercentage(earned, possible);
			SimpleGrader.printResult(earned, possible, percentage);
		}
		catch (InputMismatchException e) {
			// Don't return or exit inside a catch unless you really need to.
			// There is only one statement outside of the try/catch, so we
			// can just fall through to the end of the method.
			System.err.println("Please enter integer values.");
		}
		catch (ArithmeticException | IllegalArgumentException e) {
			// Notice we can handle two types of exceptions at once, allowing
			// for more code reuse.
			System.err.println("Please enter non-negative values.");
		}
		catch (Exception e) {
			// This shouldn't ever happen, but then again, bugs happen.
			System.err.println(e.toString());
		}

		// Now this is a compile error...
		// System.out.println(scanner.nextLine());

		System.out.println("[done]");
	}

	/*
	 * Much better! Resources are handled for us. No need to declare anything
	 * outside of the try block, which helped catch our bug. And the console
	 * output to the user is much more user-friendly!
	 */
}
