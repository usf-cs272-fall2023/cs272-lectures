package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Demonstrates the impact parallel streams can make on runtime. Please note
 * that benchmarking in Java requires considerable care. You should use
 * something like JMH to perform consistent microbenchmarks.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class ParallelStreamBenchmark {
	/** Random number generator for this benchmark. */
	public static Random random = new Random(System.nanoTime());

	/** Number of warmup runs before benchmarking. */
	public static final int warmup = 15;

	/** Number of timed runs to benchmark. */
	public static final int timed = 15;

	/** Path to test file. */
	public static final Path moby = Path.of("src", "main", "resources", "2701-0.txt");

	/** Word to look for. */
	public static final String word = "whale";

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * extremely inefficient approach due to string concatenation and reading the
	 * entire file into memory at once.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsConcat(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
			String text = "";
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				text += line + "\n";
			}

			for (String token : tokenize.apply(text)) {
				if (token.equals(word)) {
					count++;
				}
			}

			return count;
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * inefficient approach due to reading the entire file into memory at once.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsBuffer(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
			StringBuffer text = new StringBuffer();
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				text.append(line);
				text.append("\n");
			}

			for (String token : tokenize.apply(text.toString())) {
				if (token.equals(word)) {
					count++;
				}
			}

			return count;
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * efficient straightforward synchronous approach.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsNormal(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
			String line;
			long count = 0;

			while ((line = reader.readLine()) != null) {
				for (String token : tokenize.apply(line)) {
					if (token.equals(word)) {
						count++;
					}
				}
			}

			return count;
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * efficient synchronous streaming approach.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsStream(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				Stream<String> stream = reader.lines();
		) {
			return stream.flatMap(line -> Arrays.stream(tokenize.apply(line)))
					.filter(token -> token.equals(word))
					.count();
		}
	}

	/**
	 * Calculates the number of times the word provided appears in a file using
	 * the tokenize function to split lines into tokens. This method uses an
	 * efficient parallelized streaming approach.
	 *
	 * @param path the path to read
	 * @param word the word to count
	 * @param tokenize the function to break lines into tokens
	 * @return the number of times the word appeared in the file
	 * @throws IOException if an I/O error occurs
	 */
	public static long countWordsParallelStream(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				Stream<String> stream = reader.lines();
		) {
			return stream.parallel()
					.flatMap(line -> Arrays.stream(tokenize.apply(line)))
					.filter(token -> token.equals(word))
					.count();
		}
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 * @throws IOException if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		int runs = 5; // runs for concat test case

		// read the test file into memory for caching
		List<String> lines = Files.readAllLines(moby, UTF_8);
		String text = Files.readString(moby, UTF_8);

		System.out.printf("Test file has %d lines and %d characters.%n%n", lines.size(), text.length());

		// sort by minimum seconds
		TreeSet<BenchmarkResult> results = new TreeSet<>(Comparator.comparingDouble(r -> r.minimum()));

		SimpleBenchmark concat = ParallelStreamBenchmark::countWordsConcat;
		SimpleBenchmark buffer = ParallelStreamBenchmark::countWordsBuffer;
		SimpleBenchmark normal = ParallelStreamBenchmark::countWordsNormal;
		SimpleBenchmark stream = ParallelStreamBenchmark::countWordsStream;
		SimpleBenchmark parallel = ParallelStreamBenchmark::countWordsParallelStream;

		// run all of them once before benchmarking
		System.out.println("WARMUP ROUNDS");
		System.out.println("---------------");
		parallel.warmup(warmup, "Parallel");
		buffer.warmup(warmup, "Buffer");
		normal.warmup(warmup, "Normal");
		stream.warmup(warmup, "Stream");
		concat.warmup(runs, "Concat"); // takes **forever**, do fewer rounds

		System.out.println();

		// collect actual benchmark results
		System.out.println("TIMED ROUNDS");
		System.out.println("--------------");
		results.add(parallel.benchmark(timed, "Parallel"));
		results.add(buffer.benchmark(timed, "Buffer"));
		results.add(normal.benchmark(timed, "Normal"));
		results.add(stream.benchmark(timed, "Stream"));
		results.add(concat.benchmark(runs, "Concat")); // takes **forever**, do fewer rounds

		BenchmarkResult fast = results.first();
		BenchmarkResult slow = results.last();

		System.out.println();
		System.out.printf("Fastest: %.3f (%s)%n", fast.minimum(), fast.name());
		System.out.printf("Slowest: %.3f (%s)%n", slow.minimum(), slow.name());
		System.out.printf("Speedup: %.1fx%n", slow.minimum() / fast.minimum());

		/*
		 * The line-by-line approach in "normal" and "stream" is efficient with
		 * respect to time and memory, whereas the buffer approach must store the
		 * entire unprocessed file in memory along with the input line and tokenized
		 * versions.
		 */
	}

	/**
	 * This is a simple benchmark. It is difficult to get a good benchmark in Java
	 * due to all of the optimization going on while you run the code, so consider
	 * using a microbenchmark library like JMH instead.
	 */
	@FunctionalInterface
	public static interface SimpleBenchmark {
		/**
		 * The method to benchmark.
		 *
		 * @param path the path to the file to open
		 * @param word the word to look for
		 * @param tokenize the method to use for tokenization
		 * @return number of times word was found
		 * @throws IOException if an I/O error occurs
		 */
		public long method(Path path, String word, Function<String, String[]> tokenize) throws IOException;

		/**
		 * Splits a line into tokens by any non-alphabetic character.
		 *
		 * @param line the line to split into tokens
		 * @return the tokens
		 */
		public static String[] tokenize(String line) {
			return line.toLowerCase().split("[^\\p{Alpha}]+");
		}

		/**
		 * Default warmup implementation (does not save any timings).
		 *
		 * @param rounds number of warmup rounds
		 * @param label the benchmark label to use in output
		 * @return unused return value
		 * @throws IOException if an I/O error occurs
		 */
		public default long warmup(int rounds, String label) throws IOException {
			long count = 0; // just to make sure we always use the result

			System.out.printf("%10s: ", label);

			for (int i = 0; i < rounds; i++) {
				System.out.print(".");
				count += method(moby, word, SimpleBenchmark::tokenize);
				count += random.nextInt(100);
			}

			System.out.println(" (done)");
			return count;
		}

		/**
		 * Default benchmark implementation.
		 *
		 * @param rounds number of timed rounds
		 * @param label the benchmark label to use in output
		 * @return the benchmark results
		 * @throws IOException if an I/O error occurs
		 */
		public default BenchmarkResult benchmark(int rounds, String label) throws IOException {
			long count = 0; // just to make sure we always use the result

			long total = 0;
			long smallest = Long.MAX_VALUE;

			System.out.printf("%10s: ", label);

			for (int i = 0; i < rounds; i++) {
				System.out.print(".");

				Instant start = Instant.now();
				count += method(moby, word, SimpleBenchmark::tokenize);
				count += random.nextInt(100);

				long elapsed = Duration.between(start, Instant.now()).toMillis();
				total += elapsed;
				smallest = Math.min(smallest, elapsed);
			}

			for (int j = rounds; j < timed; j++) {
				System.out.print(" ");
			}

			double average = (double) total / rounds / Duration.ofSeconds(1).toMillis();
			double minimum = (double) smallest / Duration.ofSeconds(1).toMillis();

			String format = " %.4f min, %.4f avg seconds%n";
			System.out.printf(format, minimum, average);

			return new BenchmarkResult(minimum, average, label, count);
		}
	}

	/**
	 * Stores benchmark results.
	 *
	 * @param seconds the average number of seconds
	 * @param name the name of the benchmark
	 * @param unused the unused count value (help prevent over-optimization)
	 */
	@SuppressWarnings("javadoc") // javadoc + record bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=572367
	public record BenchmarkResult(double minimum, double average, String name, long unused) {

	}
}
