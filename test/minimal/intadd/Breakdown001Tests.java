package minimal.intadd;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

public class Breakdown001Tests extends TestCase {
		
	private static Class<?> c;
	private static byte[] out;
	private static byte[] bytes;
	
	static {
		try {
			RandomAccessFile f = new RandomAccessFile("test/minimal/intadd/Breakdown001Subject.class","r");
			bytes = new byte[(int) f.length()];
			f.readFully(bytes);
			PreProcess p = PreProcess.perform(bytes);
			out = Optimiser.perform(p, bytes);
			Utils.dumpForCompare(bytes, out);
			c = Utils.loadClass("minimal.intadd.Breakdown001Subject", out);
			
			RandomAccessFile f2 = new RandomAccessFile("test/minimal/intadd/Breakdown001Subject$_add_006_closure1.class","r");
			byte[] cb = new byte[(int) f2.length()];
			f2.readFully(cb);
			Utils.loadClass("minimal.intadd.Breakdown001Subject$_add_006_closure1", cb);
		} catch(Throwable e) {			
		}			
	}
	
	public void test_const_add_then_loop() throws Throwable {
		Method m = c.getMethod("add_006");
		Object o = c.newInstance();
		Object result = m.invoke(o);
		assertEquals(10, result);		
	}
	
}
