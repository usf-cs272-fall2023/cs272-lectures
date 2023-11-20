package edu.usfca.cs272;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses an email into its local, domain, and top-level domain components. Does
 * not do full email validation. Demonstrates unit testing.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class EmailParser {
	/** Reconstructed email. */
	public final String email;

	/** Local component of email; appears before the "@" at sign. */
	public final String local;

	/** Domain component of the email; appears after the "@" at sign. */
	public final String domain;

	/** Top-level domain component of the domain; similar to a file extension. */
	public final String tld;

	/**
	 * Initializes each component of the specified email.
	 *
	 * @param email email to parse into components
	 * @throws URISyntaxException when the email cannot be parsed
	 */
	public EmailParser(String email) throws URISyntaxException {
		// There are many possible regular expressions.
		// Note: Validating RFC-compliant emails are pretty hard :(

//		 String regex = "(.+)@(.+)"; // (fails because no group for tld)
//		 String regex = "(.+)@(.+(\\..*))"; // (fails because period in tld)
//		 String regex = "(.+)@(.+\\.(.*))"; // (fails no period in domain test)
//		 String regex = "(.+?)@(.+?(?:\\.([^.]+?))?)"; // (fails multiple @ symbols test)

		String regex = "([^@]+)@([^@]+?(?:\\.([^@.]+))?)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new URISyntaxException(email, "Unable to parse email.");
		}

		this.email = matcher.group(0);
		this.local = matcher.group(1);
		this.domain = matcher.group(2);
		this.tld = matcher.group(3);
	}

	@Override
	public String toString() {
		String format = "Email: %s, Local: %s, Domain: %s, TLD: %s";
		return format.formatted(email, local, domain, tld);
	}
}
