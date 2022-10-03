package edu.usfca.cs272;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexGroups {
	/*
	 * TODO: First write regex in tool, then show Java code.
	 *
	 *    Start: https://regex101.com/r/Dusf9V/
	 * Finished: https://regex101.com/r/2F5Rom/
	 */

	public static void main(String[] args) {
		String email = "username@dons.usfca.edu";

		String html = """
				<a href="mailto:username@dons.usfca.edu">user@example.com</a>
				""";

		System.out.println(email);
		System.out.println();

		String regex = "(.*)";

		System.out.println(regex);
		System.out.println();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		// System.out.printf("Group #%d: %s%n", i, matcher.group(i));
		// TODO

		System.out.println();
		System.out.println(html);

		matcher = pattern.matcher(html);
		// TODO
	}
}
