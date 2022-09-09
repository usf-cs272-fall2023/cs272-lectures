package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Arrays;

public class HashCodeDemo {
	@SuppressWarnings("removal") // Don't do this!
	public static void main(String[] args) {
		Integer integer0 = new Integer(0);
		printHash("OUTER BEG", integer0);
//		testInteger(integer0);
//		printHash("OUTER END", integer0);
//		System.out.println();

//		Integer integer1 = Integer.valueOf(1);
//		printHash("VALUE OF", integer1);
//		printHash("CALL NEW", new Integer(1));
//		System.out.println();

//		String string = "apple";
//		printHash("OUTER BEG", string);
//		testString(string);
//		printHash("OUTER END", string);
//		System.out.println();

//		StringBuilder builder = new StringBuilder("apple");
//		printHash("OUTER BEG", builder);
//		testBuilder(builder);
//		printHash("OUTER END", builder);
//		System.out.println();

//		String[] array = new String[] { "apple", "banana" };
//		printHash("OUTER BEG", array);
//		testArray(array);
//		printHash("OUTER END", array);
//		System.out.println();

//		ArrayList<String> list = new ArrayList<String>();
//		list.add("apple");
//		printHash("OUTER BEG", list);
//		testList(list);
//		printHash("OUTER END", list);
	}

	public static void testInteger(Integer value) {
		printHash("INNER BEG", value);
		value = 1;
		printHash("INNER END", value);
	}

	public static void testString(String value) {
		printHash("INNER BEG", value);
		value = value.toUpperCase();
		printHash("INNER END", value);
	}

	public static void testBuilder(StringBuilder value) {
		printHash("INNER BEG", value);
		value.append(" banana");
		printHash("INNER END", value);
	}

	public static void testArray(String[] value) {
		printHash("INNER BEG", value);
		value[0] = "carrot";
		printHash("INNER END", value);
	}

	public static void testList(ArrayList<String> value) {
		printHash("INNER BEG", value);
		value.add("banana");
		printHash("INNER BEG", value);
	}

	public static void printHash(String label, Object object) {
		Object[] args = { label, System.identityHashCode(object), object };
		System.out.format("%-9s : x%08X : %s %n", args);
	}

	public static void printHash(String label, Object[] object) {
		Object[] args = { label, System.identityHashCode(object), Arrays.toString(object) };
		System.out.format("%-9s : x%08X : %s %n", args);
	}
}
