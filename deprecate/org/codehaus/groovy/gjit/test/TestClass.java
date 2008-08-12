package org.codehaus.groovy.gjit.test;

public class TestClass {

	public void myMethod() throws Throwable {
		for (int i = 0; i < 10000; i++) {
			Thread.sleep(100);
			System.out.println("a");			
		}		
	}

}
