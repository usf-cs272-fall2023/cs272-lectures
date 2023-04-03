package edu.usfca.cs272;

public class RegexBoundaries extends RegexHelper {
	public static void main(String[] args) {
		// https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/util/regex/Pattern.html

		String text = "Knock knock!\nWho's there?";
		console.println(text);
		console.println();

		printMatches(text, ".*");
		console.println();
	}
}
