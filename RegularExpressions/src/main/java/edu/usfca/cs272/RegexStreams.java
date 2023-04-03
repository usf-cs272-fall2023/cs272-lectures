package edu.usfca.cs272;

import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Demonstrates how to use streams and lambda functions with regular expressions
 * to replace substrings.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class RegexStreams {
	/**
	 * A simple regular expression to find HTML tags. Not robust enough to catch all
	 * valid cases!
	 */
	public static final Pattern HTML_TAG = Pattern.compile("</?(\\w+)>");

	/**
	 * Converts found HTML tags to lowercase using a lambda expression.
	 *
	 * @param html the html code to search through
	 * @return html code with the found tags converted to lowercase
	 */
	public static String lowercaseTags(String html) {
		Matcher matcher = HTML_TAG.matcher(html);
		return matcher.replaceAll(result -> result.group().toLowerCase());
	}

	/**
	 * Collects a set of simple HTML tags found using regular expressions and a
	 * stream of {@link MatchResult} objects.
	 *
	 * @param html the html code to search through
	 * @return a set of the html tags found
	 */
	public static Set<String> collectTags(String html) {
		Matcher matcher = HTML_TAG.matcher(html);

		return matcher.results() // stream through results
				.map(result -> result.group(1).toLowerCase()) // get match as lowercase
				.collect(Collectors.toSet()); // store as set
	}

	/**
	 * Filters out lines that contain any HTML tags using a regular expression as a
	 * predicate.
	 *
	 * @param lines individual HTML lines to filter
	 * @return lines that contained a closing HTML tag
	 */
	public static List<String> withoutTags(List<String> lines) {
		return lines.stream().filter(HTML_TAG.asPredicate().negate()) // not html tag
				.map(String::strip) // remove whitespace
				.toList();
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String html = """
				<HTML>
				  <BODY>
				    <P>
				      Hello WORLD!
				    </P>
				    <UL>
				      <LI>Item 1
				      <LI>Item 1</LI>
				    </UL>
				  </BODY>
				</HTML>
				<html>
				  <body>
				    <p>
				      Hello WORLD!
				    </p>
				    <ul>
				      <li>Item 1
				      <li>Item 1</li>
				    </ul>
				  </body>
				</html>
				""";

		System.out.println(html);
		System.out.println();

		System.out.println(lowercaseTags(html));
		System.out.println();

		System.out.println(collectTags(html));
		System.out.println();

		System.out.println(withoutTags(html.lines().collect(Collectors.toList())));
		System.out.println();
	}
}
