package edu.usfca.cs272;

/**
 * Demonstrates basic character classes in regular expressions.
 *
 * @see RegexHelper
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class RegexClasses extends RegexHelper {
	/**
	 * Demonstrates basic character classes in regular expressions.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// __________0___1____2___3____4___________5___6_____
		console.println("Lowercase s");
		showMatches(sample, "s");
		console.println();

		// TODO: Ask class: which word contains the first match?

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2___3____4___5____6___________7___8_____
		console.println("Lowercase or Uppercase [sS]");
		showMatches(sample, "[sS]");
		console.println();

		// TODO: Ask class: which word contains the first match?

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___2___3____4___5____6___________7___8_____
		console.println("Lowercase or Uppercase (?i)s");
		showMatches(sample, "(?i)s");
		console.println();

		/*
		 * The above example shows how to use the (?i) flag in a regex.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// _0123__45_6789A____BCD_EFGHIJ__KL___MNO_PQR_STUVW_
		console.println("Lowercase Letters [a-z]");
		showMatches(sample, "[a-z]");
		console.println();

		// TODO: Ask class: what is the first matching letter?
		// TODO: Ask class: what is the last  matching letter?

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0_____1___________________________________________
		console.println("Uppercase  Letters \\p{Upper}");
		showMatches(sample, "\\p{Upper}");
		console.println();

		// TODO: Ask class: what is the first matching letter?

		/*
		 * As demonstrated above, there are several ways to specify equivalent
		 * character classes.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// ________________01________________________________
		console.println("Digit Characters \\d");
		showMatches(sample, "\\d");
		console.println();

		// TODO: Ask class: what is the first match?

		/*
		 * Notice in digit output above that each digit 7 and 6 are individual
		 * matches.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 01234_567_89ABC_DE_FGH_IJKLMN__OP___QRS_TUVWXYZ01_
		console.println("Word Characters \\w");
		showMatches(sample, "\\w");
		console.println();

		// TODO: Ask class: will the "," character match?
		// TODO: Ask class: will the "_" character match?

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 01234_567_89ABC_DE_FGHIJKLMNOP_QR___STU_VWXYZ01234
		console.println("Non-Whitespace Characters \\S");
		showMatches(sample, "\\S");
		console.println();

		// TODO: Ask class: will the "," character match?
		// TODO: Ask class: will the "_" character match?

		/*
		 * Notice difference between word and non-whitespace characters above is
		 * whether the symbols match.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// _____0___1_____2__3___________4__567___8__________
		console.println("Whitespaces \\s");
		showMatches(sample, "\\s");
		console.println();

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// _____0___1_____2__3___4______56__789___A_________B
		console.println("Non-Word Characters \\W");
		showMatches(sample, "\\W");
		console.println();

		// TODO: Ask class: will the "," character match?
		// TODO: Ask class: will the "_" character match?

		/*
		 * Notice difference between whitespace and non-word characters above is
		 * whether the symbols match.
		 */

		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		// 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCD
		console.println("Any Character .");
		showMatches(sample, ".");
	}
}
