package edu.usfca.cs272;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demonstrates what happens to the identity hashcodes when attempting to modify
 * an immutable {@link String} object versus a mutable {@link StringBuilder}
 * object. In particular, shows why String concatenation performs poorly for
 * large files.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class ConcatenationDemo {
	/**
	 * Opens the sample text file and concatenates or appends each line to a
	 * immutable or mutable object.
	 *
	 * @param args unused
	 * @throws IOException if unable to read file
	 */
	public static void main(String[] args) throws IOException {
		// sample test file
		Path path = Path.of("src", "main", "resources", "lines.txt");

		// read the whole test file (not focus of example)
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

//		lines.forEach(System.out::println);
//		System.out.println();

		// store concatenated text from file in immutable vs mutable object
		String string = "";
		StringBuilder builder = new StringBuilder();

		// stores hashcodes
		Set<Integer> strings = new HashSet<>();
		Set<Integer> builders = new HashSet<>();

		// tracks number of characters copied
		int copies = 0;

		// produces console tabular output
		String format = "%-10s  x%08X  %3d  x%08x  %3d%n";
		String summary = "%10s   %8s  %3d   %8s  %3d%n";
		System.out.println("LINE        STRING       #  BUILDER      #");
		System.out.println("----------  --------------  --------------");

		for (String line : lines) {
			string += line; // creates new object (new hashcode)
			builder.append(line); // does not create new object (same hashcode)

			// keep track of unique hashcodes
			strings.add(System.identityHashCode(string));
			builders.add(System.identityHashCode(builder));

			// keep track of number of copied characters
			copies += string.length();

			// lets check what happened
			System.out.printf(format, line,
					System.identityHashCode(string), string.length(),
					System.identityHashCode(builder), builder.length());

//			break; // stop after first iteration to understand output
		}

		System.out.println("----------  --------------  --------------");
		System.out.printf(summary, "COPIED", "", copies, "", builder.length());
		System.out.printf(summary, "OBJECTS", "", strings.size(), "", builders.size());

		/*
		 * Every time you see the hashcode change, a new String object is being
		 * created in memory with room for the old text plus the new line. As the
		 * file is read, this causes larger and larger copies.
		 *
		 * With the size of text files used for the project, using the String
		 * concatenation code will result in significantly slower code!
		 */
	}
}
