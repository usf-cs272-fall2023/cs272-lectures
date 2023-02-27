package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Demonstrates streams.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class StreamDemo {
	/**
	 * Prints an element followed by a space.
	 *
	 * @param element the element to print
	 *
	 * @see System#out
	 * @see PrintStream#print
	 */
	public static void print(String element) {
		System.out.print(element);
		System.out.print(" ");
	}

	/**
	 * Removes punctuation from the provided text.
	 *
	 * @param text to remove punctuation
	 * @return text without punctuation
	 */
	public static String removePunctuation(String text) {
		return text.replaceAll("(?U)\\p{Punct}+", "");
	}

	/**
	 * Demonstrates streams.
	 *
	 * @param args unused
	 * @throws IOException if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		// https://en.wikipedia.org/wiki/List_of_computing_and_IT_abbreviations
		// API - Application Program Interface
		// BLOB - Binary Large OBject
		// CPU - Central Processing Unit
		// DBMS - DataBase Management System
		// EOF - End Of File
		// FIFO - First In First Out
		// GUI - Graphical User Interface
		// HDD - HardDisk Drive
		// IDE - Integrated Development Environment
		// JSON - JavaScript Object Notation
		// OOP - Object Oriented Programming
		// URI - Uniform Resource Identifier
		// WYSIWYG - What You See Is What You Get

		ArrayList<String> acronyms = new ArrayList<>();
		Collections.addAll(acronyms, "IDE", "CPU", "URI", "HDD", "GUI", "API", "OOP", "EOF");
		Collections.addAll(acronyms, "DBMS", "BLOB", "FIFO", "JSON");
		Collections.addAll(acronyms, "WYSIWYG");

		int i = 0;

		// use a method reference to print on one line
		// (this is not a stream)
		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// get a steam with the acronyms as its data source
		Stream<String> stream = acronyms.stream();

		// demonstrate that the stream is not modifying its source
		System.out.print(++i + ": ");
		stream.sorted().forEach(StreamDemo::print);
		System.out.println();

		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// demonstrate that collection operations do modify its source
		// demonstrate that the collection can be reused
		acronyms.sort(null);

		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// demonstrate that the stream cannot be reused
		try {
			System.out.print(++i + ": ");
			stream.sorted().forEach(StreamDemo::print);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// demonstrate lazy versus eager
		// find the first three-letter acronym that has no vowels

		Pattern regex = Pattern.compile("[^AIEOU]+");

		// using collections
		System.out.print(++i + ": ");
		for (String acronym : acronyms) {
			print(acronym);
			if (acronym.length() == 3) {
				print(acronym);
				if (regex.matcher(acronym).matches()) {
					print(acronym);
					break;
				}
			}
		}
		System.out.println();

		// using streams

		/*
		 * peek(...) is used to debug streams, and normally not included in a
		 * pipeline
		 */

		System.out.print(++i + ": ");

		// re-create stream (nothing happens here, no terminal)
		stream = acronyms.stream().peek(StreamDemo::print);

		// add more operations to pipeline (can chain together)
		stream = stream.filter(s -> s.length() == 3).peek(StreamDemo::print);
		stream = stream.filter(s -> regex.matcher(s).matches()).peek(StreamDemo::print);

		// trigger pipeline with terminal operation
		stream.findFirst();
		System.out.println();

		/*
		 * In both cases, the same output is produced. The second filter is not
		 * executed unless necessary, and findFirst is going to terminate the stream
		 * in the same way our break terminated the for loop.
		 */

		// demonstrate other data sources
		System.out.print(++i + ": ");
		Path sally = Path.of("src", "main", "resources", "sally.txt");
		try (Stream<String> lines = Files.newBufferedReader(sally).lines()) {
			lines.flatMap(line -> Stream.of(line.split("\\s+")))
					.map(StreamDemo::removePunctuation)
					.map(String::toLowerCase)
					.filter(word -> word.startsWith("s"))
					.forEach(StreamDemo::print);
		}
	}
}
