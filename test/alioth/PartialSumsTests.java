package alioth;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class PartialSumsTests extends TestCase {

    private static Class<?> c;
    private static byte[] out;
    private static byte[] bytes;

    static {
        try {
            RandomAccessFile f = new RandomAccessFile("test/alioth/PartialSums.class","r");
            bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            PreProcess p = PreProcess.perform(bytes);
            out = Optimiser.perform(p, bytes);
            Utils.dumpForCompare(bytes, out);
            c = Utils.loadClass("alioth.PartialSums", out);
        } catch(Throwable e) {
        }
    }

    public void testPartialSums() throws Throwable {
        Method m = c.getMethod("run", Object.class);
        Object o = c.newInstance();
        Object result = m.invoke(o, 10);
        assertEquals(3, result);
    }

}
