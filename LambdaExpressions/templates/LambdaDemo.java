package edu.usfca.cs272;

public class LambdaDemo {
	public static class StaticNestedClass implements SimpleInterface {
		@Override
		public void simpleMethod() {
			System.out.println(this.getClass().getTypeName());
		}
	}

	public class InnerClass implements SimpleInterface {
		@Override
		public void simpleMethod() {
			System.out.println(this.getClass().getTypeName());
		}
	}

	public void initNormalClass() {
		SimpleInterface normalClass = null;
		normalClass.simpleMethod();
	}

	public void initNestedClass() {
		SimpleInterface nestedClassShort = null;
		nestedClassShort.simpleMethod();
		
		SimpleInterface nestedClassFull = null;
		nestedClassFull.simpleMethod();
	}

	public void initInnerClass() {
		SimpleInterface innerClassShort = null;
		innerClassShort.simpleMethod();
		
		SimpleInterface innerClassFull = null;
		innerClassFull.simpleMethod();
	}

	public void initAnonymousClass() {
		SimpleInterface anonymousClass = null;
		anonymousClass.simpleMethod();
	}

	public void initLambdaExpression() {
		SimpleInterface lambdaExpression = null;
		lambdaExpression.simpleMethod();
	}

	public static void main(String[] args) {
		LambdaDemo demo = new LambdaDemo();
		demo.initNormalClass();
		demo.initNestedClass();
		demo.initInnerClass();
		demo.initAnonymousClass();
		demo.initLambdaExpression();

		// TODO Fill in
		SimpleInterface anonymousClass = null;
		SimpleInterface lambdaExpression = null;
	}
}
