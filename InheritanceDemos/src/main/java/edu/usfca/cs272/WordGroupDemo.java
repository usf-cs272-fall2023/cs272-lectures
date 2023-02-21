package edu.usfca.cs272;

/**
 * Briefly demonstrates how to use the {@link WordGroup} interface.
 *
 * @see WordGroup
 * @see WordPrefix
 * @see WordLength
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class WordGroupDemo {
	/**
	 * Demonstrates the {@link WordGroup} example.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String[] words = { "ant", "anteater", "antelope", "bat", "badger", "cat", "cattle", "dog", "dragonfly" };

		WordPrefix prefixes = new WordPrefix(2);
		prefixes.addWords(words);
		printData(prefixes);

		System.out.println();

		WordLength lengths = new WordLength();
		lengths.addWords(words);
		printData(lengths);
	}

	/**
	 * Prints the groups and words within those groups.
	 *
	 * @param <K> the type of word group
	 * @param groups the word group instance
	 */
	public static <K> void printData(WordGroup<K> groups) {
		System.out.println(groups.numGroups() + " groups:");
		for (K group : groups.viewGroups()) {
			System.out.print(group.toString() + " -> ");
			System.out.println(groups.viewWords(group));
		}
	}
}
