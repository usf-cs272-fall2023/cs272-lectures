package edu.usfca.cs272;

import java.util.Arrays;

/**
 * Illustrates basic mutability using an array and primitive types. According to
 * the Java tutorials, "[a]n object is considered immutable if its state cannot
 * change after it is constructed." The term "mutable" versus "immutable" is not
 * clearly defined in the glossary or the API documentation, but is used
 * throughout the API documentation.
 *
 * The state of an object is determined by what it stores; or in simplified
 * terms, what is stored in the memory allocated for that object.
 *
 * Most data structures are mutable. However, some data structures behave
 * unexpectedly if you store within them mutable objects.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class MutabilityDemo {
	/**
	 * Illustrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		/*
		 * We start by creating a simple integer array. The "new" keyword indicates we
		 * are allocating new memory for this array.
		 */

		int[] a = new int[] { 0, 1, 2 };

		System.out.println("a: " + Arrays.toString(a));
		System.out.println();

		/*
		 * Keep in mind there is a shortcut syntax of creating arrays (and Strings) that
		 * does not require the new keyword.
		 */

		// int[] a = { 0, 1, 2 };

		/*
		 * Arrays are mutable, so when we change an individual value in the array, it is
		 * modifying the internal state (i.e. memory) of that array.
		 */

		a[0] = 3;

		System.out.println("a: " + Arrays.toString(a));
		System.out.println();

		/*
		 * Here notice we are not allocating new memory, as there is no new keyword.
		 * Instead, we are simply creating another way to refer to the memory already
		 * allocated to "a". Think of it as a nickname.
		 *
		 * Since arrays are also mutable, whenever we change the state of this object,
		 * all references to that object will see this modified state.
		 */

		int[] b = a;
		b[1] = 4;

		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println();

		/*
		 * References to this object could be in another scope. For example, the
		 * incrementFirst() method here has a reference to the array, so changes within
		 * that method will be seen outside of the method as well.
		 */

		incrementFirst(a);

		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println();

		/*
		 * If we use the "new" keyword, that means new memory is being created. In this
		 * case, "b" will no longer refer to the same memory as "a".
		 */

		b = new int[] { 5, 6, 7 };

		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println();

		/*
		 * Primitive types are different. Mutability is something we really only discuss
		 * in to the context of objects, but you can think of a primitive type as being
		 * immutable. If you modify the value, you break the original memory reference.
		 */

		int c = a[2];
		c = 8;

		System.out.println("a: " + Arrays.toString(a));
		System.out.println("b: " + Arrays.toString(b));
		System.out.println("c: " + c);
		System.out.println();

		/*
		 * This is why primitives are often considered to behave like pass-by-value.
		 * When you pass the value to a method, any modifications to that value are lost
		 * when the method scope is exited unless you return those changes.
		 */

		incrementPrimitive(c);

		System.out.println("c: " + c);
		System.out.println();
	}

	/**
	 * Increments and outputs the first value in the array.
	 *
	 * @param z the array to increment and output
	 */
	public static void incrementFirst(int[] z) {
		z[0]++;
		System.out.println("z: " + Arrays.toString(z));
	}

	/**
	 * Increments and outputs the value.
	 *
	 * @param z the value to increment and output
	 */
	public static void incrementPrimitive(int z) {
		z++;
		System.out.println("z: " + z);
	}
}
