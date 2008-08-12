package minimal;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class LongAddTests extends TestCase {
	
	private static Class<?> c;
	private static byte[] out;
	private static byte[] bytes;
	
	static {
		try {
			RandomAccessFile f = new RandomAccessFile("test/minimal/LongSubject.class","r");
			bytes = new byte[(int) f.length()];
			f.readFully(bytes);
			PreProcess p = PreProcess.perform(bytes);
			out = Optimiser.perform(p, bytes);
			Utils.dumpForCompare(bytes, out);
			c = Utils.loadClass("minimal.LongSubject", out);
			
		} catch(Throwable e) {			
		}			
	}	
	
	public void test_long_const_add_const() throws Throwable {
		Method m = c.getMethod("add_001");
		Object o = c.newInstance();
		Long result = (Long)m.invoke(o);
		assertEquals(111111111112L, (long)result);
	}
	
	public void test_long_const_add_const_reverse() throws Throwable {
		Method m = c.getMethod("add_002");
		Object o = c.newInstance();
		Long result = (Long)m.invoke(o);
		assertEquals(111111111113L, (long)result);
	}
	
	public void test_long_add_int_return_int() throws Throwable {
		Method m = c.getMethod("add_003");
		Object o = c.newInstance();
		Integer result = (Integer)m.invoke(o);
		assertEquals(-1116077169, (int)result);
	}	
	
	public void test_long_add_int_return_float() throws Throwable {
		Method m = c.getMethod("add_004");
		Object o = c.newInstance();
		Float result = (Float)m.invoke(o);
		assertEquals((float)2.22222221E11, (float)result);
	}		
	
	public void test_long_add_int_return_double() throws Throwable {
		Method m = c.getMethod("add_005");
		Object o = c.newInstance();
		Double result = (Double)m.invoke(o);
		assertEquals((double)3.33333333334E11, (double)result);
	}		
}
