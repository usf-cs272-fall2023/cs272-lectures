package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathDemo {
	private static final String format = "%22s: %s%n";

	public static void pathDetails(Path path) {
		System.out.printf(format, "toString()", path.toString());
		System.out.printf(format, "isAbsolute()", path.isAbsolute());
		System.out.printf(format, "getParent()", path.getParent());
		System.out.printf(format, "getRoot()", path.getRoot());
		System.out.printf(format, "getFileName()", path.getFileName());
		System.out.printf(format, "normalize()", path.normalize());
		System.out.printf(format, "toAbsolutePath()", path.toAbsolutePath());
		System.out.printf(format, "toUri()", path.toUri());
		System.out.println();
	}

	public static void fileDetails(Path path) throws IOException {
		System.out.printf(format, "toString()", path.toString());

		System.out.printf(format, "size()", Files.size(path));
		System.out.printf(format, "getOwner()", Files.getOwner(path));
		System.out.printf(format, "getLastModifiedTime()", Files.getLastModifiedTime(path));

		System.out.printf(format, "exists()", Files.exists(path));
		System.out.printf(format, "isDirectory()", Files.isDirectory(path));
		System.out.printf(format, "isHidden()", Files.isHidden(path));

		System.out.printf(format, "isExecutable()", Files.isExecutable(path));
		System.out.printf(format, "isReadable()", Files.isReadable(path));
		System.out.printf(format, "isWritable()", Files.isWritable(path));
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		Path relativeDirectory = Path.of(".");
		pathDetails(relativeDirectory);

//		Path absoluteDirectory = relativeDirectory.toAbsolutePath();
//		pathDetails(absoluteDirectory);
//
//		Path normalizedDirectory = absoluteDirectory.normalize();
//		pathDetails(normalizedDirectory);
//
//		Path srcDirectory = Path.of(".", "src", "main", "java");
//		pathDetails(srcDirectory);
//
//		Path cs272Directory = Path.of("edu", "usfca", "cs272");
//
//		Path fileRelativePath = srcDirectory
//				.resolve(cs272Directory)
//				.resolve(PathDemo.class.getSimpleName() + ".java");
//		pathDetails(fileRelativePath);
//
//		Path fileCanonicalPath = fileRelativePath.toAbsolutePath().normalize();
//		pathDetails(fileCanonicalPath);
//
//		System.out.printf(format, "getRoot()", fileCanonicalPath.getRoot());
//		System.out.printf(format, "getNameCount()", fileCanonicalPath.getNameCount());
//		System.out.printf(format, "getName(0)", fileCanonicalPath.getName(0));
//		System.out.printf(format, "subpath(1, 3)", fileCanonicalPath.subpath(1, 3));
//		System.out.println();
//
//		fileDetails(normalizedDirectory);
//		fileDetails(fileCanonicalPath);
	}
}
