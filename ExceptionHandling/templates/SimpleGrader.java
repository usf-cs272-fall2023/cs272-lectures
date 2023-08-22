package edu.usfca.cs272;

import java.util.Scanner;

public class SimpleGrader {
	public static int calcPercentage(int earned, int possible) {
		return 100 * earned / possible;
	}

	public static void printResult(int earned, int possible, int percentage) {
		System.out.printf("%d/%d = %d%% %n", earned, possible, percentage);
	}

	@SuppressWarnings("resource")
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
}
