package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class CollectionDemo {
	public static void parseLine(String line) {
		ArrayList<String> wordList = new ArrayList<>();
		HashSet<String> wordHashSet = new HashSet<>();
		TreeSet<String> wordTreeSet = new TreeSet<>();

		// TODO

		// Print out results in a consistent format
		String format = "%-9s : %2d items : %s\n";
		System.out.printf("\n");
		System.out.printf(format, "ArrayList", wordList.size(), wordList);
		System.out.printf(format, "HashSet", wordHashSet.size(), wordHashSet);
		System.out.printf(format, "TreeSet", wordTreeSet.size(), wordTreeSet);

		System.out.println();
	}

	public static void main(String[] args) {
		String test1 = "the old man the boat";
		String test2 = "rose rose to put rose roes on her rows of roses";
		String test3 = "time flies like an arrow fruit flies like an apple";

		parseLine(test1);
		parseLine(test2);
		parseLine(test3);
	}
}
