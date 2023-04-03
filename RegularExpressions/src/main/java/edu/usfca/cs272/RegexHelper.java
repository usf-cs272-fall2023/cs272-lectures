package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to help debug and understand regular expressions.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class RegexHelper {
	/** Example string for testing regular expressions. **/
	public static final String sample = "Sally Sue sells 76 sea-shells, by   the sea_shore.";

	/**
	 * Replacement for {@code System#out} that supports
	 * {@code StandardCharsets#UTF_8} characters.
	 */
	public static final PrintStream console = new PrintStream(System.out, true, UTF_8);

	/**
	 * Simple (but not most efficient) way to replace certain invisible control
	 * characters with a visible symbol.
	 *
	 * @param text the text to replace
	 * @return text with visible symbols to replace certain control characters
	 */
	public static String replaceSymbols(String text) {
		if (text.isEmpty()) {
			return "\u03B5";
		}

		return text.replaceAll("\n", "\u21B5") // ↵
				.replaceAll("\t", "\u00BB")        // »
				.replaceAll(" ", "\u00B7");        // ·
	}

	/**
	 * Prints all of the matches found in the provided text.
	 *
	 * @param text text to search in
	 * @param regex regular expression to search for
	 */
	public static void printMatches(String text, String regex) {
		var matches = getMatches(text, regex);
		console.printf("%12s -> %2d matches: %s %n", replaceSymbols(regex), matches.size(), matches);
	}

	/**
	 * Returns a list of all the matches found in the provided text.
	 *
	 * @param text text to search in
	 * @param regex regular expression to search for
	 * @return list of all matches found in text
	 */
	public static ArrayList<String> getMatches(String text, String regex) {
		ArrayList<String> matches = new ArrayList<String>();

		// create regex pattern and matcher
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		int index = 0;

		// keep going while found a match in text
		while (index < text.length() && matcher.find(index)) {

			// store matching substring with visible whitespace symbols
			String foundMatch = text.substring(matcher.start(), matcher.end());
			String visibleSymbols = replaceSymbols(foundMatch);
			matches.add(visibleSymbols);

			if (matcher.start() == matcher.end()) {
				// advance index if matched empty string
				index = matcher.end() + 1;
			}
			else {
				// otherwise start looking at end of last match
				index = matcher.end();
			}
		}

		return matches;
	}

	/**
	 * Prints out the matching groups underneath the original string.
	 *
	 * @param text text to search in
	 * @param regex regular expression to search for
	 */
	public static void showMatches(String text, String regex) {
		// create regex and matcher
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		// character array to show where matches are located
		char fill = '_';
		char[] region = new char[text.length()];
		Arrays.fill(region, fill);

		// used to track current match
		int index = 0;
		int count = 0;

		// keep going while found a match in text
		while (index < text.length() && matcher.find(index)) {
			// converts count to a character
			// will loop through 0-9 and then A-Z as necessary
			fill = Character.forDigit(count % 36, 36);
			fill = Character.toUpperCase(fill);

			// indicate region matched
			Arrays.fill(region, matcher.start(), matcher.end(), fill);

			// test if matched empty string
			if (matcher.start() == matcher.end()) {
				// indicate empty string at location was matched
				region[matcher.start()] = '\u2024';

				// move to next position to avoid infinite loop
				index++;
			}
			else {
				// otherwise start looking at end of last match
				index = matcher.end();
			}

			// increment count
			count++;
		}

		// print original string and matching regions
		console.println(text);
		console.println(region);
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String text = """
				hello	WORLD
				good   morning
				""";

		console.println("Original:");
		console.println(text);

		console.println("Replaced:");
		console.println(replaceSymbols(text));
		console.println();

		printMatches(sample, "(?s)\\w+");
		console.println();

		showMatches(sample, "s");
		console.println();

		showMatches(sample, "s*");
	}
}
