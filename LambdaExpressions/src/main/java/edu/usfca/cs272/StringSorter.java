package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class created to demonstrate lambda functions (not yet streams).
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class StringSorter {
	// Should our inner class be a static nested class or not?

	/**
	 * Allows String comparison by length.
	 */
	public static class StringIncreasingLength implements Comparator<String> {
		@Override
		public int compare(String one, String two) {
			return Integer.compare(one.length(), two.length());
		}
	};

	/**
	 * Outputs list and label to console in easy to compare format.
	 *
	 * @param label the list label
	 * @param words the list of words to output
	 */
	public static void printList(String label, List<String> words) {
		System.out.printf("%9s: %s%n", label, words);
	}

	/**
	 * Demonstrates different ways of sorting.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// initialize the test case
		List<String> words = new ArrayList<>();

		Collections.addAll(words, "ant", "AARDVARK", "Bee", "capybara", "deer",
				"elk", "elephant", "FOX", "gecko", "hippopatamus");

		printList("Original", words);

		// demonstrate shuffle operation
		Collections.shuffle(words);
		printList("Shuffled", words);

		// sort via natural ordering and output
		Collections.shuffle(words);
		Collections.sort(words);
		printList("Natural", words);

		// sort using case-insensitive ordering
		// https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/lang/String.java#L2053
		Collections.shuffle(words);
		words.sort(String.CASE_INSENSITIVE_ORDER);
		printList("No Case", words);

		// create a custom comparator static nested class sorting by length
		Collections.shuffle(words);
		words.sort(new StringIncreasingLength());
		printList("Length", words);

		// create a custom comparator anonymous inner class sorting by length
		Comparator<String> increasingLength = new Comparator<String>() {
			@Override
			public int compare(String one, String two) {
				return Integer.compare(one.length(), two.length());
			}
		};

		Collections.shuffle(words);
		words.sort(increasingLength);
		printList("Length", words);

		// embed the same anonymous inner class definition within the sort method
		Collections.shuffle(words);

		words.sort(new Comparator<String>() {
			@Override
			public int compare(String one, String two) {
				return Integer.compare(one.length(), two.length());
			}
		});

		printList("Length", words);

		// convert the method to lambda expressions
		Collections.shuffle(words);
		words.sort((one, two) -> Integer.compare(one.length(), two.length()));
		printList("Length", words);

		// alternate form of conversion (using other lambda notation)
		Collections.shuffle(words);
		words.sort(Comparator.comparingInt(String::length));
		printList("Length", words);

		// reversed form of conversion
		Collections.shuffle(words);
		words.sort(Comparator.comparingInt(String::length).reversed());
		printList("Reversed", words);

		// sort by length and then case-insensitive sort by text
		Collections.shuffle(words);

		words.sort(new Comparator<String>() {
			@Override
			public int compare(String one, String two) {
				int same = Integer.compare(one.length(), two.length());
				return same != 0 ? same : String.CASE_INSENSITIVE_ORDER.compare(one, two);
			}
		});

		printList("Combined", words);

		// converted to lambda function
		Collections.shuffle(words);

		words.sort((one, two) -> {
			int same = Integer.compare(one.length(), two.length());
			return same != 0 ? same : String.CASE_INSENSITIVE_ORDER.compare(one, two);
		});

		printList("Combined", words);

		// alternate form of conversion
		Collections.shuffle(words);

		words.sort(Comparator.comparingInt(String::length)
				.thenComparing(String.CASE_INSENSITIVE_ORDER));

		printList("Combined", words);
	}
}
