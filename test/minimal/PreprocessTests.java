package minimal;

import java.io.RandomAccessFile;

import org.codehaus.groovy.gjit.PreProcess;

import junit.framework.TestCase;

public class PreprocessTests extends TestCase {

    public void testCheckGroovyFile() {
        try {
            RandomAccessFile f = new RandomAccessFile("test/minimal/PreprocessSubject.class","r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            PreProcess p = PreProcess.perform(bytes);
            assertEquals(true, p.isGroovyClassFile());
            assertEquals(true, p.getMethods().containsKey("<init>()V"));
            assertEquals(3, p.getMethods().size());
        } catch(Throwable e) {
            fail(e.getMessage());
        }
    }

}
