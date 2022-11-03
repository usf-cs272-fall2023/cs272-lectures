package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates using a {@link URLConnection} to fetch the headers and content
 * from a URL on the web.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class UrlFetcher {
	// https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers

	/**
	 * Fetches the headers and content for the specified URL. The content is
	 * placed as a list of all the lines fetched under the "Content" key.
	 *
	 * @param url the url to fetch
	 * @return a map with the headers and content
	 * @throws IOException if unable to fetch headers and content
	 */
	public static Map<String, List<String>> fetchUrl(URL url) throws IOException {
		// used to store all headers and content
		Map<String, List<String>> results = new HashMap<>();

		// connection to url
		URLConnection urlConnection = url.openConnection();

		// HttpURLConnection will follow redirects (within same protocol) by default
		// this is the connection usually underlying URLConnection
		HttpURLConnection.setFollowRedirects(false);

		// close connection instead of keep-alive
		urlConnection.setRequestProperty("Connection", "close");

		// get all of the headers
		// note the status code will be placed with a "null" key
		results.putAll(urlConnection.getHeaderFields());

		try (
				// need more steps to create a stream from the url connection than from a file
				InputStreamReader input = new InputStreamReader(urlConnection.getInputStream(), UTF_8);
				BufferedReader reader = new BufferedReader(input);
		) {
			// remainder of stream will be all of the content
			List<String> content = reader.lines().toList();
			results.put("Content", content);
		}
		catch (IOException e) {
			results.put("Content", Collections.emptyList());
		}

		return results;
	}

	/**
	 * See {@link #fetchUrl(URL)} for details.
	 *
	 * @param url the url to fetch
	 * @return a map with the headers and content
	 * @throws MalformedURLException if unable to convert String to URL
	 * @throws IOException if unable to fetch headers and content
	 *
	 * @see #fetchUrl(URL)
	 */
	public static Map<String, List<String>> fetchUrl(String url) throws MalformedURLException, IOException {
		return fetchUrl(new URL(url));
	}

	/**
	 * Demonstrates the {@link #fetchUrl(URL)} method.
	 *
	 * @param args unused
	 * @throws Exception if unable to fetch url
	 */
	public static void main(String[] args) throws Exception {
		String[] urls = new String[] { "http://www.cs.usfca.edu/", // 302 -> https
				"https://www.cs.usfca.edu/", // 302 -> myusf
				"https://www.cs.usfca.edu/~cs272/", // 200
				"https://www.cs.usfca.edu/~cs272/simple/double_extension.html.txt", // text/plain
				"https://www.cs.usfca.edu/~cs272/nowhere.html" // 404
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
