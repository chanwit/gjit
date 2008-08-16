package alioth.binarytree;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class Breakdown001Tests extends TestCase {

    private static Class<?> c;
    private static byte[] out;
    private static byte[] bytes;

    static {
        try {
            RandomAccessFile f = new RandomAccessFile("test/alioth/binarytree/Breakdown001Subject.class","r");
            bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            PreProcess p = PreProcess.perform(bytes);
            out = Optimiser.perform(p, bytes);
            Utils.dumpForCompare(bytes, out);
            c = Utils.loadClass("alioth.binarytree.Breakdown001Subject", out);
        } catch(Throwable e) {
        }
    }

    public void testBottomUpTree() throws Throwable {
        Method m = c.getMethod("bottomUpTree", int.class, int.class);
        Object result = m.invoke(null, 10, 0);
        assertEquals("alioth.binarytree.Breakdown001Subject", result.getClass().getName());
    }

    public void testItemCheck() throws Throwable {
        Method m = c.getMethod("itemCheck");
        assertNotNull(m);
//        Object result = m.invoke(null, 10, 0);
//        assertEquals("alioth.binarytree.Breakdown001Subject", result.getClass().getName());
    }
}
