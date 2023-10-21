package edu.usfca.cs272;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A quick demo of how to use groups in regular expressions.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class RegexGroups extends RegexHelper {
	/*
	 * TODO: First write regex in tool, then show Java code.
	 *
	 *    Start: https://regex101.com/r/Dusf9V/
	 * Finished: https://regex101.com/r/2F5Rom/
	 */

	/**
	 * Demonstrates how to extract different groups from a regular expression. Not
	 * meant to be a good regular expression for emails though! It breaks for some
	 * special cases.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// A simple test email. Keeping it simple to focus on groups.
		String email = "username@dons.usfca.edu";

		String html = """
				<a href="mailto:username@dons.usfca.edu">user@example.com</a>
				""";

		// A regular expression to demonstrate how to extract different groups.
		// (?i)(.+)@(.+\.(.+))
		// (?i)(\w+)@([.\w]+\.([\w]+))
		String regex = "(?i)(\\w+)@([.\\w]+\\.([\\w]+))";

		/*
		 * Notes: Group 0 is the entire match.
		 *
		 * The (?i) at the start does not contribute to group count. Any group that
		 * starts with a ? question mark is a non-capturing group.
		 *
		 * The first group, i.e. group 1, is the match for the first (.+).
		 *
		 * The @ at sign will not appear in any group (except group 0, which is the
		 * entire match).
		 *
		 * The second group is the match for (.+\\.(.+)) after the @ at sign.
		 *
		 * The third group is the nested group (.+) inside the second group. It will NOT
		 * include the last period (i.e. \\.).
		 */

		console.println(regex);
		console.println();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		console.println(email);
		console.println();

		if (matcher.matches()) { // matches against entire text (yes or no)
			for (int i = 0; i <= matcher.groupCount(); i++) {
				console.printf("Group #%d: %s%n", i, matcher.group(i));
			}
		}

		console.println();
		console.println(html);

		matcher = pattern.matcher(html);

		while (matcher.find()) { // finds matches within text (multiple matches)
			for (int i = 0; i <= matcher.groupCount(); i++) {
				console.printf("Group #%d: %s%n", i, matcher.group(i));
			}
			console.println();
		}
	}
}
