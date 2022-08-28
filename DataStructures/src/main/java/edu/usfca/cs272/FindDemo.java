package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class demonstrates how to find all elements that start with a specific
 * prefix using a set or a list.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class FindDemo {
	/** An array of animals used for demonstration purposes. */
	public static final String[] animals = new String[] {
			"cow", "ape", "dog", "bee", "boa", "cat", "ant", "bat" };

	/** An array of prefixes that we want to find matches for. */
	public static final String[] prefixes = new String[] { "b", "be", "d", "e" };

	/**
	 * This example demonstrates an inefficient way to deal with an unsorted
	 * array. Note how many iterations we must make!
	 */
	public static void inefficientDemo() {
		int iterations = 0;

		for (String prefix : prefixes) {
			System.out.print(prefix.toUpperCase() + ": ");

			for (String word : animals) {
				iterations++;

				if (word.startsWith(prefix)) {
					System.out.print(word + " ");
				}
			}

			System.out.println();
		}

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	/**
	 * This example demonstrates how to deal with an unsorted list. First, we sort
	 * the list and then use binary search to find the words.
	 *
	 * Note: See the {@link Arrays} class for {@link Arrays#sort(Object[])} and
	 * {@link Arrays#binarySearch(Object[], Object)} methods too. You do not need
	 * to convert to a list to do this.
	 */
	public static void listDemo() {
		/*
		 * Make sure to use the helper methods in Collections. It is faster to use
		 * Collections.addAll() to add elements to a list. And, there is an
		 * efficient sort implementation already created for you!
		 */
		ArrayList<String> list = new ArrayList<String>();
		Collections.addAll(list, animals);
		Collections.sort(list);

		int iterations = 0;

		for (String prefix : prefixes) {
			System.out.print(prefix.toUpperCase() + ": ");

			/*
			 * Binary search is an efficient search implementation for a sorted list.
			 * If the element is found, index will be the first location of the
			 * element in the list.
			 */
			int index = Collections.binarySearch(list, prefix);

			/*
			 * What happens if the element is not found? In our case, we are looking
			 * for prefixes that do not exist in our list.
			 *
			 * The binary search method will return a negative value to indicate the
			 * element was not found. However, this negative value is informative. It
			 * tells us where this element would exist if it were in the list. For
			 * example, if we wanted to insert the prefix "a" into our sorted list, it
			 * should be at the front.
			 *
			 * This "insertion point" will tell us where to start. We do have to do
			 * some conversion since the value is negative, which is explained in the
			 * API.
			 */
			if (index < 0) {
				index = -(index + 1);
			}

			// Lets start with a traditional for loop. We'll demonstrate a for-each
			// loop later in this example.
			for (int i = index; i < list.size(); i++) {
				// Count this towards our total iterations
				iterations++;

				// Again, make sure to stop in the appropriate location
				if (!list.get(i).startsWith(prefix)) {
					break;
				}

				System.out.print(list.get(i) + " ");
			}

			System.out.println();
		}

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	/**
	 * This example demonstrates how to efficiently find all the words in a sorted
	 * set that start with the specified prefix. For example, how to find all of
	 * the words in animals that starts with the prefix "a".
	 */
	public static void tailsetDemo() {
		/*
		 * We need a sorted set for this example. We use Collections.addAll() to add
		 * all of the animals to a set. This is more efficient than adding elements
		 * one at a time.
		 */
		TreeSet<String> set = new TreeSet<String>();
		Collections.addAll(set, animals);

		int iterations = 0;

		// Uses an enhanced for loop to iterate through prefixes
		for (String prefix : prefixes) {
			System.out.print(prefix.toUpperCase() + ": ");

			/*
			 * We will use the tailSet() method to generate a reference to our set
			 * that starts in the correct place and then uses an enhanced for loop to
			 * continue iteration.
			 */
			for (String animal : set.tailSet(prefix)) {
				// Count this towards our total iterations
				iterations++;

				/*
				 * Just because we started in the right place, does not mean we will end
				 * in the right place. Once we no longer have a word that starts with
				 * our prefix, we can quit. For example, if we are looking for words
				 * that start with "a" and we encounter the word "bat", we know that we
				 * have seen all of the words that start with "a" because the set is
				 * sorted.
				 */
				if (!animal.startsWith(prefix)) {
					break;
				}

				System.out.print(animal + " ");
			}

			System.out.println();
		}

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	/**
	 * This example uses a traditional for loop, which allows us to move the check
	 * if the word starts with our prefix into the for loop condition. This
	 * eliminates the need for the break.
	 */
	public static void setDemoTraditionalFor() {
		TreeSet<String> set = new TreeSet<String>();
		Collections.addAll(set, animals);

		int iterations = 0;

		// Uses an enhanced for loop to iterate through prefixes
		for (String prefix : prefixes) {
			System.out.print(prefix.toUpperCase() + ": ");

			for (String animal = set.ceiling(prefix); animal != null
					&& animal.startsWith(prefix); animal = set.higher(animal)) {

				// Count this towards our total iterations
				iterations++;

				System.out.print(animal + " ");
			}

			System.out.println();
		}

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	/**
	 * This uses a stream, but the inefficient approach of checking every element.
	 * However, we can easily make it parallel.
	 */
	public static void streamApproach() {
		// Uses an enhanced for loop to iterate through prefixes
		for (String prefix : prefixes) {

			System.out.print(prefix.toUpperCase() + ": ");

			Set<String> words = Stream.of(animals)
					// .parallel();
					.filter(w -> w.startsWith(prefix)).collect(Collectors.toSet());

			for (String animal : words) {
				System.out.print(animal + " ");
			}

			System.out.println();
		}

		System.out.println();
	}

	/**
	 * Shows the output of the examples above.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		inefficientDemo();
		listDemo();
		tailsetDemo();
		setDemoTraditionalFor();
		streamApproach();
	}
}
