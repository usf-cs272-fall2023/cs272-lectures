package edu.usfca.cs272;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailParser {
	public final String email;
	public final String local;
	public final String domain;
	public final String tld;

	public EmailParser(String email) throws URISyntaxException {
		String regex = "(.+)@(.+)\\.(.+)";

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
