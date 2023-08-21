package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Attempts to benchmark the different multithreading approaches. Run multiple
 * times to see whether the execution order impacts runtimes.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class ListingBenchmark {
	/** Number of warmup rounds. */
	public static final int WARMUP_ROUNDS = 10;

	/** Number of timed rounds. */
	public static final int TIMED_ROUNDS = 20;

	/*
	 * This is really meant to demonstrate just how bad we can make the runtime
	 * with a poor multithreading implementation, and the difference using a work
	 * queue can make on the runtime. However, even with the work queue, it might
	 * be slower than single threading for this particular problem since it has so
	 * many write operations versus read operations.
	 */

	/**
	 * Runs the benchmarks in random order.
	 *
	 * @param args unused
	 * @throws IOException if an I/O exception occurs
	 * @throws InterruptedException if interrupted
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TURN OFF LOGGING BEFORE RUNNING THIS!
		Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);

		// TODO Change this to a large directory on your system!
		Path test = Path.of("..");
		Set<Path> expected = SerialDirectoryListing.list(test);

		// collect benchmarks in a list and shuffle to avoid order effects
		List<Benchmarker> benchmarks = new ArrayList<>();

		benchmarks.add(new Benchmarker("Serial") {
			@Override
			public Set<Path> run(Path path) throws IOException {
				return SerialDirectoryListing.list(test);
			}
		});

		benchmarks.add(new Benchmarker("Blocking") {
			@Override
			public Set<Path> run(Path path) throws InterruptedException {
				return BlockingDirectoryListing.list(test);
			}
		});

		benchmarks.add(new Benchmarker("Threaded") {
			@Override
			public Set<Path> run(Path path) throws InterruptedException {
				return ThreadedDirectoryListing.list(test);
			}
		});

		benchmarks.add(new Benchmarker("Queued") {
			@Override
			public Set<Path> run(Path path) throws InterruptedException {
				return QueuedDirectoryListing.list(test);
			}
		});

		benchmarks.add(new Benchmarker("Executor") {
			@Override
			public Set<Path> run(Path path) throws InterruptedException {
				return ExecutorDirectoryListing.list(test);
			}
		});

		Collections.shuffle(benchmarks);

		for (Benchmarker current : benchmarks) {
			current.benchmark(test, expected);
		}

		Collections.sort(benchmarks);

		String order = benchmarks.stream()
				.map(benchmark -> benchmark.name)
				.collect(Collectors.joining(" < "));

		System.out.println();
		System.out.println(order);

		System.out.println();

		var iterator = benchmarks.iterator();
		Benchmarker fastest = iterator.next();

		while (iterator.hasNext()) {
			Benchmarker current = iterator.next();
			double speedup = (double) current.minimum.toNanos() / fastest.minimum.toNanos();
			String format = "%s has %.1fx speedup over %s%n";
			System.out.printf(format, fastest.name, speedup, current.name);
		}
	}

	/**
	 * A class to benchmark directory listings.
	 */
	private static abstract class Benchmarker implements Comparable<Benchmarker> {
		/** Name of the benchmark. */
		public String name;

		/** Total elapsed runtime. */
		public Duration total;

		/** Calculated minimum runtime. */
		public Duration minimum;

		/**
		 * Initializes this benchmark.
		 *
		 * @param name the name of the benchmark
		 */
		public Benchmarker(String name) {
			this.name = name;
			this.total = Duration.ZERO;
			this.minimum = Duration.ofDays(1);
		}

		/**
		 * Generates a directory listing.
		 *
		 * @param directory the directory to start with
		 * @return set of paths found within the directory
		 * @throws IOException if an I/O exception occurs
		 * @throws InterruptedException if interrupted
		 */
		public abstract Set<Path> run(Path directory) throws IOException, InterruptedException;

		/**
		 * Conducts the benchmark.
		 *
		 * @param directory the directory to start with
		 * @param expected the expected results
		 * @throws IOException if an I/O exception occurs
		 * @throws InterruptedException if interrupted
		 */
		public void benchmark(Path directory, Set<Path> expected) throws InterruptedException, IOException {
			System.out.print(String.format("%9s: ", name));
			Runtime.getRuntime().gc();

			Set<Path> actual = run(directory);

			// verify results first
			if (!actual.equals(expected)) {
				System.err.printf(
						"Unexpected results! Expected %d elements, found %d elements.",
						expected.size(), actual.size());
			}

			// warmup
			for (int i = 0; i < WARMUP_ROUNDS; i++) {
				actual.addAll(run(directory));
			}

			// timed
			for (int i = 0; i < TIMED_ROUNDS; i++) {
				Instant start = Instant.now();
				Set<Path> local = run(directory);
				Instant end = Instant.now();

				Duration elapsed = Duration.between(start, end);
				total = total.plus(elapsed);
				minimum = elapsed.compareTo(minimum) < 0 ? elapsed : minimum;

				actual.addAll(local);
			}

			// averaged result
			double average = (double) total.toMillis() / TIMED_ROUNDS;
			double converted = (double) minimum.toNanos() / Duration.ofMillis(1).toNanos();

			System.out.printf("%7.2fms min, %7.2fms avg%n", converted, average);
		}

		@Override
		public int compareTo(Benchmarker other) {
			return this.minimum.compareTo(other.minimum);
		}
	}
}
