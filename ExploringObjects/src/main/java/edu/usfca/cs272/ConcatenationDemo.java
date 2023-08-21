package edu.usfca.cs272;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Demonstrates performance differences between an immutable {@link String}
 * object versus a mutable {@link StringBuilder} object. In particular, shows
 * why String concatenation performs poorly for large numbers of mutations both
 * in terms of time and memory usage.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class ConcatenationDemo {
	/**
	 * Demonstrates performance differences between String and StringBuilder
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		int size = 10000;
		int rounds = 5;

		// uncomment below to see when the gc is run
		// ManagementFactory.getMemoryMXBean().setVerbose(true);

		compareMemory(size);
		compareMutation(size, rounds);
	}

	/**
	 * Estimates the difference in memory requirements for a String list versus a
	 * StringBuilder list when each item is small and unique.
	 *
	 * @param size number of lines to generate
	 */
	public static void compareMemory(int size) {
		long strings = getUsedStrings(size);
		long builders = getUsedBuilders(size);

		long difference = builders - strings;
		double change = (double) difference / strings * 100.0;
		System.out.printf("%15s: %,10d bytes (%.1f%% more)%n%n", "difference", difference, change);
	}

	/**
	 * Estimates how much memory is used for a list of String objects.
	 *
	 * @param size the size of list to create
	 * @return estimated used memory in bytes
	 */
	public static long getUsedStrings(int size) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		long init = run.totalMemory() - run.freeMemory();
		List<String> lines = new ArrayList<>(size);
		long stored = 0;

		for (int i = 1; i <= size; i++) {
			String item = Integer.toString(i);
			stored += item.length();
			lines.add(item);
		}

		long used = (run.totalMemory() - run.freeMemory()) - init;
		System.out.printf("%10s used: %,10d bytes for %,d characters%n", "strings", used, stored);

		return used;
	}

	/**
	 * Estimates how much memory is used for a list of StringBuilder objects.
	 *
	 * @param size the size of list to create
	 * @return estimated used memory in bytes
	 */
	public static long getUsedBuilders(int size) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		long init = run.totalMemory() - run.freeMemory();
		List<StringBuilder> lines = new ArrayList<>(size);
		long stored = 0;

		for (int i = 1; i <= size; i++) {
			StringBuilder item = new StringBuilder(Integer.toString(i));
			stored += item.length();
			lines.add(item);
		}

		long used = (run.totalMemory() - run.freeMemory()) - init;
		System.out.printf("%10s used: %,10d bytes for %,d characters%n", "builders", used, stored);

		return used;
	}

	/**
	 * Estimates the difference in time and memory requirements for joining a list
	 * of lines using a String versus StringBuilder.
	 *
	 * @param size number of lines to generate
	 * @param rounds number of rounds to run
	 */
	public static void compareMutation(int size, int rounds) {
		List<String> lines = Collections.nCopies(size, "hello world");

		Map<String, Long> strings = new LinkedHashMap<>();
		Map<String, Long> builders = new LinkedHashMap<>();

		for (int i = 0; i < rounds; i++) {
			Map<String, Long> profile = profileConcat(lines);
			profile.forEach((k, v) -> strings.merge(k, v, Math::min));
			System.out.println(profile);
		}

		System.out.println();

		for (int i = 0; i < rounds; i++) {
			Map<String, Long> profile = profileAppend(lines);
			profile.forEach((k, v) -> builders.merge(k, v, Math::min));
			System.out.println(profile);
		}

		System.out.println();
		System.out.println("|          description |   string concat |  builder append |      difference |");
		System.out.println("|----------------------|-----------------|-----------------|-----------------|");

		String format = "| %20s | %,15d | %,15d | %,15d |%n";

		for (String key : strings.keySet()) {
			long string = strings.get(key);
			long builder = builders.get(key);
			System.out.printf(format, key, string, builder, string - builder);
		}
	}

	/**
	 * Estimates performance of concatenation.
	 *
	 * @param lines the lines to join
	 * @return profile of operation
	 */
	public static Map<String, Long> profileConcat(List<String> lines) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		Set<CharSequence> sequences = new HashSet<CharSequence>();
		long stored = 0;

		long beforeBytes = run.totalMemory() - run.freeMemory();
		Instant beforeTime = Instant.now();
		String joined = "";

		for (String line : lines) {
			joined += line;
			sequences.add(joined);
		}

		Instant afterTime = Instant.now();
		long afterBytes = run.totalMemory() - run.freeMemory();

		for (CharSequence sequence : sequences) {
			stored += sequence.length();
		}

		Map<String, Long> profile = new LinkedHashMap<>();
		profile.put("joined characters", Long.valueOf(joined.length()));
		profile.put("created objects", Long.valueOf(sequences.size()));
		profile.put("created characters", Long.valueOf(stored));
		profile.put("used bytes", afterBytes - beforeBytes);
		profile.put("elapsed nanos", Duration.between(beforeTime, afterTime).toNanos());
		return profile;
	}

	/**
	 * Estimates performance of appending.
	 *
	 * @param lines the lines to join
	 * @return profile of operation
	 */
	public static Map<String, Long> profileAppend(List<String> lines) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		Set<CharSequence> sequences = new HashSet<CharSequence>();
		long stored = 0;

		long beforeBytes = run.totalMemory() - run.freeMemory();
		Instant beforeTime = Instant.now();
		StringBuilder joined = new StringBuilder();

		for (String line : lines) {
			joined.append(line);
			sequences.add(joined);
		}

		Instant afterTime = Instant.now();
		long afterBytes = run.totalMemory() - run.freeMemory();

		for (CharSequence sequence : sequences) {
			stored += sequence.length();
		}

		Map<String, Long> profile = new LinkedHashMap<>();
		profile.put("joined characters", Long.valueOf(joined.length()));
		profile.put("created objects", Long.valueOf(sequences.size()));
		profile.put("created characters", Long.valueOf(stored));
		profile.put("used bytes", afterBytes - beforeBytes);
		profile.put("elapsed nanos", Duration.between(beforeTime, afterTime).toNanos());
		return profile;
	}
}
