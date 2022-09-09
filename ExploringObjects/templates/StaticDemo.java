package edu.usfca.cs272;

@SuppressWarnings({"static-access", "unused"})
public class StaticDemo {
	private int instanceMember;
	private static int classMember;

	public static void main(String[] args) {
		StaticDemo demo1 = new StaticDemo();
		demo1.instanceMember = 0;
		demo1.classMember = 1;

		System.out.println("Demo 1:");
//		System.out.println(demo1.instanceMember + ", " + demo1.classMember);
//		System.out.println();

//		StaticDemo demo2 = new StaticDemo();
//		demo2.instanceMember = 2;
//		demo2.classMember = 3;
//
//		System.out.println("Demo 2:");
//		System.out.println(demo2.instanceMember + ", " + demo2.classMember);
//		System.out.println();

	}
}
