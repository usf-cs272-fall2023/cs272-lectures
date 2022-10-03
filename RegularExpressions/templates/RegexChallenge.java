package edu.usfca.cs272;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexChallenge extends RegexHelper {
	public static void main(String[] args) {
		String text = null;
		String regex = null;

		text = "hubbub";
		regex = ".*";

		printMatches(text, regex);
		console.println();

//		text = "ant ape bat bee bug cat cow dog";
//		printMatches(text, ".*");
//		console.println();

//		text = "dragonfly";
//		regex = ".*";
//		printGroups(regex, text);

		// https://regex101.com/r/ayaRps/
//		regex = "(?i).+?[+?]?(?:\\?[^?])+?";
//		printMatches("hello", regex);
	}

	public static void printGroups(String regex, String text) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);

		System.out.println(" Text: " + text);
		System.out.println("Regex: " + regex);
		System.out.println();

		// TODO
		System.out.println(m.hashCode());

		System.out.println();
	}

}
