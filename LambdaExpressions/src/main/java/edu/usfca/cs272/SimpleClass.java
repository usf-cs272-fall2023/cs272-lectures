package edu.usfca.cs272;

/**
 * A simple class that implements a functional interface.
 *
 * @see SimpleInterface
 * @see LambdaDemo
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class SimpleClass implements SimpleInterface {
	@Override
	public void simpleMethod() {
		// Using getTypeName() because it provides output even with anonymous
		// classes and lambda expressions
		System.out.println(this.getClass().getTypeName());
	}
}
