package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class TextFileStreamer {
	public static void consumeTextFile(Path path /* TODO */) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8);
				Stream<String> lines = reader.lines();) {
			// TODO
		}
	}

	public static List<String> consumeTextAsList(Path path /* TODO */) throws IOException {
		List<String> words = new ArrayList<String>();
		// TODO
		return words;
	}

	public static <C extends Collection<String>> C collectTextFile(Path path /* TODO */) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path, UTF_8);) {
			// TODO
			return null;
		}
	}

	public static List<String> collectTextAsList(Path path /* TODO */) throws IOException {
		// TODO
		return null;
	}

	public static void main(String[] args) throws IOException {
		Path sally = Path.of("src", "main", "resources", "sally.txt");
		Function<String, String> clean = s -> s.toLowerCase().replaceAll("[^A-z\\s]+", " ");

		// TODO
	}
}
