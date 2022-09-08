package edu.usfca.cs272;

public class ShapeDemo {
	private static final String format = "| %-8s | %-11s | %#010x | %-9s | %-15s | %6.2f |%n";
	private static final String divider = "|----------+-------------+------------+-----------+-----------------+--------|";

	private static void printInfo(String variable, Shape shape) {
		String name = shape.getClass().getName();
		int start = name.lastIndexOf(".");
		System.out.printf(format, variable, name.substring(start + 1), shape.hashCode(),
				shape.getName(), shape.toString(), shape.area());
	}

	private static void printHead() {
		System.out.println(divider);
		System.out.printf("| %-8s | %-11s | %-10s | %-9s | %-15s | %-6s |%n",
				"VARIABLE", "CLASS", "HASHCODE", "TYPE", "STRING", "AREA");
		System.out.println(divider);
	}

	public static void main(String[] args) {
		Shape.debug = false;
		printHead();

		Rectangle r1 = null;
		// System.out.printf(format, "r1", r1.getClass().getSimpleName(), r1.hashCode(),
		// r1.getName(), r1.toString(), r1.area());

		Rectangle r2 = null;
		// System.out.printf(format, "r2", r2.getClass().getSimpleName(),
		// r2.hashCode(),r2.getName(), r2.toString(), r2.area());

		Rectangle r3 = null;
		// System.out.printf(format, "r3", r3.getClass().getSimpleName(), r3.hashCode(),
		// r3.getName(), r3.toString(), r3.area());

		Square q1 = null;
		// System.out.printf(format, "q1", q1.getClass().getSimpleName(), q1.hashCode(),
		// q1.getName(), q1.toString(), q1.area());

		Square q2 = null;
		// printInfo("q2", q2);

		Shape s1 = null;
		// printInfo("s1", s1);

		// A s2 = null;
		// printInfo("s2", s2);

		Shape s3 = null;
		// printInfo("s3", s3);

		System.out.println(divider);
	}
}
