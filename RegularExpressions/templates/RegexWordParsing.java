package edu.usfca.cs272;

public class RegexWordParsing extends RegexHelper {
	public static void main(String[] args) {
		String text = sample;
		String regex = "";

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		console.println(RegexHelper.sample);
		console.println();

		// TODO: What is the (first, last) match?
		// e.g. "Sally, shore"

		// The character class \b matches a "word boundary"
		// e.g. caused by a non-word character or start or end of String

		regex = "\\b\\w";
		console.println("Regex: " + regex);
		showMatches(text, regex);
		console.println();
	}
}
