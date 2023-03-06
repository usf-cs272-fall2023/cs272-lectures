package edu.usfca.cs272;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharacterCounter {
	private static int SIZE = 1000;

	public static int count(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			// TODO
		}
		catch (IOException e) {

		}

		return total;
	}

	public static int debugCount(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			// TODO
		}
		catch (IOException e) {

		}

		return total;
	}

	public static void main(String[] args) throws IOException {
		Path readme = Path.of("README.md");

		System.out.println("    Path: " + readme.toString());
		System.out.println("  Actual: " + count(readme));
		System.out.println("Expected: " + Files.size(readme));
		System.out.println();

		Path nowhere = Path.of("nowhere");

		System.out.println("    Path: " + nowhere.toString());
		System.out.println("  Actual: " + count(nowhere));

		try {
			System.out.print("Expected: ");
			System.out.print(Files.size(nowhere));
		}
		catch (IOException e) {
			System.err.print(e.toString());
			System.out.println("\n");
		}

		// TODO
//		try {
//			System.out.println("Check Assertions:");
//			assert false : "hello world";
//		}
//		catch (AssertionError e) {
//			System.err.println(e.toString());
//		}
//		finally {
//			System.out.println();
//		}

		System.out.println(debugCount(readme));
		System.out.println();

		System.out.println(debugCount(nowhere));
		System.out.println();
	}
}
