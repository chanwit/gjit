package minimal;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class WhileTests extends TestCase {
	
	private static Class<?> c;
	private static byte[] out;
	private static byte[] bytes;
	
	static {
		try {
			RandomAccessFile f = new RandomAccessFile("test/minimal/WhileSubject.class","r");
			bytes = new byte[(int) f.length()];
			f.readFully(bytes);
			PreProcess p = PreProcess.perform(bytes);
			out = Optimiser.perform(p, bytes);
			Utils.dumpForCompare(bytes, out);
			c = Utils.loadClass("minimal.WhileSubject", out);			
		} catch(Throwable e) {			
		}			
	}
	
	public void testWhile_with_int() throws Throwable {
		Method m = c.getMethod("while_001");
		Object o = c.newInstance();
		Object result = m.invoke(o);
		assertEquals(-1, result);
	}
	
	public void testWhile_with_double() throws Throwable {
		Method m = c.getMethod("while_002");
		Object o = c.newInstance();
		Object result = m.invoke(o);
		assertEquals(-1.0D, result);
	}	
}
