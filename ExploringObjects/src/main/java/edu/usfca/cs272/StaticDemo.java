package edu.usfca.cs272;

/**
 * Briefly demonstrates the affect the "static" keyword has on
 * members/variables.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class StaticDemo {
	/** Example instance member; belongs to a specific object/instance. */
	private int instanceMember;

	/** Example static/class member; shared by all objects/instances */
	private static int classMember;

	/**
	 * Demonstrates the affect of the "static" keyword.
	 *
	 * @param args unused
	 */
	@SuppressWarnings("static-access") // (Don't do this!)
	public static void main(String[] args) {
		/*
		 * Lets create a StaticDemo object. Remember, a class is like a blueprint.
		 * An object is a specific element, such as a specific house, built from
		 * that blueprint.
		 */

		StaticDemo demo1 = new StaticDemo();
		demo1.instanceMember = 0;
		demo1.classMember = 1;

		System.out.println("Demo 1:");
		System.out.println(demo1.instanceMember + ", " + demo1.classMember);
		System.out.println();

		/*
		 * Ignore for the moment that we did not properly initialize our members.
		 */

		StaticDemo demo2 = new StaticDemo();
		demo2.instanceMember = 2;
		demo2.classMember = 3;

		System.out.println("Demo 2:");
		System.out.println(demo2.instanceMember + ", " + demo2.classMember);
		System.out.println();

		/*
		 * Suppose we create another StaticDemo object. The INSTANCE member
		 * (non-static) is specific to an object, so it will have its own value.
		 * However, CLASS members are shared by all objects (and exist even
		 * when there are no active objects), so it will share the value with demo1.
		 */

		System.out.println("Demo 1 (Revisited):");
		System.out.println(demo1.instanceMember + ", " + demo1.classMember);
		System.out.println();

		/*
		 * Note that I had to suppress warnings in this file. DON'T DO THIS! Since b
		 * is static, we should really be accessing it as below. It makes it more
		 * clear that we are dealing with a class-wide element instead of a
		 * per-instance element.
		 */

		StaticDemo.classMember = 4;

		System.out.println("Demo 1 and Demo 2:");
		System.out.println(demo1.instanceMember + ", " + demo1.classMember);
		System.out.println(demo2.instanceMember + ", " + demo2.classMember);
	}
}
