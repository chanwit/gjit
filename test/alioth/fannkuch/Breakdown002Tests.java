package alioth.fannkuch;

import helper.Utils;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;

import org.codehaus.groovy.gjit.Optimiser;
import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class Breakdown002Tests extends TestCase {

    private static Class<?> c;
    private static byte[] out;
    private static byte[] bytes;

    static {
        try {
            RandomAccessFile f = new RandomAccessFile("test/alioth/fannkuch/Breakdown002.class","r");
            bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            PreProcess p = PreProcess.perform(bytes);
            out = Optimiser.perform(p, bytes);
            Utils.dumpForCompare(bytes, out);
            c = Utils.loadClass("alioth.fannkuch.Breakdown002", out);
        } catch(Throwable e) {
        }
    }

    public void testFannkuch() throws Throwable {
        Method m = c.getMethod("main", String[].class);
        String[] args = new String[]{"8"};
        m.invoke(null, (Object)args);
    }

}
