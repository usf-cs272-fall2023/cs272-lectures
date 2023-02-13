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

public class ConcatenationDemo {
	public static void main(String[] args) {
		int size = 10;
		int rounds = 5;

		// ManagementFactory.getMemoryMXBean().setVerbose(true);

		compareMemory(size);
		compareMutation(size, rounds);
	}

	public static void compareMemory(int size) {
		long strings = getUsedStrings(size);
		long builders = getUsedBuilders(size);

		long difference = builders - strings;
		double change = (double) difference / strings * 100.0;
		System.out.printf("%15s: %,10d bytes (%.1f%% more)%n%n", "difference", difference, change);
	}

	public static long getUsedStrings(int size) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		long init = run.totalMemory() - run.freeMemory();
		List<String> lines = new ArrayList<>(size);
		long stored = 0;

		for (int i = 1; i <= size; i++) {
			// TODO
		}

		long used = (run.totalMemory() - run.freeMemory()) - init;
		System.out.printf("%10s used: %,10d bytes for %,d characters%n", "strings", used, stored);

		return used;
	}

	public static long getUsedBuilders(int size) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		long init = run.totalMemory() - run.freeMemory();
		List<StringBuilder> lines = new ArrayList<>(size);
		long stored = 0;

		for (int i = 1; i <= size; i++) {
			// TODO
		}

		long used = (run.totalMemory() - run.freeMemory()) - init;
		System.out.printf("%10s used: %,10d bytes for %,d characters%n", "builders", used, stored);

		return used;
	}

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

	public static Map<String, Long> profileConcat(List<String> lines) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		Set<CharSequence> sequences = new HashSet<CharSequence>();
		long stored = 0;

		long beforeBytes = run.totalMemory() - run.freeMemory();
		Instant beforeTime = Instant.now();
		String joined = "";

		for (String line : lines) {
			// TODO
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

	public static Map<String, Long> profileAppend(List<String> lines) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		Set<CharSequence> sequences = new HashSet<CharSequence>();
		long stored = 0;

		long beforeBytes = run.totalMemory() - run.freeMemory();
		Instant beforeTime = Instant.now();
		StringBuilder joined = new StringBuilder();

		for (String line : lines) {
			// TODO
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
