package minimal;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

public class ForTests extends TestCase {

    private static Class<?> c;
    private static byte[] out;
    private static byte[] bytes;

    static {
        try {
            RandomAccessFile f = new RandomAccessFile("test/minimal/ForSubject.class","r");
            bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            PreProcess p = PreProcess.perform(bytes);
            out = Optimiser.perform(p, bytes);
            Utils.dumpForCompare(bytes, out);
            c = Utils.loadClass("minimal.ForSubject", out);
        } catch(Throwable e) {
        }
    }

    public void test_const_add_const() throws Throwable {
        Method m = c.getMethod("add_001");
        Object o = c.newInstance();
        Object result = m.invoke(o);
        assertEquals(0, result);
    }

    public void test_const_add_const2() throws Throwable {
        Method m = c.getMethod("add_002");
        Object o = c.newInstance();
        Object result = m.invoke(o);
        assertEquals(0, result);
    }

    public void test_const_add_const3() throws Throwable {
        Method m = c.getMethod("add_003");
        Object o = c.newInstance();
        Object result = m.invoke(o);
        assertEquals(0, result);
    }

    public void test_const_add_const4() throws Throwable {
        Method m = c.getMethod("add_004");
        Object o = c.newInstance();
        Object result = m.invoke(o);
        assertEquals(0, result);
    }

}
