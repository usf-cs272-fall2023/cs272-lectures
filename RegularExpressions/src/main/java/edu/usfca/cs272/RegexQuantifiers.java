package edu.usfca.cs272;

/**
 * Demonstrates differences between quantifiers.
 *
 * @see RegexHelper
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class RegexQuantifiers extends RegexHelper {
	/**
	 * Demonstrates differences between quantifiers.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		String text = "aardvark";
		String regex;

		// TODO: Ask class: what is the first match?
		// TODO: How many matches are there?

		/*
		 * Basic greedy quantifiers.
		 */

		// aardvark
		// 01___2__
		regex = "a";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// 00***4**
		regex = "a*";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// 00___1__
		regex = "a+";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// 00______
		regex = "a{2}";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		/*
		 * Demonstrate reluctant.
		 */

		// aardvark
		// 0000000_
		regex = "a.+r";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// 000_____
		regex = "a.+?r";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		/*
		 * Demonstrate possessive.
		 */

		// aardvark
		// 00000000
		regex = "[^k]+k";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// 00000000
		regex = "[^k]++k";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// 0000____
		regex = "[^k]+d";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// aardvark
		// ________
		regex = "[^k]++d";
		console.println("\nRegex: " + regex);
		showMatches(text, regex);

		// Possessive is trying to match entire string, no backtracking
		// is done if entire string does not match.
	}
}
