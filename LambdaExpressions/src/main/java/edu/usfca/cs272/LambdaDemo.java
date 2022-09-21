package edu.usfca.cs272;

/**
 * Demonstrates one difference (namely the use of the {@code this} keyword)
 * between lambda expressions and traditional classes, static nested classes,
 * inner classes, and anonymous classes.
 *
 * @see SimpleInterface
 * @see SimpleClass
 *
 * @see FunctionalInterface
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class LambdaDemo {
	/**
	 * An example static nested class.
	 */
	public static class StaticNestedClass implements SimpleInterface {
		@Override
		public void simpleMethod() {
			// Think about what "this" is referring to here!
			System.out.println(this.getClass().getTypeName());
		}
	}

	/**
	 * An example inner (non-static) class.
	 */
	public class InnerClass implements SimpleInterface {
		@Override
		public void simpleMethod() {
			// Think about what "this" is referring to here!
			System.out.println(this.getClass().getTypeName());
		}
	}

	/**
	 * Instantiating a normal class.
	 */
	public void initNormalClass() {
		SimpleInterface normalClass = new SimpleClass();
		normalClass.simpleMethod();
	}

	/**
	 * Instantiating a static nested class.
	 */
	public void initNestedClass() {
		SimpleInterface nestedClassShort = new StaticNestedClass();
		nestedClassShort.simpleMethod();

		SimpleInterface nestedClassFull = new LambdaDemo.StaticNestedClass();
		nestedClassFull.simpleMethod();
	}

	/**
	 * Instantiating an inner class.
	 */
	public void initInnerClass() {
		SimpleInterface innerClassShort = new InnerClass();
		innerClassShort.simpleMethod();

		SimpleInterface innerClassFull = this.new InnerClass();
		innerClassFull.simpleMethod();
	}

	/**
	 * Instantiating an anonymous inner class.
	 */
	public void initAnonymousClass() {
		SimpleInterface anonymousClass = new SimpleInterface() {
			@Override
			public void simpleMethod() {
				// Think about what "this" is referring to here!
				System.out.println(this.getClass().getTypeName());
			}
		};
		anonymousClass.simpleMethod();
	}

	/**
	 * Using a lambda expression to define the method.
	 */
	public void initLambdaExpression() {
		SimpleInterface lambdaExpression = () -> {
			// Think about what "this" is referring to here!
			System.out.println(this.getClass().getTypeName());
		};
		lambdaExpression.simpleMethod();
	}

	/**
	 * Demonstrating the classes and methods in this demo.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		LambdaDemo demo = new LambdaDemo();
		demo.initNormalClass();
		demo.initNestedClass();
		demo.initInnerClass();
		demo.initAnonymousClass();
		demo.initLambdaExpression();

		// another anonymous inner class
		SimpleInterface anonymousClass = new SimpleInterface() {
			@Override
			public void simpleMethod() {
				// Think about what "this" is referring to here!
				System.out.println(this.getClass().getTypeName());
			}
		};
		anonymousClass.simpleMethod();

		/*
		 * Since we are in a static method, there is no "this" context for the
		 * lambda expression below to reference. Remember, lambda expressions are
		 * NOT quite objects (and do not create a new scope)!
		 */

		SimpleInterface lambdaExpression = () -> {
			// Think about what "this" is referring to here!
			// System.out.println(this.getClass().getTypeName());
			System.out.println(LambdaDemo.class.getTypeName());
		};
		lambdaExpression.simpleMethod();

		System.out.println(lambdaExpression.getClass().getTypeName());
	}
}
