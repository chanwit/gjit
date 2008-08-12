package minimal;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;
import org.objectweb.asm.util.TraceClassVisitor;

public class AddTests extends TestCase {
		
	private static Class<?> c;
	private static byte[] out;
	private static byte[] bytes;
	
	static {
		try {
			RandomAccessFile f = new RandomAccessFile("test/minimal/AddSubject.class","r");
			bytes = new byte[(int) f.length()];
			f.readFully(bytes);
			PreProcess p = PreProcess.perform(bytes);
			out = Optimiser.perform(p, bytes);
			Utils.dump(out);
			c = Utils.loadClass("minimal.AddSubject", out);			
		} catch(Throwable e) {			
		}			
	}

	public void test_const_add_const() throws Throwable {
		Method m = c.getMethod("add_001");
		Object o = c.newInstance();
		Object result = m.invoke(o);
		assertEquals(true, out.length < bytes.length);
		assertEquals(3, result);
	}
	
	public void test_const_add_wo_return() throws Throwable {		
		Method m = c.getMethod("add_002");
		Object o = c.newInstance();
		Object result = m.invoke(o);
		assertEquals(2, result);			
	}
	
	public void test_const_add_args() throws Throwable {		
		Method m = c.getMethod("add_003", int.class, int.class);
		Object o = c.newInstance();
		Object result = m.invoke(o, 2, 2);
		assertEquals(4, result);			
	}	
	
	public void test_const_add_args_wo_return() throws Throwable {		
		Method m = c.getMethod("add_003", int.class, int.class);
		Object o = c.newInstance();
		Object result = m.invoke(o, 3, 3);
		assertEquals(6, result);			
	}	

}
