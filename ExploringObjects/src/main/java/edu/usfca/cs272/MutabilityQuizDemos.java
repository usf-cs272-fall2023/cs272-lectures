package edu.usfca.cs272;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Class with code used by the mutability quizzes.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class MutabilityQuizDemos {
	/** Stores text. */
	private static String text;

	/**
	 * Concatenate "apple" to text.
	 *
	 * @param text value to modify
	 */
	public static void concatApple(String text) {
		text.concat("apple");
	}

	/**
	 * Concatenate "cone" to text.
	 */
	public static void concatCone() {
		text += "cone";
	}

	/**
	 * Append "apple" to text.
	 *
	 * @param text value to modify
	 */
	public static void appendApple(StringBuilder text) {
		text.append("apple");
	}

	/**
	 * Set to new "pinecone" object.
	 *
	 * @param text value to modify
	 */
	public static void newPinecone(StringBuilder text) {
		text = new StringBuilder("pinecone");
	}

	/**
	 * Runs code from the Mutability, Part 1 quiz.
	 */
	public static void mutability1Questions() {
		text = "pine";
		concatCone();
		System.out.println(text);

		String text1 = new String("pine");
		concatApple(text1);
		System.out.println(text1);

		StringBuilder text2 = new StringBuilder("pine");
		appendApple(text2);
		System.out.println(text2);

		StringBuilder text3 = new StringBuilder("pine");
		newPinecone(text3);
		System.out.println(text3);
	}

	/**
	 * Code from the Mutability, Part 2 quiz.
	 */
	public static void mutability2Questions() {
		int[] numbers1 = new int[] { 1, 2, 3 };
		int[] numbers2 = numbers1;
		numbers2[0] = 4;
		System.out.println(Arrays.toString(numbers1));
		System.out.println(Arrays.toString(numbers2));

		int value1 = 42;
		int value2 = value1;
		value2 = 24;
		System.out.println(value1);
		System.out.println(value2);

		String dragon1 = new String("dragon");
		String dragon2 = dragon1;
		dragon2 += "fly";
		System.out.println(dragon1);
		System.out.println(dragon2);

		StringBuilder dragon3 = new StringBuilder("dragon");
		StringBuilder dragon4 = new StringBuilder(dragon3);
		dragon4.append("fly");
		System.out.println(dragon3);
		System.out.println(dragon4);

		TreeSet<String> set1 = new TreeSet<>();
		set1.add("ant");

		TreeSet<String> set2 = set1;
		set2.add("bat");
		set2.add("cat");

		System.out.println(set1);
		System.out.println(set2);
	}

	/**
	 * Runs all of the quiz question methods. Comment out lines based on which
	 * quizzes you are working on.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		mutability1Questions();
		mutability2Questions();
	}
}
