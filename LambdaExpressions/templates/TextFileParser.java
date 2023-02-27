package edu.usfca.cs272;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFileParser {
	public static void main(String[] args) throws Exception {
		Path path = Path.of("src", "main", "resources", "sally.txt");
		// TODO Fill in
	}

	public static String removePunctuation(String text) {
		return text.replaceAll("(?U)\\p{Punct}+", "");
	}

	public static List<String> listCleanWords(Path path /* TODO */)
			throws IOException {
		List<String> words = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(path,
				StandardCharsets.UTF_8)) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");

				for (String token : tokens) {
					// TODO Fill in
				}
			}
		}

		return words;
	}

	public static void parseWords(Path path /* TODO */) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(path,
				StandardCharsets.UTF_8)) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");

				for (String token : tokens) {
					// TODO Fill in
				}
			}
		}
	}

	public static List<String> listParsedWords(Path path /* TODO */)
			throws IOException {
		List<String> words = new ArrayList<>();
		// TODO Fill in
		return words;
	}
}
