package edu.usfca.cs272;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

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

//		TreeSet<Integer> threads = new TreeSet<>();
//		Collections.addAll(threads, 1, 2, 3, 5, 8, 20);
//
//		int size = 100;
//		double fastest = Double.MAX_VALUE;
//
//		for (Integer thread : threads) {
//			fastest = Math.min(fastest, benchmark(size, thread));
//		}
//
//		System.out.println();
//
//		for (Integer thread : threads.descendingSet()) {
//			fastest = Math.min(fastest, benchmark(size, thread));
//		}
//
//		System.out.printf("%nFastest average: %.06f", fastest);
	}

	private static double benchmark(int size, int threads) throws InterruptedException {
		int warmup = 10;
		int runs = 30;

		int[] numbers = new Random().ints(size, 0, 100).toArray();

		long placeholder = 0;
		double average = 0;

		Instant start;
		Duration elapsed;

		for (int i = 0; i < warmup; i++) {
			placeholder = Math.max(placeholder, total(numbers, threads));
		}

		for (int i = 0; i < runs; i++) {
			start = Instant.now();
			placeholder = Math.max(placeholder, total(numbers, threads));
			elapsed = Duration.between(start, Instant.now());
			average += elapsed.toNanos();
		}

		average /= runs;
		average /= Duration.ofSeconds(1).toNanos();

		System.out.printf(
				"%.06f seconds average for %d numbers and %02d threads (placeholder %d)%n",
				average, size, threads, placeholder);

		return average;
	}
}
