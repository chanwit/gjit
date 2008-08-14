package minimal;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

public class IntAddTests extends TestCase {

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
            Utils.dumpForCompare(bytes, out);
            c = Utils.loadClass("minimal.AddSubject", out);

            RandomAccessFile f2 = new RandomAccessFile("test/minimal/AddSubject$_add_006_closure1.class","r");
            byte[] cb = new byte[(int) f2.length()];
            f2.readFully(cb);
            Utils.loadClass("minimal.AddSubject$_add_006_closure1", cb);
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
        Method m = c.getMethod("add_004", int.class, int.class);
        Object o = c.newInstance();
        Object result = m.invoke(o, 3, 3);
        assertEquals(6, result);
    }

    public void test_const_add_7_int() throws Throwable {
        Method m = c.getMethod("add_005");
        Object o = c.newInstance();
        Object result = m.invoke(o);
        assertEquals(1+2+3+4+5+6+7, result);
    }

    public void test_const_add_then_loop() throws Throwable {
        Method m = c.getMethod("add_006");
        Object o = c.newInstance();
        Object result = m.invoke(o);
        assertEquals(10, result);
    }

}
