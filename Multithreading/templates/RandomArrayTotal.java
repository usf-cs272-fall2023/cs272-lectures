package edu.usfca.cs272;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

public class RandomArrayTotal {

	public static long total(int[] numbers) {
		return 0; // TODO Fill in single threading code
	}

	public static long total(int[] numbers, int threads) throws InterruptedException {
		ArrayWorker[] workers = new ArrayWorker[threads];

		int chunk = numbers.length / workers.length;
		int remainder = numbers.length % workers.length;
		int last = workers.length - 1;
		long total = 0;

		// TODO Fill in multithreading code

		return total;
	}

	private static class ArrayWorker extends Thread {
		// TODO Fill in class
	}

	public static void main(String[] args) throws InterruptedException {
		int[] numbers = new Random().ints(10, 0, 100).toArray();

		System.out.println(Arrays.toString(numbers));
		System.out.println();

		System.out.println(total(numbers));
		System.out.println(total(numbers, 5));
		System.out.println();

		/*
		TreeSet<Integer> threads = new TreeSet<>();
		Collections.addAll(threads, 1, 2, 3, 5, 8, 20);

		int size = 100; // 10000000
		System.out.printf("calculating sum for %d random numbers...%n", size);

		double minBestTime = Double.MAX_VALUE;
		int minThreads = 0;

		for (Integer thread : threads) {
			double bestTime = benchmark(size, thread);

			if (bestTime < minBestTime) {
				minBestTime = bestTime;
				minThreads = thread;
			}
		}

		System.out.println();

		for (Integer thread : threads.descendingSet()) {
			double bestTime = benchmark(size, thread);

			if (bestTime < minBestTime) {
				minBestTime = bestTime;
				minThreads = thread;
			}
		}

		System.out.printf(
				"%nFastest minimum: %02d threads in %.06f seconds",
				minThreads, minBestTime);
		*/
	}

	private static double benchmark(int size, int threads) throws InterruptedException {
		int warmup = 10;
		int runs = 30;

		int[] numbers = new Random().ints(size, 0, 100).toArray();

		long placeholder = 0;
		double average = 0;

		Instant start;
		Duration elapsed;
		Duration minimum = ChronoUnit.FOREVER.getDuration();

		for (int i = 0; i < warmup; i++) {
			placeholder = Math.max(placeholder, total(numbers, threads));
		}

		for (int i = 0; i < runs; i++) {
			start = Instant.now();
			placeholder = Math.max(placeholder, total(numbers, threads));
			elapsed = Duration.between(start, Instant.now());
			minimum = minimum.compareTo(elapsed) > 0 ? elapsed : minimum;
			average += elapsed.toNanos();
		}

		average /= runs;
		average /= Duration.ofSeconds(1).toNanos();

		double seconds = (double) minimum.toNanos() / Duration.ofSeconds(1).toNanos();

		System.out.printf(
				"%02d threads: %.06f min, %.06f avg seconds (sum: %d) %n",
				threads, seconds, average, placeholder);

		return seconds;
	}
}
