package edu.usfca.cs272;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.function.Predicate;

/**
 * Demonstrates lambda expressions using the {@link PathMatcher} functional
 * interface.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class PathMatcherDemo {
	/**
	 * Matches paths that end with .txt.
	 */
	public static class NestedMatcher implements PathMatcher {
		@Override
		public boolean matches(Path path) {
			return path.toString().endsWith(".txt");
		}
	}

	/**
	 * Demonstrates the examples in the lambda expression lecture slides.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Path hello = Path.of("hello.txt");
		Path world = Path.of("txt.world");

		// static nested class
		PathMatcher nestedClass = new NestedMatcher();
		System.out.println(hello + ": " + nestedClass.matches(hello));
		System.out.println(world + ": " + nestedClass.matches(world));
		System.out.println();

		// anonymous inner class
		PathMatcher anonClass = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(hello + ": " + anonClass.matches(hello));
		System.out.println(world + ": " + anonClass.matches(world));
		System.out.println();

		// verbose lambda expression (anonymous method)
		PathMatcher verboseLambda = (Path p) -> { return p.toString().endsWith(".txt"); };

		// access anonymous method through reference (reference determines name used)
		System.out.println(hello + ": " + verboseLambda.matches(hello));
		System.out.println(world + ": " + verboseLambda.matches(world));
		System.out.println();

		// compact lambda expression
		PathMatcher compactLambda = p -> p.toString().endsWith(".txt");

		System.out.println(hello + ": " + compactLambda.matches(hello));
		System.out.println(world + ": " + compactLambda.matches(world));
		System.out.println();

		// changing the identifier type
		// only the function "shape" matters (not the name)
		Predicate<Path> predicateLambda = p -> p.toString().endsWith(".txt");

		// note change in how access the method (name changed)
		System.out.println(hello + ": " + predicateLambda.test(hello));
		System.out.println(world + ": " + predicateLambda.test(world));
		System.out.println();

		// can the var keyword be used for anonymous inner classes?
		// yes, the type is given by the constructor call
		var varAnonClass = new PathMatcher() {
			@Override
			public boolean matches(Path p) {
				return p.toString().endsWith(".txt");
			}
		};

		System.out.println(hello + ": " + varAnonClass.matches(hello));
		System.out.println(world + ": " + varAnonClass.matches(world));
		System.out.println();

		// can the var keyword be used for lambda expressions?
		// no, lambda expressions have no standalone type and require a target type
		// https://stackoverflow.com/questions/49578553/why-cant-the-var-keyword-in-java-be-assigned-a-lambda-expression

//		var varLambda = p -> p.toString().endsWith(".txt");
//
//		System.out.println(hello + ": " + varLambda.test(hello));
//		System.out.println(world + ": " + varLambda.test(world));
//		System.out.println();
	}
}
