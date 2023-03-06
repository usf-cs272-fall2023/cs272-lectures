package edu.usfca.cs272;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class CharacterBenchmark {
	public static void main(String[] args) throws Exception {
		int warmup = 20;
		int runs = 20;

		Path file1 = Path.of("src", "main", "resources", "pg1661.txt");
		Path file2 = Path.of("src", "main", "resources", "pg2701.txt");

		Instant start;
		Duration elapsed;

		double single = 0;
		double multiple = 0;

		int result = 0;

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
			multiple += elapsed.toNanos();
			System.out.printf("  %.05fs\t", (double) elapsed.toNanos() / Duration.ofSeconds(1).toNanos());

			start = Instant.now();
			result = Math.max(result, CharacterCompare.compareSequentially(file1, file2, 'c'));
			elapsed = Duration.between(start, Instant.now());
			single += elapsed.toNanos();
			System.out.printf("  %.05fs%n", (double) elapsed.toNanos() / Duration.ofSeconds(1).toNanos());
		}

		System.out.println();

		multiple /= runs;
		multiple /= Duration.ofSeconds(1).toNanos();
		System.out.printf("Took %.05f seconds average for concurrent comparison.%n", multiple);

		single /= runs;
		single /= Duration.ofSeconds(1).toNanos();

		System.out.printf("Took %.05f seconds average for sequential comparison.%n", single);

		double speedup = single / multiple;
		System.out.printf("Execution time speedup: x%.02f times faster%n", speedup);
	}
}
