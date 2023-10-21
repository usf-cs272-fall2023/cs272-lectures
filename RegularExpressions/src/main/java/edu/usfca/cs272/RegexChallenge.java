package edu.usfca.cs272;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tests how well you combine different regex components.
 *
 * @see RegexHelper
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class RegexChallenge extends RegexHelper {
	/**
	 * Tests how well you combine different regex components.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String text = null;
		String regex = null;

		/*
		 * Can you predict the output?
		 */

		text = "hubbub";
		printMatches(text, "[hb].*b");
		printMatches(text, "[hb].*?b");
		printMatches(text, "[hb].*bb*");
		printMatches(text, "[hb].*bb+");
		printMatches(text, "[hb].*bb?");
		printMatches(text, "[hb].*?bb??");

		console.println();

		/*
		 * Can you predict the output?
		 */

		text = "ant ape bat bee bug cat cow dog";
		printMatches(text, "\\w*a\\w*");
		printMatches(text, "\\w+a\\w+");
		printMatches(text, "\\w+t\\b");
		printMatches(text, "\\w*[^e]e\\b");

		console.println();

		/*
		 * Can you predict the output?
		 */

		text = "dragonfly";

		regex = "(drag(on))(fly)";
		printGroups(regex, text);

		regex = "(drag((on)(fly)))";
		printGroups(regex, text);

		/*
		 * Can you find other matches for this regex?
		 */

		// https://regex101.com/r/ayaRps/
		regex = "(?i).+?[+?]?(?:\\?[^?])+?";

		// create smallest text that matches this regex
		printMatches("???!", regex);
		printMatches("++?+", regex);
		printMatches("o+?o", regex);

		// create 10 char text that matches this regex
		printMatches("ahhhhh???!", regex);
		printMatches("?????????!", regex);
	}

	/**
	 * Outputs groups to the console.
	 *
	 * @param regex the regular expression
	 * @param text the text to match against
	 */
	public static void printGroups(String regex, String text) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		System.out.println(" Text: " + text);
		System.out.println("Regex: " + regex);
		System.out.println();

		if (m.matches()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				console.println(i + ": " + m.group(i));
			}
		}

		System.out.println();
	}
}
