package minimal;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class AddTests extends TestCase {
	
	public void test_const_add_const() {
		try {
			RandomAccessFile f = new RandomAccessFile("test/minimal/AddSubject.class","r");
			byte[] bytes = new byte[(int) f.length()];
			f.readFully(bytes);
			PreProcess p = PreProcess.perform(bytes);
			assertEquals(true, p.isGroovyClassFile());
			byte[] out = Optimiser.perform(p, bytes);
			Class<?> c = Utils.loadClass("minimal.AddSubject", out);
			Method m = c.getMethod("add_001");
			Object o = c.newInstance();
			Object result = m.invoke(o);
			assertEquals(true, out.length < bytes.length);
			assertEquals(3, result);
		} catch(Throwable e) {
			fail(e.getMessage());
		}		
	}
}
