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

public class SetDemo {
	public static List<Integer> blackhole = new ArrayList<>();
	public static final int WARMUP = 10;
	public static final int TIMED = 30;

	public static Integer expensiveReads(IndexedSet<Integer> source, boolean sorted) {
		IndexedSet<Integer> copy = source.copy(sorted);
		Integer max = 0;

		for (int i = 0; i < copy.size(); i++) {
			max = Math.max(max, source.get(i));
		}

		return max;
	}

	public static long timeSingle(IndexedSet<Integer> source) {
		Instant start = Instant.now();
		Integer result1 = expensiveReads(source, true);
		Integer result2 = expensiveReads(source, false);
		Duration elapsed = Duration.between(start, Instant.now());

		blackhole.add(result1);
		blackhole.add(result2);

		return elapsed.toNanos();
	}

	public static class WorkerThread extends Thread {
		private final IndexedSet<Integer> source;
		private final boolean sorted;
		private Integer result;

		public WorkerThread(IndexedSet<Integer> source, boolean sorted) {
			this.source = source;
			this.sorted = sorted;
		}

		@Override
		public void run() {
			result = expensiveReads(source, sorted);
		}
	}

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

	public static double benchmark(int size) throws InterruptedException {
		List<Integer> values = new Random().ints(size).boxed().toList();

		IndexedSet<Integer> set1 = new IndexedSet<>();
		IndexedSet<Integer> set2 = new SynchronizedSet<>();
//		IndexedSet<Integer> set3 = new ConcurrentSet<>();

		set1.addAll(values);
		set2.addAll(values);
//		set3.addAll(values);

		double nanos = Duration.ofSeconds(1).toNanos();

		long[] warmup1 = new long[WARMUP];
		long[] warmup2 = new long[WARMUP];
		long[] warmup3 = new long[WARMUP];

		long[] timed1 = new long[TIMED];
		long[] timed2 = new long[TIMED];
		long[] timed3 = new long[TIMED];

		blackhole.clear();

		for (int i = 0; i < WARMUP; i++) {
//			warmup3[i] = timeThread(set3);
			warmup2[i] = timeThread(set2);
			warmup1[i] = timeSingle(set1);
		}

		long warmupTotal = 0;
		warmupTotal += LongStream.of(warmup1).sum();
		warmupTotal += LongStream.of(warmup2).sum();
		warmupTotal += LongStream.of(warmup3).sum();

		System.out.printf("Warmup lasted for %.4f seconds.%n", warmupTotal / nanos);

		for (int i = 0; i < TIMED; i++) {
//			timed3[i] = timeThread(set3);
			timed2[i] = timeThread(set2);
			timed1[i] = timeSingle(set1);
		}

		Integer maxLast = blackhole.stream().max(Comparator.naturalOrder()).get();
		System.out.printf("The maximum last value was %d.%n", maxLast.intValue());
		System.out.println();

		double average1 = LongStream.of(timed1).sum() / nanos / TIMED;
		double average2 = LongStream.of(timed2).sum() / nanos / TIMED;
		double average3 = LongStream.of(timed3).sum() / nanos / TIMED;

		double minimum1 = LongStream.of(timed1).min().getAsLong() / nanos;
		double minimum2 = LongStream.of(timed2).min().getAsLong() / nanos;
		double minimum3 = LongStream.of(timed3).min().getAsLong() / nanos;

		String format = "%15s : %.5f min, %.5f avg seconds%n";
		System.out.printf(format, set1.getClass().getSimpleName(), minimum1, average1);
		System.out.printf(format, set2.getClass().getSimpleName(), minimum2, average2);
//		System.out.printf(format, set3.getClass().getSimpleName(), minimum3, average3);
		System.out.println();

		double speed1 = minimum1 / minimum2;
//		double speed2 = minimum2 / minimum3;
//		double speed3 = minimum1 / minimum3;

		format = "%-15s is %.4fx %s than %-12s%n";
		System.out.printf(format, set2.getClass().getSimpleName(), speed1,
				minimum2 < minimum1 ? "faster" : "slower",
				set1.getClass().getSimpleName());

//		System.out.printf(format, set3.getClass().getSimpleName(), speed3,
//				minimum3 < minimum1 ? "faster" : "slower",
//				set1.getClass().getSimpleName());
//
//		System.out.printf(format, set3.getClass().getSimpleName(), speed2,
//				minimum3 < minimum2 ? "faster" : "slower",
//				set2.getClass().getSimpleName());

		return Math.max(Math.max(average1, average2), average3);
	}

	public static void main(String[] args) throws InterruptedException {
		Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);
		int size = 1000;
		benchmark(size);
	}
}
