package edu.usfca.cs272;

/**
 * Designed to catch older Java installations by using new Java features.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class VersionCheck {
	/**
	 * Method called on run.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// Switch Expressions, available from Java 14+.
		String name = switch (args.length) {
			case 0:
				yield "anonymous";
			default:
				yield args[1];
		};

		System.out.println("Hello, " + name + "!");

		// Multiple-line String, available from Java 15+.
		String hello = """
				Hello,
				World!
				""";

		System.out.println(hello);

		// Record types, available from Java 16+.
		record Person(String name) {
		}

		Person person = new Person(name);

		System.out.println("Hello, " + person.name() + "!");

		// Sealed class, available from Java 17+.
		Shape shape = new Circle();
		shape.hello();
	}

	/** Sealed class, available from Java 17+. */
	public static sealed class Shape {
		/** Outputs a message. */
		public void hello() {
			System.out.println("Hi there!");
		}
	}

	/** Sealed class, available from Java 17+. */
	public static final class Circle extends Shape {
	}
}
