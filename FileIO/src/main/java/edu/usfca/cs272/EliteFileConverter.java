package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a file to a simple version of 1337-speak. The history behind this
 * "alternative" alphabet is as follows:
 *
 * <blockquote> One theory is that it was developed to defeat text filters
 * created by BBS or Internet Relay Chat system operators for message boards to
 * discourage the discussion of forbidden topics, like cracking and hacking.
 * Creative misspellings and ASCII-art-derived words were also a way to attempt
 * to indicate one was knowledgeable about the culture of computer users.
 * </blockquote>
 *
 * Demonstrates simple for loops, switch statements, method overloading, the
 * ternary operator, and line-by-line file reading and writing. Does not cover
 * exception handling in depth!
 *
 * @see <a href="https://en.wikipedia.org/wiki/Leet">Leet - Wikipedia</a>
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class EliteFileConverter {
	/**
	 * Converts a letter to its 1337 representation, or randomizes the letter
	 * capitalization. Uses a {@code switch} statement to demonstrate how the
	 * {@code case} keyword works.
	 *
	 * @param letter letter to convert
	 * @return letter converted to 1337-speak
	 */
	public static char toLeet(char letter) {
		boolean random = Math.random() < 0.5;

		// switch expressions are now possible! see:
		// https://docs.oracle.com/en/java/javase/15/language/switch-expressions.html
		return switch (letter) {
			// ternary (3-way) operator
			// condition ? trueValue : falseValue
			// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html
			case 'a', 'A' -> random ? '4' : '@';
			case 'b', 'B' -> '8';
			case 'e', 'E' -> '3';
			case 'i', 'I' -> '!';
			case 'l', 'L' -> '1';
			case 'o', 'O' -> '0';
			case 's', 'S' -> random ? '5' : '$';
			case 't', 'T' -> '7';
			default -> random ? Character.toLowerCase(letter) : Character.toUpperCase(letter);
		};
	}

	/**
	 * Randomly converts certain characters to a simple version of 1337-speak. The
	 * provided threshold will determine the percentage of letters that will attempt
	 * to be converted.
	 *
	 * @param text the text to convert
	 * @param threshold the percentage of time letters should be converted (should
	 *   be a value between 0 and 1)
	 *
	 * @return the converted text
	 *
	 * @see #toLeet(char)
	 * @see #toLeet(String)
	 * @see #toLeet(String, double)
	 */
	public static String toLeet(String text, double threshold) {
		char[] chars = text.toCharArray();

		// traditional for loop
		// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html
		for (int i = 0; i < chars.length; i++) {
			boolean random = Math.random() < threshold;
			chars[i] = random ? toLeet(chars[i]) : chars[i];
		}

		return String.valueOf(chars);
	}

	/*
	 * Why no exception handling above if threshold is invalid? You could make an
	 * argument it should be included. However, nothing really breaks if the value
	 * is invalid. If it is negative, this will NEVER convert letters. If it is
	 * greater than 1, it will ALWAYS convert letters. Valid values can also cause
	 * that same behavior. Since the behavior isn't invalid even though the
	 * parameter value is, I have chosen NOT to throw an exception here.
	 */

	/**
	 * Randomly converts certain characters to a simple version of 1337-speak. Same
	 * as {@link #toLeet(String, double)} with a threshold of 0.5.
	 *
	 * @param text the text to convert
	 * @return the converted text
	 *
	 * @see #toLeet(char)
	 * @see #toLeet(String)
	 * @see #toLeet(String, double)
	 */
	public static String toLeet(String text) {
		// this is an example convenience method
		// https://en.wikipedia.org/wiki/Convenience_function
		return toLeet(text, 0.5);
	}

	/**
	 * Demonstrates a simple, but memory-intensive way to convert a text file to
	 * 1337-speak.
	 *
	 * @param input path to the input file
	 * @param output path to the output file
	 * @throws IOException from {@link Files#readAllLines(Path)}
	 */
	public static void toLeetLists(Path input, Path output) throws IOException {
		// quote from api: "not intended for reading in large files"
		List<String> inputLines = Files.readAllLines(input, StandardCharsets.UTF_8);

		// creates another arraylist with same size
		List<String> outputLines = new ArrayList<String>(inputLines.size());

		// enhanced for loop (use these when possible)
		// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html
		for (String line : inputLines) {
			outputLines.add(toLeet(line));
		}

		Files.write(output, outputLines, StandardCharsets.UTF_8);
	}

	/*
	 * Why do we throw exceptions above? We want this method to be general enough to
	 * be used within other code. Throwing the exception here lets other developers
	 * using this method decide what to do if something goes wrong. This is common
	 * for methods that can run into IOException exception types.
	 */

	/**
	 * Demonstrates a better way to convert a text file to 1337-speak, making sure
	 * resources are closed and as little memory as possible is used. Does NOT
	 * perform its own exception handling!
	 *
	 * @param input the path to the input file
	 * @param output the path to the output file
	 * @throws IOException from {@link Files#readAllLines(Path)}
	 */
	public static void toLeetBuffered(Path input, Path output) throws IOException {
		// try-with-resources
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try (
				BufferedReader reader = Files.newBufferedReader(input, UTF_8);
				BufferedWriter writer = Files.newBufferedWriter(output, UTF_8);
		) {
			String line = null;

			// only 1 line needs to be "in memory" at a time
			// (realistically, an entire buffer of text is in memory at a time)
			while ((line = reader.readLine()) != null) {
				writer.write(toLeet(line));
				writer.newLine();
			}
		}

		// note: we can still throw exceptions (do not need to catch)
	}

	/**
	 * Demonstrates the {@link EliteFileConverter} class.
	 *
	 * @param args unused
	 * @throws IOException from {@link Files#readAllLines(Path)}
	 */
	public static void main(String[] args) throws IOException {
		// multi-line strings are new in Java 15+
		// see: https://docs.oracle.com/en/java/javase/15/text-blocks/index.html
		String text = """
				Sally sells sea shells
				at the sea shore.
				""";

		System.out.println(text);
		System.out.println(toLeet(text));
		System.out.println(toLeet(text, 0.25));
		System.out.println(toLeet(text, 1.00));

		String filename = EliteFileConverter.class.getSimpleName();
		Path base = Path.of("src", "main", "java", "edu", "usfca", "cs272");
		Path input = base.resolve(filename + ".java");
		Path output = Path.of("out", filename + ".txt");

		Files.createDirectories(output.getParent());
		toLeetBuffered(input, output);

		// throwing exceptions in main result in stack trace console output
		Path nowhere = Path.of("nowhere");
		toLeetBuffered(nowhere, nowhere);
	}

	/*
	 * It is in the main method above that we should really catch the IOException
	 * exceptions thrown in the code. We are just using this to demo the code for
	 * the moment, so we'll skip that step for now.
	 *
	 * In a more realistic setting, we might parse command-line arguments from the
	 * user to decide what to read from and write to, as well as decide what to do
	 * if that argument was invalid.
	 */
}
