package edu.usfca.cs272;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStreams {
	public static final Pattern HTML_TAG = Pattern.compile("</?(\\w+)>");

	public static String lowercaseTags(String html) {
		Matcher matcher = HTML_TAG.matcher(html);
		return null; // TODO
	}

	public static Set<String> collectTags(String html) {
		Matcher matcher = HTML_TAG.matcher(html);
		return null; // TODO
	}

	public static List<String> withoutTags(List<String> lines) {
		return null; // TODO
	}

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

		System.out.println(withoutTags(html.lines().toList()));
		System.out.println();
	}
}
