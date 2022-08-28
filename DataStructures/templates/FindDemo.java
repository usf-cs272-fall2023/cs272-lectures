package edu.usfca.cs272;

@SuppressWarnings("unused")
public class FindDemo {
	private static final String[] animals = new String[] {
			"cow", "ape", "dog", "bee", "boa", "cat", "ant", "bat" };

	private static final String[] letters = new String[] { "b", "be", "d", "e" };

	private static void inefficientDemo() {
		int iterations = 0;

		// TODO

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	private static void listDemo() {
		int iterations = 0;

		// TODO

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	private static void setDemo() {
		int iterations = 0;

		// TODO

		System.out.println("Iterations: " + iterations);
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("Inefficient Example:");
		inefficientDemo();

//		System.out.println("ArrayList Example:");
//		listDemo();

//		System.out.println("TreeSet Example:");
//		setDemo();
	}
}
