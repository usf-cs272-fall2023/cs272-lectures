package edu.usfca.cs272;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpsFetcher {
	public static Map<String, List<String>> fetchUrl(URL url) throws Exception {
		try (
				AutoCloseable placeholder = System.in; // TODO Fix fetchURL resources block
		) {
			// TODO Fill in fetchURL try block
			Map<String, List<String>> headers = new HashMap<>();
			return headers;
		}
	}

	public static Map<String, List<String>> fetchUrl(String url) throws Exception {
		return fetchUrl(new URL(url));
	}

	public static Socket openConnection(URL url) throws Exception {
		return null; // TODO Fill in openConnection
	}

	public static void printGetRequest(PrintWriter writer, URL url) throws Exception {
		return; // TODO Fill in printGetRequest
	}

	public static Map<String, List<String>> getHeaderFields(BufferedReader response) throws Exception {
		Map<String, List<String>> results = new HashMap<>();
		// TODO Fill in getHeaderFields
		return results;
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