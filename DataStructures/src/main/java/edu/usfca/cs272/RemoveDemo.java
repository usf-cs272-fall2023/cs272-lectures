package edu.usfca.cs272;

import java.util.ConcurrentModificationException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Demonstrates that modifying a collection while iterating through that
 * collection is a bad idea.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class RemoveDemo {
	/** Example used to demonstrate remove methods. */
	private static final Set<String> words = Set.of("ant", "bee", "cow", "dog", "elk");

	/**
	 * Uses a traditional for loop with {@link TreeSet#pollFirst()} within the loop.
	 * Notice that we are missing dog and elk in the loop output, and that they are
	 * not removed from the set!
	 */
	public static void traditionalFor() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		for (int i = 0; i < copy.size(); i++) {
			// retrieves and **removes** first element
			String first = copy.pollFirst();

			System.out.printf("size = %d, i = %d, element = %s, set = %s%n", copy.size(), i, first, copy);
		}

		System.out.println("after loop: " + copy);
	}

	/**
	 * Uses an enhanced for loop with {@link TreeSet#pollFirst()} within the loop.
	 * Notice how we get a {@link ConcurrentModificationException} thrown on the
	 * first iteration!
	 */
	public static void enhancedFor() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		try {
			for (String word : copy) {
				// retrieves and **removes** first element
				String first = copy.pollFirst();

				System.out.printf("size = %d, element = %s, set = %s (word = %s)%n", copy.size(), first, copy, word);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("after loop: " + copy);
	}

	/**
	 * Uses a while loop with {@link TreeSet#pollFirst()} within the loop. This
	 * example works properly for removing elements. Other operations, especially
	 * those that add elements, may require other strategies.
	 */
	public static void whileLoop() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		while (copy.size() > 0) {
			// retrieves and **removes** first element
			String first = copy.pollFirst();

			System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);
		}

		System.out.println("after loop: " + copy);
	}

	/**
	 * Of course, if we really wanted to remove ALL the elements, there are much
	 * simpler ways for us to proceed.
	 */
	public static void usingClear() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);
		copy.clear();
		System.out.println("after loop: " + copy);
	}

	/**
	 * Shows the output of the examples above.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		System.out.println("Original: " + words);

		System.out.println();
		System.out.println("Traditional For Loop:");
		traditionalFor();

		System.out.println();
		System.out.println("Enhanced For Loop:");
		enhancedFor();

		System.out.println();
		System.out.println("While Loop:");
		whileLoop();

		System.out.println();
		System.out.println("Using Clear:");
		usingClear();
	}
}
