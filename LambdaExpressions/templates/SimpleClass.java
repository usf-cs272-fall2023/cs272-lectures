package edu.usfca.cs272;

public class SimpleClass implements SimpleInterface {
	@Override
	public void simpleMethod() {
		System.out.println(this.getClass().getTypeName());
	}
}
