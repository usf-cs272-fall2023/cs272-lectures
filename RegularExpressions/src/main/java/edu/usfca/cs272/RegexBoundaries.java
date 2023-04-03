package edu.usfca.cs272;

/**
 * Demonstrates basic boundary matching in regular expressions. Shows how the
 * MULTILINE (?m) and DOTALL (?s) flags changes the results.
 *
 * @see RegexHelper
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class RegexBoundaries extends RegexHelper {
	/**
	 * Demonstrates basic boundary matching in regular expressions.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// https://www.cs.usfca.edu/~cs272/javadoc/api/java.base/java/util/regex/Pattern.html

		// Example string for testing regular expressions.
		String text = "Knock knock!\nWho's there?";
		console.println(text);

		console.println();
		console.println("Input Boundary \\A and \\z");

		// \A The beginning of the input
		// \Z The end of the input but for the final terminator, if any
		// \z The end of the input

		// . Any character (may or may not match line terminators)

		// .* will not match \n
		// since no single-line input, does not match anything
		printMatches(text, "\\A.*\\z");

		// In dotall mode, the expression . matches any character, including a line terminator.
		// By default this expression does not match line terminators.
		// (The s is a mnemonic for "single-line" mode, which is what this is called in Perl.)

		// .* will match \n
		// the (?s) flag enables matching \n when using "."
		printMatches(text, "(?s)\\A.*\\z");

		console.println();
		console.println("Line Boundary ^ and $");

		// ^ The beginning of a line
		// $ The end of a line

		// .* will not match \n
		// []
		printMatches(text, "^.*$");

		// In multiline mode the expressions ^ and $ match just after or just before, respectively, a line terminator or the end of the input sequence.
		// By default these expressions only match at the beginning and the end of the entire input sequence.

		// ^$ will look at individual lines
		// the (?m) flag enables multiline mode
		// [Knock knock!, Who's there?]
		printMatches(text, "(?m)^.*$");

		// .* will match \n
		// [Knock knock!\nWho's there?]
		printMatches(text, "(?s)^.*$");

		// Usually choose either m or s flags, but can combine:

		// greedy, matches everything
		// [Knock knock!\nWho's there?]
		printMatches(text, "(?ms)^.*$");

		// reluctant, matches each line
		// [Knock knock!, Who's there?]
		printMatches(text, "(?ms)^.*?$");
	}
}
