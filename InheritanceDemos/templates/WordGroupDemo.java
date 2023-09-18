package edu.usfca.cs272;

import java.util.Arrays;

public class WordGroupDemo {
	public static void main(String[] args) {
		String[] words = { "ant", "anteater", "antelope", "bat", "badger", "cat", "cattle", "dog", "dragonfly" };
		System.out.println(Arrays.toString(words));

//		WordPrefix prefixes = new WordPrefix(2);
//		prefixes.addWords(words);
//		printData(prefixes);

//		System.out.println();

//		WordLength lengths = new WordLength();
//		lengths.addWords(words);
//		printData(lengths);
	}

	public static void printData(WordGroup groups) { // TODO Fix method
		System.out.println(groups.numGroups() + " groups:");
		for (Object group : groups.viewGroups()) {
			System.out.print(group.toString() + " -> ");
			System.out.println(groups.viewWords(group));
		}
	}
}
