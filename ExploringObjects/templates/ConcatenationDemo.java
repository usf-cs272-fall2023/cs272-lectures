package edu.usfca.cs272;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConcatenationDemo {
	public static void main(String[] args) throws IOException {
		Path path = Path.of("src", "main", "resources", "lines.txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		lines.forEach(System.out::println);
		System.out.println();
		
		String string = "";
		StringBuilder builder = new StringBuilder();

		Set<Integer> strings = new HashSet<>();
		Set<Integer> builders = new HashSet<>();

		int copies = 0;

		String format = "%-10s  x%08X  %3d  x%08x  %3d%n";
		String summary = "%10s   %8s  %3d   %8s  %3d%n";
		System.out.println("LINE        STRING       #  BUILDER      #");
		System.out.println("----------  --------------  --------------");

		for (String line : lines) {
			// TODO Fill in

			System.out.printf(format, line,
					System.identityHashCode(string), string.length(),
					System.identityHashCode(builder), builder.length());
			
			break; // TODO Remove
		}

		System.out.println("----------  --------------  --------------");
		System.out.printf(summary, "COPIED", "", copies, "", builder.length());
		System.out.printf(summary, "OBJECTS", "", strings.size(), "", builders.size());
	}
}
