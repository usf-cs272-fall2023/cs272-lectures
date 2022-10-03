package edu.usfca.cs272;

public class RegexQuantifiers extends RegexHelper {
	public static void main(String[] args) {
		String text = "aardvark";
		String regex;

		regex = "a";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);
	}
}
