package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class SerialDirectoryListing {
	public static Set<Path> list(Path path) throws IOException {
		Set<Path> paths = new HashSet<>();

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				list(path, paths);
			}
		}

		return paths;
	}

	private static void list(Path path, Set<Path> paths) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path current : stream) {
				paths.add(current);

				if (Files.isDirectory(current)) {
					list(current, paths);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		list(Path.of(".")).stream().forEach(System.out::println);
	}
}
