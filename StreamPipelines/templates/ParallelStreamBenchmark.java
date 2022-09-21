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

public class ParallelStreamBenchmark {
	public static Random random = new Random(System.nanoTime());
	public static final int warmup = 15;
	public static final int timed = 15;
	public static final Path moby = Path.of("src", "main", "resources", "2701-0.txt");
	public static final String word = "whale";

	public static long countWordsConcat(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
			String text = "";
			String line;
			long count = 0;

			// TODO Implement countWordsConcat

			return count;
		}
	}

	public static long countWordsBuffer(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
			StringBuffer text = new StringBuffer();
			String line;
			long count = 0;

			// TODO Implement countWordsBuffer

			return count;
		}
	}

	public static long countWordsNormal(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8)) {
			String line;
			long count = 0;

			// TODO Implement countWordsNormal

			return count;
		}
	}

	public static long countWordsStream(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				// TODO Add stream
		) {
			// TODO Implement countWordsStream
			return 0;
		}
	}

	public static long countWordsParallelStream(Path path, String word,
			Function<String, String[]> tokenize) throws IOException {
		try (
				BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				// TODO Add stream
		) {
			// TODO Implement countWordsParallelStream
			return 0;
		}
	}

	public static void main(String[] args) throws IOException {
		int runs = 5;

		List<String> lines = Files.readAllLines(moby, UTF_8);
		String text = Files.readString(moby, UTF_8);

		System.out.printf("Test file has %d lines and %d characters.%n%n", lines.size(), text.length());

		TreeSet<BenchmarkResult> results = new TreeSet<>(/* TODO Add comparator */);

		SimpleBenchmark concat = ParallelStreamBenchmark::countWordsConcat;
		SimpleBenchmark buffer = ParallelStreamBenchmark::countWordsBuffer;
		SimpleBenchmark normal = ParallelStreamBenchmark::countWordsNormal;
		SimpleBenchmark stream = ParallelStreamBenchmark::countWordsStream;
		SimpleBenchmark parallel = ParallelStreamBenchmark::countWordsParallelStream;

		System.out.println("WARMUP ROUNDS");
		System.out.println("---------------");
		parallel.warmup(warmup, "Parallel");
		buffer.warmup(warmup, "Buffer");
		normal.warmup(warmup, "Normal");
		stream.warmup(warmup, "Stream");
		concat.warmup(runs, "Concat");

		System.out.println();

		System.out.println("TIMED ROUNDS");
		System.out.println("--------------");
		results.add(parallel.benchmark(timed, "Parallel"));
		results.add(buffer.benchmark(timed, "Buffer"));
		results.add(normal.benchmark(timed, "Normal"));
		results.add(stream.benchmark(timed, "Stream"));
		results.add(concat.benchmark(runs, "Concat"));

		BenchmarkResult fast = results.first();
		BenchmarkResult slow = results.last();

		System.out.println();
		System.out.printf("Fastest: %.3f (%s)%n", fast.minimum(), fast.name());
		System.out.printf("Slowest: %.3f (%s)%n", slow.minimum(), slow.name());
		System.out.printf("Speedup: %.1fx%n", slow.minimum() / fast.minimum());
	}

	@FunctionalInterface
	public static interface SimpleBenchmark {
		public long method(Path path, String word, Function<String, String[]> tokenize) throws IOException;

		public static String[] tokenize(String line) {
			return line.toLowerCase().split("[^\\p{Alpha}]+");
		}

		public default long warmup(int rounds, String label) throws IOException {
			long count = 0;

			System.out.printf("%10s: ", label);

			for (int i = 0; i < rounds; i++) {
				System.out.print(".");
				count += method(moby, word, SimpleBenchmark::tokenize);
				count += random.nextInt(100);
			}

			System.out.println(" (done)");
			return count;
		}

		public default BenchmarkResult benchmark(int rounds, String label) throws IOException {
			long count = 0;

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

	public record BenchmarkResult(double minimum, double average, String name, long unused) {

	}
}
