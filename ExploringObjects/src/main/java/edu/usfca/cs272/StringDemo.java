package edu.usfca.cs272;

/**
 * This class demonstrates the {@link String} class, and how it is optimized by
 * Java for efficiency. It also illustrates the dangers of using the == operator
 * on objects in Java.
 *
 * This class is a companion to {@link ObjectDemo}.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class StringDemo {
	/**
	 * Demonstrates the {@link String} class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		/*
		 * When the new keyword is used, new memory will always be created. However, we
		 * do not need to use this keyword with String objects. Since they are
		 * immutable, Java will reuse String objects when possible if the new keyword is
		 * not used.
		 */

		String demo1 = "apple";
		String demo2 = "apple";
		String demo3 = new String("apple");
		String demo4 = new String(demo3);

		/*
		 * Unsurprisingly, all of the String objects have value "apple".
		 */

		System.out.printf("demo1 = %s%n", demo1);
		System.out.printf("demo2 = %s%n", demo2);
		System.out.printf("demo3 = %s%n", demo3);
		System.out.printf("demo4 = %s%n", demo4);
		System.out.printf("%n");

		/*
		 * Even though we asked Java to create two of the String objects in new memory,
		 * all of the hash codes appear to be the same!
		 */

		System.out.printf("demo1.hashCode() = %x%n", demo1.hashCode());
		System.out.printf("demo2.hashCode() = %x%n", demo2.hashCode());
		System.out.printf("demo3.hashCode() = %x%n", demo3.hashCode());
		System.out.printf("demo4.hashCode() = %x%n", demo4.hashCode());
		System.out.printf("%n");

		/*
		 * Notice that the equals() method also considers these objects equivalent to
		 * each other. We actually want this behavior---if the String objects have the
		 * same value, they should be considered equivalent.
		 */

		System.out.printf("demo1.equals(demo2) = %s%n", demo1.equals(demo2));
		System.out.printf("demo2.equals(demo3) = %s%n", demo2.equals(demo3));
		System.out.printf("demo3.equals(demo4) = %s%n", demo3.equals(demo4));
		System.out.printf("%n");

		/*
		 * However, the equals() and == operations do not return the same results here.
		 * Why? Remember, both the content and hashcodes are the same!
		 */

		System.out.printf("demo1 == demo2 = %s%n", demo1 == demo2);
		System.out.printf("demo2 == demo3 = %s%n", demo2 == demo3);
		System.out.printf("demo3 == demo4 = %s%n", demo3 == demo4);
		System.out.printf("%n");

		/*
		 * The String class overrode the equals() and hashCode() methods to use the
		 * content of the String, NOT the internal address of the String object. We can
		 * get the original hashcode implementation using the "system identity" hashcode
		 * instead, which is more likely to give us that information.
		 */

		System.out.printf("demo1.identityHashCode() = %x%n", System.identityHashCode(demo1));
		System.out.printf("demo2.identityHashCode() = %x%n", System.identityHashCode(demo2));
		System.out.printf("demo3.identityHashCode() = %x%n", System.identityHashCode(demo3));
		System.out.printf("demo4.identityHashCode() = %x%n", System.identityHashCode(demo4));
		System.out.printf("%n");

		/*
		 * Now we can understand the difference. The == operation relies on similar
		 * information as the identity hashcode, which is not testing whether two
		 * objects are equivalent but whether they are the same object internally. That
		 * is usually NOT what we want in our code, hence the recommendation to use
		 * equals() instead of == for any kind of object.
		 */
	}
}
