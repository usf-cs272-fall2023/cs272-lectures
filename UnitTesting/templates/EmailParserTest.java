package edu.usfca.cs272;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Assertions;

public class EmailParserTest {

	// TODO

	public class SimpleTest {
		private EmailParser parser;
		private String email;
		private String local;
		private String domain;
		private String tld;

		public void setupTest(String email, String local, String domain, String tld) throws URISyntaxException {
			this.email = email;
			this.local = local;
			this.domain = domain;
			this.tld = tld;

			this.parser = new EmailParser(this.email);
		}

		public void setup() throws URISyntaxException {
			setupTest("simple@example.com", "simple", "example.com", "com");
		}

		public void testLocal() {
			String expect = local;
			String actual = parser.local;
			Assertions.assertEquals(expect, actual);
		}

		public void testDomain() {
			String expect = domain;
			String actual = parser.domain;
			Assertions.assertEquals(expect, actual, parser.toString());
		}

		public void testTLD() {
			String expect = tld;
			String actual = parser.tld;
			Assertions.assertEquals(expect, actual, () -> parser.toString());
		}
	}


//		@ValueSource(strings = { "email@example.com", "email@subdomain.example.com",
//				"first.last@example.org", "first-last@example.net",
//				"first+last@example.net", "1234567890@example.com", "a@example.com",
//				"email@localhost" })

//		@ValueSource(strings = { "", "hello", "example.com", "@example.com",
//				"example@", "a@b@c", "@" })

}
