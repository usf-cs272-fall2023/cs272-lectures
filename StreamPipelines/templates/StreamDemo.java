package edu.usfca.cs272;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class StreamDemo {
	public static void print(String element) {
		System.out.print(element);
		System.out.print(" ");
	}

	public static String removePunctuation(String text) {
		return text.replaceAll("(?U)\\p{Punct}+", "");
	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> acronyms = new ArrayList<>();
		Collections.addAll(acronyms, "IDE", "CPU", "URI", "HDD", "GUI", "API", "OOP", "EOF");
		Collections.addAll(acronyms, "DBMS", "BLOB", "FIFO", "JSON");
		Collections.addAll(acronyms, "WYSIWYG");

		int i = 0;

		System.out.print(++i + ": ");
		acronyms.forEach(StreamDemo::print);
		System.out.println();

		// TODO
	}
}
