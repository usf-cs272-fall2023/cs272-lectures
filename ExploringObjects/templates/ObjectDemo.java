package edu.usfca.cs272;

public class ObjectDemo {
	public static void objectDemo() {
		Object demo = new Object(); // TODO Modify

		System.out.printf("%s%n", demo.toString());
		System.out.printf("%s%n", demo.getClass().getName());
		System.out.printf("%x%n", demo.hashCode());
		System.out.printf("%n");
	}

	public static void equalsDemo() {
		Object demo1 = new Object();
		Object demo2 = new Object();
		Object demo3 = demo2;

		System.out.printf("demo1 = %x%n", demo1.hashCode());
		System.out.printf("demo2 = %x%n", demo2.hashCode());
		System.out.printf("demo3 = %x%n", demo3.hashCode());
		System.out.printf("%n");

		System.out.printf("demo1 == demo2 = %s%n", demo1 == demo2);
		System.out.printf("demo2 == demo3 = %s%n", demo2 == demo3);
		System.out.printf("demo2.equals(demo3) = %s%n", demo2.equals(demo3));
		System.out.printf("%n");
	}

	public static void main(String[] args) {
		objectDemo();
	}
}
