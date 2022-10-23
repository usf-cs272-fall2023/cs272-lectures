package edu.usfca.cs272;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Demonstrates the time difference between using the synchronized keyword and a
 * read/write lock for an IndexedSet object.
 *
 * @see IndexedSet
 * @see SynchronizedSet
 * @see ConcurrentSet
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class SetDemo {
	/** Used to hold results (but never actively used). */
	public static List<Integer> blackhole = new ArrayList<>();

	/** Number of warmup rounds. */
	public static final int WARMUP = 10;

	/** Number of timed rounds. */
	public static final int TIMED = 30;

	/**
	 * Perform several expensive read operations.
	 *
	 * @param source the source set
	 * @param sorted whether to make a sorted or unsorted copy
	 * @return the maximum value found in the source set
	 */
	public static Integer expensiveReads(IndexedSet<Integer> source, boolean sorted) {
		IndexedSet<Integer> copy = source.copy(sorted);
		Integer max = 0;

		for (int i = 0; i < copy.size(); i++) { // use copy size
			max = Math.max(max, source.get(i)); // use source get
		}

		return max;
	}

	/**
	 * Times two expensive read operations sequentially.
	 *
	 * @param source the data source
	 * @return elapsed time
	 */
	public static long timeSingle(IndexedSet<Integer> source) {
		Instant start = Instant.now();

		Integer result1 = expensiveReads(source, true);
		Integer result2 = expensiveReads(source, false);

		Duration elapsed = Duration.between(start, Instant.now());

		blackhole.add(result1);
		blackhole.add(result2);

		return elapsed.toNanos();
	}

	/**
	 * Calls expensive read operations from within run method.
	 *
	 * @see SetDemo#expensiveReads(IndexedSet, boolean)
	 */
	public static class WorkerThread extends Thread {
		/** The data source. */
		private final IndexedSet<Integer> source;

		/** Whether to make a sorted or unsorted copy. */
		private final boolean sorted;

		/** The result calculated from the expensive readers. */
		private Integer result;

		/**
		 * Initializes this thead.
		 *
		 * @param source the data source
		 * @param sorted whether to make a sorted copy
		 */
		public WorkerThread(IndexedSet<Integer> source, boolean sorted) {
			this.source = source;
			this.sorted = sorted;
		}

		@Override
		public void run() {
			result = expensiveReads(source, sorted);
		}
	}

	/**
	 * Times two expensive read operations sequentially.
	 *
	 * @param source the data source (should be thread safe)
	 * @return elapsed time
	 * @throws InterruptedException if interrupted
	 */
	public static long timeThread(IndexedSet<Integer> source) throws InterruptedException {
		WorkerThread worker1 = new WorkerThread(source, true);
		WorkerThread worker2 = new WorkerThread(source, false);

		Instant start = Instant.now();

		worker1.start();
		worker2.start();

		worker1.join();
		worker2.join();

		Duration elapsed = Duration.between(start, Instant.now());

		blackhole.add(worker1.result);
		blackhole.add(worker2.result);

		return elapsed.toNanos();
	}

	/**
	 * Attempts to benchmark the differences between synchronization approaches.
	 * Try modifying the order operations are performed in the warmup and timed
	 * loops. It should (theoretically) not impact which approach is faster.
	 *
	 * @param size the size of the benchmark to run
	 * @return the maximum average value
	 * @throws InterruptedException if interrupted
	 */
	public static double benchmark(int size) throws InterruptedException {
		List<Integer> values = new Random().ints(size).boxed().toList();

		IndexedSet<Integer> set1 = new IndexedSet<>();
		IndexedSet<Integer> set2 = new SynchronizedSet<>();
		IndexedSet<Integer> set3 = new ConcurrentSet<>();

		set1.addAll(values);
		set2.addAll(values);
		set3.addAll(values);

		double nanos = Duration.ofSeconds(1).toNanos();

		long[] warmup1 = new long[WARMUP];
		long[] warmup2 = new long[WARMUP];
		long[] warmup3 = new long[WARMUP];

		long[] timed1 = new long[TIMED]; // indexed
		long[] timed2 = new long[TIMED]; // synchronized
		long[] timed3 = new long[TIMED]; // concurrent

		// clear out static data before running
		blackhole.clear();

		for (int i = 0; i < WARMUP; i++) {
			warmup3[i] = timeThread(set3);
			warmup2[i] = timeThread(set2);
			warmup1[i] = timeSingle(set1);
		}

		long warmupTotal = 0;
		warmupTotal += LongStream.of(warmup1).sum();
		warmupTotal += LongStream.of(warmup2).sum();
		warmupTotal += LongStream.of(warmup3).sum();

		System.out.printf("Warmup lasted for %.4f seconds.%n", warmupTotal / nanos);

		for (int i = 0; i < TIMED; i++) {
			timed3[i] = timeThread(set3);
			timed2[i] = timeThread(set2);
			timed1[i] = timeSingle(set1);
		}

		/*
		 * make sure to use the blackhole values somehow
		 */

		Integer maxLast = blackhole.stream().max(Comparator.naturalOrder()).get();
		System.out.printf("The maximum last value was %d.%n", maxLast.intValue());
		System.out.println();

		/*
		 * calculate and output minimum and average times
		 */

		double average1 = LongStream.of(timed1).sum() / nanos / TIMED;
		double average2 = LongStream.of(timed2).sum() / nanos / TIMED;
		double average3 = LongStream.of(timed3).sum() / nanos / TIMED;

		double minimum1 = LongStream.of(timed1).min().getAsLong() / nanos;
		double minimum2 = LongStream.of(timed2).min().getAsLong() / nanos;
		double minimum3 = LongStream.of(timed3).min().getAsLong() / nanos;

		String format = "%15s : %.5f min, %.5f avg seconds%n";
		System.out.printf(format, set1.getClass().getSimpleName(), minimum1, average1);
		System.out.printf(format, set2.getClass().getSimpleName(), minimum2, average2);
		System.out.printf(format, set3.getClass().getSimpleName(), minimum3, average3);
		System.out.println();

		/*
		 * calculate and output speedups (using minimums)
		 */

		double speed1 = minimum1 / minimum2;
		double speed2 = minimum2 / minimum3;
		double speed3 = minimum1 / minimum3;

		format = "%-15s is %.4fx %s than %-12s%n";
		System.out.printf(format, set2.getClass().getSimpleName(), speed1,
				minimum2 < minimum1 ? "faster" : "slower",
				set1.getClass().getSimpleName());

		System.out.printf(format, set3.getClass().getSimpleName(), speed3,
				minimum3 < minimum1 ? "faster" : "slower",
				set1.getClass().getSimpleName());

		System.out.printf(format, set3.getClass().getSimpleName(), speed2,
				minimum3 < minimum2 ? "faster" : "slower",
				set2.getClass().getSimpleName());

		// return something (unused)
		return Math.max(Math.max(average1, average2), average3);
	}

	/**
	 * Roughly demonstrates runtime different between using synchronized and a
	 * read/write lock when there are more than one large read operations. Note:
	 * not an accurate benchmark, but you get the idea.
	 *
	 * @param args unused
	 * @throws InterruptedException if interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		// TURN OFF LOGGING BEFORE RUNNING THIS!
		Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);

		int size = 1000; // on my laptop, 10000 takes painfully long
		benchmark(size);
	}
}
