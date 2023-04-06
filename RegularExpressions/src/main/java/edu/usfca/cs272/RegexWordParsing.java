package edu.usfca.cs272;

/**
 * Demonstrates basic word matching in regular expressions.
 *
 * @see RegexHelper
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class RegexWordParsing extends RegexHelper {
	/**
	 * Demonstrates basic word matching in regular expressions.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String text = sample;
		String regex = "";

		// TODO: Ask class: what is the (first, last) match?

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2_____3__4___5_______6____7___8_________
		// Note: The underscore does not start a new word.
		regex = "\\b\\w";
		console.println("Start of Word: " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ____0___1_____2__3___4______5___6_____7_________8_
		// Note: The dash and punctuation (except underscore) ends words.
		regex = "\\w\\b";
		console.println("End of Word: " + regex);
		showMatches(text, regex);
		console.println();

		// TODO: How to match words using boundaries?

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0000000000000000000000000000000000000000000000000_
		// Note: Does not work; too greedy.
		regex = "\\b.+\\b";
		console.println("Words (Using Boundaries, Greedy): " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0000000000000000000000000000000000000000000000000_
		// Note: Does not work; matching spaces and symbols.
		regex = "\\b.+?\\b";
		console.println("Words (Using Boundaries, Reluctant): " + regex);
		showMatches(text, regex);
		console.println();

		// TODO: Use \w word character class instead.

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_444_555555__66___777_888888888_
		// Note: Matches do not include spaces or punctuation.
		regex = "\\b\\w+\\b";
		console.println("Words (Sans Hyphen): " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_444_555555__66___777_888888888_
		// Note: Same affect as before, simpler regex.
		regex = "\\w+";
		console.println("Words (Sans Hyphen): " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_4444444444__55___666_777777777_
		// Note: The dash and underscore are included in the matches.
		regex = "\\b\\S+\\b";
		console.println("Words (With Hyphen): " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222_33_44444444444_55___666_7777777777
		// Note: The simpler case (without \b) includes punctuation.
		regex = "\\S+";
		console.println("Words (With Symbol): " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 00000_111_22222____3333333333___________444444444_
		// Note: Matches any "word" that starts with "s"
		regex = "\\b[sS]\\S+\\b";
		console.println("Words that start with S: " + regex);
		showMatches(text, regex);
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ______000_11111____2222222222_______333_444444444_
		// Note: Matches any "word" that contains an "e" within.
		regex = "\\b\\S*e\\S*\\b";
		console.println("Words that contain an e: " + regex);
		showMatches(text, regex);

		// Note: try not to match greedy vs reluctant quantifiers in same regex
		// Note: try to be more specific with classes and use greedy quantifiers
		// over excessively using reluctant quantifiers
	}
}
