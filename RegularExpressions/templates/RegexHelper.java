package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	public static final String sample = "Sally Sue sells 76 sea-shells, by   the sea_shore.";
	public static final PrintStream console = new PrintStream(System.out, true, UTF_8);

	public static String replaceSymbols(String text) {
		if (text.isEmpty()) {
			return "\u03B5";
		}

		return text.replaceAll("\n", "\u21B5") // ↵
				.replaceAll("\t", "\u00BB")        // »
				.replaceAll(" ", "\u00B7");        // ·
	}

	public static void printMatches(String text, String regex) {
		var matches = getMatches(text, regex);
		console.printf("%10s -> %2d matches: %s %n", replaceSymbols(regex), matches.size(), matches);
	}

	public static ArrayList<String> getMatches(String text, String regex) {
		ArrayList<String> matches = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		int index = 0;

		while (index < text.length() && matcher.find(index)) {
			matches.add(replaceSymbols(text.substring(matcher.start(), matcher.end())));

			if (matcher.start() == matcher.end()) {
				index = matcher.end() + 1;
			}
			else {
				index = matcher.end();
			}
		}

		return matches;
	}

	public static void showMatches(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		char fill = '_';
		char[] region = new char[text.length()];
		Arrays.fill(region, fill);

		int index = 0;
		int count = 0;

		while (index < text.length() && matcher.find(index)) {
			fill = Character.forDigit(count % 36, 36);
			fill = Character.toUpperCase(fill);

			Arrays.fill(region, matcher.start(), matcher.end(), fill);

			if (matcher.start() == matcher.end()) {
				region[matcher.start()] = '\u2024';
				index++;
			}
			else {
				index = matcher.end();
			}

			count++;
		}

		console.println(text);
		console.println(region);
	}

	public static void main(String[] args) {
		String text = """
				hello	WORLD
				good   morning
				""";

		console.println("Original:");
		console.println(text);

		console.println("Replaced:");
		console.println(replaceSymbols(text));

		printMatches(sample, "(?s)\\w+");
		console.println();

		showMatches(sample, "s");
		showMatches(sample, "s*");
	}
}
