package edu.usfca.cs272;

import java.nio.file.Path;
import java.nio.file.PathMatcher;

public class PathMatcherDemo {
	public static void main(String[] args) {
		Path hello = Path.of("hello.txt");
		Path world = Path.of("txt.world");

		PathMatcher anonClass = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(hello + ": " + anonClass.matches(hello));
		System.out.println(world + ": " + anonClass.matches(world));
		System.out.println();

		/*
		 *
		 * TODO
		 *
		 */

//		PathMatcher verboseLambda = null;

//		System.out.println(hello + ": " + verboseLambda.matches(hello));
//		System.out.println(world + ": " + verboseLambda.matches(world));
//		System.out.println();

//		PathMatcher compactLambda = null;

//		System.out.println(hello + ": " + compactLambda.matches(hello));
//		System.out.println(world + ": " + compactLambda.matches(world));
//		System.out.println();

//		Predicate<Path> predicateLambda = null;

//		System.out.println(hello + ": " + predicateLambda.test(hello));
//		System.out.println(world + ": " + predicateLambda.test(world));
//		System.out.println();

//		System.out.println(hello + ": " + varAnonClass.matches(hello));
//		System.out.println(world + ": " + varAnonClass.matches(world));
//		System.out.println();
//
//		System.out.println(hello + ": " + varLambda.test(hello));
//		System.out.println(world + ": " + varLambda.test(world));
//		System.out.println();
	}
}
