package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demonstrates how to implement a text parser using streams.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class TextFileStreamer {
	/*
	 * An initial approach trying to implement text parsing using streams instead.
	 * However, when we use this to add to a collection defined outside our
	 * stream, we are creating code with side-effects!
	 */

	/**
	 * Streams through a text file at the specified path, using the tokenize
	 * function to split lines into tokens, applies the clean method to each
	 * token, and then uses the consumer to collect the results.
	 *
	 * @param path the path to read
	 * @param clean the function to apply to each token
	 * @param tokenize the function to split lines into tokens
	 * @param consumer the consumer to collect the results
	 * @throws IOException if an I/O error occurs
	 */
	public static void consumeTextFile(Path path, Function<String, String> clean,
			Function<String, String[]> tokenize, Consumer<String> consumer) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				Stream<String> lines = reader.lines();
		) {
			lines.map(clean) // string --> string
					.flatMap(line -> Stream.of(tokenize.apply(line))) // string --> array of strings
					.forEach(consumer);
		}
	}

	/**
	 * Splits each line at the specified path by whitespace, applies the provided
	 * clean method to each token, and returns the result as a list.
	 *
	 * @param path the path to read
	 * @param clean the function to apply to each token
	 * @return list of split and cleaned words
	 * @throws IOException if an I/O error occurs
	 */
	public static List<String> consumeTextAsList(Path path,
			Function<String, String> clean) throws IOException {
		List<String> words = new ArrayList<String>();
		consumeTextFile(path, clean, s -> s.split("\\s+"), words::add);
		return words;
	}

	/*
	 * A better approach that has no dangerous side-effects.
	 */

	/**
	 * Streams through a text file at the specified path, using the tokenize
	 * function to split lines into tokens, applies the clean method to each
	 * token, and then uses the consumer to collect the results.
	 *
	 * @param <C> the type of {@link Collection} to return
	 * @param path the path to read
	 * @param clean the function to apply to each token
	 * @param tokenize the function to split lines into tokens
	 * @param collector the function to collect the results
	 * @return the collection of results
	 * @throws IOException if an I/O error occurs
	 */
	public static <C extends Collection<String>> C collectTextFile(Path path,
			Function<String, String> clean, Function<String, String[]> tokenize,
			Supplier<C> collector) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				Stream<String> lines = reader.lines();
		) {
			return lines.map(clean)
					.flatMap(line -> Stream.of(tokenize.apply(line)))
					.collect(Collectors.toCollection(collector));
		}
	}

	/**
	 * Splits each line at the specified path by whitespace, applies the provided
	 * clean method to each token, and returns the result as a list.
	 *
	 * @param path the path to read
	 * @param clean the function to apply to each token
	 * @return list of split and cleaned words
	 * @throws IOException if an I/O error occurs
	 */
	public static List<String> collectTextAsList(Path path,
			Function<String, String> clean) throws IOException {
		return collectTextFile(path, clean, s -> s.split("\\s+"), ArrayList::new);
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 * @throws IOException if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		Path sally = Path.of("src", "main", "resources", "sally.txt");

		Function<String, String> clean = s -> s.toLowerCase().replaceAll("[^A-z\\s]+", " ");
		System.out.println(consumeTextAsList(sally, clean));
		System.out.println(collectTextAsList(sally, clean));

		Function<String, String[]> tokenize = (String s) -> s.split("\\s+");
		TreeSet<String> set = collectTextFile(sally, clean, tokenize, TreeSet<String>::new);
		System.out.println(set);
	}

	/*
	 * Have we gone too far? This does demonstrate how we could use streams and
	 * lambdas to continue and generalize our approach to text parsing, but
	 * honestly the core operations in streamTextFile are compact and simple
	 * enough that you really could just recreate and customize it as necessary.
	 */
}
