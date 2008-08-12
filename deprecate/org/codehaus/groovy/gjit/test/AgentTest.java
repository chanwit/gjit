package org.codehaus.groovy.gjit.test;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

import org.codehaus.groovy.gjit.agent.Agent;

public class AgentTest {

	public static void main(String[] args) throws Throwable {
		// get an instrumentation from the agent

		// here, print "a"
		TestClass t1 = new TestClass();
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
				}
				Instrumentation i = Agent.getInstrumentation();
				byte[] bytes;
				try {
					bytes = TestClassDump.dump();
					ClassDefinition[] def = new ClassDefinition[1];
					def[0] = new ClassDefinition(TestClass.class, bytes);
					i.redefineClasses(def);
					System.out.println("done");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}.start();
		t1.myMethod();

		// doing bytecode manipulation,
		// replace a new method body for "myMethod"
		
		// this shows "modified"
		// TestClass t2 = new TestClass();
		// t2.myMethod();
	}

}