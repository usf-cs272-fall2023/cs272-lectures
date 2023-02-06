package edu.usfca.cs272;

import java.util.Set;
import java.util.TreeSet;

public class RemoveDemo {
	private static final Set<String> words = Set.of("ant", "bee", "cow", "dog", "elk");

	public static void traditionalFor() {
		TreeSet<String> copy = new TreeSet<>(words);

		// TODO
//		System.out.printf("size = %d, i = %d, element = %s, set = %s%n", copy.size(), i, first, copy);

		System.out.println("after loop: " + copy);
	}

	public static void enhancedFor() {
		TreeSet<String> copy = new TreeSet<>(words);

		try {
			// TODO
//			System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("after loop: " + copy);
	}

	public static void whileLoop() {
		TreeSet<String> copy = new TreeSet<>(words);

		// TODO
//		System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);

		System.out.println("after loop: " + copy);
	}

	public static void usingClear() {
		TreeSet<String> copy = new TreeSet<>(words);

		// TODO

		System.out.println("after loop: " + copy);
	}

	public static void main(String[] args) {
		System.out.println("Original: " + words);

//		System.out.println();
//		System.out.println("Traditional For Loop:");
//		traditionalFor();

//		System.out.println();
//		System.out.println("Enhanced For Loop:");
//		enhancedFor();

//		System.out.println();
//		System.out.println("While Loop:");
//		whileLoop();

//		System.out.println();
//		System.out.println("Using Clear:");
//		usingClear();
	}
}
