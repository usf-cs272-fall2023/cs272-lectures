package edu.usfca.cs272;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlFetcher {
	public static Map<String, List<String>> fetchUrl(URL url) throws Exception {
		Map<String, List<String>> results = new HashMap<>();

		try (
				AutoCloseable placeholder = System.in; // TODO Fix fetchURL resources block
		) {
			// TODO Fill in fetchURL try block
		}
		catch (IOException e) {
			// TODO Fill in fetchURL catch block
		}

		return results;
	}

	public static Map<String, List<String>> fetchUrl(String url) throws Exception {
		return fetchUrl(new URL(url));
	}

	public static void main(String[] args) throws Exception {
		String[] urls = new String[] { "http://www.cs.usfca.edu/",
				"https://www.cs.usfca.edu/",
				"https://www.cs.usfca.edu/~cs272/",
				"https://www.cs.usfca.edu/~cs272/simple/double_extension.html.txt",
				"https://www.cs.usfca.edu/~cs272/nowhere.html"
		};

		for (String url : urls) {
			System.out.println(url);

			var results = fetchUrl(url);

			for (var entry : results.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}

			System.out.println();
		}
	}
}
