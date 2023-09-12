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
		int loops = 5;

		String string = "";
		StringBuilder builder = new StringBuilder();

		// TODO Update output
//		System.out.println("| i | text  | string (len)  | builder (cap)  |");
//		System.out.println("|---+-------+---------------+----------------|");
//		String format = "| %d | %-5s | x%08X (%d) | x%08X (%d) |%n";

		for (int i = 1; i <= loops; i++) {
			String text = Integer.toString(i);

			// TODO Update loop
			System.out.println(text);

//			System.out.printf(format, i, string, System.identityHashCode(string), string.length(),
//					System.identityHashCode(builder), builder.capacity());
		}

		System.out.println();

		// TODO Add larger comparison
	}

	public static void compareMemory(int size) {
		long strings = getUsedStrings(size);
		long builders = getUsedBuilders(size);

		long difference = builders - strings;
		double change = (double) difference / strings * 100.0;

		System.out.printf("%15s: %,10d bytes (%.1f%% %s)%n%n", "difference",
				difference, change, change < 0 ? "less" : "more");
	}

	public static long getUsedStrings(int size) {
		List<String> lines = new ArrayList<>(size);
		long stored = 0;

		for (int i = 1; i <= size; i++) {
			String item = Integer.toString(i);
			stored += item.length();
			lines.add(item);
		}

		System.out.printf("%10s used: %,10d bytes for %,d characters%n", "strings", stored, stored);
		return stored;
	}

	public static long getUsedBuilders(int size) {
		List<StringBuilder> lines = new ArrayList<>(size);
		long stored = 0;
		long capacity = 0;

		for (int i = 1; i <= size; i++) {
			StringBuilder item = new StringBuilder(Integer.toString(i));
			stored += item.length();
			capacity += item.capacity();
			lines.add(item);
		}

		System.out.printf("%10s used: %,10d bytes for %,d characters%n", "builders", capacity, stored);
		return capacity;
	}

	public static void compareMutation(int size, int rounds) {
		List<String> lines = Collections.nCopies(size, "hello world");

		Map<String, Long> strings = new LinkedHashMap<>();
		Map<String, Long> builders = new LinkedHashMap<>();

		System.out.println("Profiling concat...");
		for (int i = 0; i < rounds; i++) {
			Map<String, Long> profile = profileConcat(lines);
			profile.forEach((k, v) -> strings.merge(k, v, Math::min));
			System.out.println(profile);
		}

		System.out.println();

		System.out.println("Profiling append...");
		for (int i = 0; i < rounds; i++) {
			Map<String, Long> profile = profileAppend(lines);
			profile.forEach((k, v) -> builders.merge(k, v, Math::min));
			System.out.println(profile);
		}

		System.out.println();
		System.out.println("|          description |   string concat |  builder append |      difference |        change |");
		System.out.println("|----------------------|-----------------|-----------------|-----------------|---------------|");

		String format = "| %20s | %,15d | %,15d | %,15d | %7.2f%% %s |%n";

		for (String key : strings.keySet()) {
			long string = strings.get(key);
			long builder = builders.get(key);

			long difference = builder - string;
			double change = (double) difference / string * 100.0;

			System.out.printf(format, key, string, builder, difference, Math.abs(change), change < 0 ? "less" : "more");
		}
	}

	public static Map<String, Long> profileConcat(List<String> lines) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		Set<CharSequence> sequences = new HashSet<CharSequence>();
		long stored = 0;
		String joined = "";

		Instant beforeTime = Instant.now();

		for (String line : lines) {
			joined += line;
			sequences.add(joined);
		}

		Instant afterTime = Instant.now();

		for (CharSequence sequence : sequences) {
			stored += sequence.length();
		}

		Map<String, Long> profile = new LinkedHashMap<>();
		profile.put("joined characters", Long.valueOf(joined.length()));
		profile.put("created objects", Long.valueOf(sequences.size()));
		profile.put("created characters", Long.valueOf(stored));
		profile.put("allocated space", Long.valueOf(stored));
		profile.put("elapsed nanos", Duration.between(beforeTime, afterTime).toNanos());
		return profile;
	}

	public static Map<String, Long> profileAppend(List<String> lines) {
		Runtime run = Runtime.getRuntime();
		run.gc();

		Set<CharSequence> sequences = new HashSet<CharSequence>();
		long stored = 0;

		StringBuilder joined = new StringBuilder();
		long capacity = joined.capacity();
		long previous = capacity;

		Instant beforeTime = Instant.now();

		for (String line : lines) {
			joined.append(line);
			sequences.add(joined);

			if (joined.capacity() != previous) {
				previous = joined.capacity();
				capacity += capacity;
			}
		}

		Instant afterTime = Instant.now();

		for (CharSequence sequence : sequences) {
			stored += sequence.length();
		}

		Map<String, Long> profile = new LinkedHashMap<>();
		profile.put("joined characters", Long.valueOf(joined.length()));
		profile.put("created objects", Long.valueOf(sequences.size()));
		profile.put("created characters", Long.valueOf(stored));
		profile.put("allocated space", capacity);
		profile.put("elapsed nanos", Duration.between(beforeTime, afterTime).toNanos());
		return profile;
	}
}
