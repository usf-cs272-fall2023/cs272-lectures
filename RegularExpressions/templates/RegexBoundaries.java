package edu.usfca.cs272;

public class RegexBoundaries extends RegexHelper {
	public static void main(String[] args) {
		// https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html

		String text = "Knock knock!\nWho's there?";
		console.println(text);
		console.println();

		printMatches(text, ".*");
		console.println();
	}
}
