package edu.usfca.cs272;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Approximates the runtime differences between the sequential and concurrent
 * approaches. Note: Benchmarking is difficult in Java. For more sophisticated
 * benchmarking, look into benchmarking extensions or third-party libraries.
 *
 * @see CharacterFinder
 * @see CharacterCompare
 * @see CharacterBenchmark
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class CharacterBenchmark {
	/**
	 * Demonstrates this class
	 *
	 * @param args unused
	 * @throws Exception if anything goes wrong
	 */
	public static void main(String[] args) throws Exception {
		// The benchmark can give different results depending on these numbers...
		int warmup = 10;
		int runs = 20;

		Path file1 = Path.of("src", "main", "resources", "pg1661.txt");
		Path file2 = Path.of("src", "main", "resources", "pg2701.txt");

		Instant start;
		Duration elapsed;

		double single = Double.MAX_VALUE;
		double multiple = Double.MAX_VALUE;

		int result = 0;

		// Make sure you DISABLE LOGGING before running this benchmark!
		Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);

		System.out.print("Warmup rounds");
		for (int i = 0; i < warmup; i++) {
			System.out.print(".");
			result = Math.max(result, CharacterCompare.compareConcurrently(file1, file2, 'c'));
			result = Math.max(result, CharacterCompare.compareSequentially(file1, file2, 'c'));
		}

		System.out.println(" done.");
		System.out.println();

		System.out.println("Concurrent\tSequential");
		System.out.println("----------\t----------");

		for (int i = 0; i < runs; i++) {
			start = Instant.now();
			result = Math.max(result, CharacterCompare.compareConcurrently(file1, file2, 'c'));
			elapsed = Duration.between(start, Instant.now());
			multiple = Math.min(multiple, elapsed.toNanos());
			System.out.printf("  %.05fs\t", (double) elapsed.toNanos() / Duration.ofSeconds(1).toNanos());

			start = Instant.now();
			result = Math.max(result, CharacterCompare.compareSequentially(file1, file2, 'c'));
			elapsed = Duration.between(start, Instant.now());
			single = Math.min(single, elapsed.toNanos());
			System.out.printf("  %.05fs%n", (double) elapsed.toNanos() / Duration.ofSeconds(1).toNanos());
		}

		System.out.println();

		multiple /= Duration.ofSeconds(1).toNanos();
		System.out.printf("Took %.05f seconds minimum for concurrent comparison.%n", multiple);

		single /= Duration.ofSeconds(1).toNanos();
		System.out.printf("Took %.05f seconds minimum for sequential comparison.%n", single);

		double speedup = single / multiple;
		System.out.printf("Execution time speedup: x%.02f times faster%n", speedup);
	}
}
