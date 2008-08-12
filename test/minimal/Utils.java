package minimal;

import java.io.File;
import java.io.PrintWriter;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

public class Utils {

	public static Class<?> loadClass(String className, byte[] b) {
		Class<?> clazz = null;
		try {
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			Class<?> cls = Class.forName("java.lang.ClassLoader");
			java.lang.reflect.Method method = cls.getDeclaredMethod(
					"defineClass", new Class[] { String.class, byte[].class,
							int.class, int.class });

			method.setAccessible(true);
			try {
				Object[] args = new Object[] { className, b, new Integer(0),
						new Integer(b.length) };
				clazz = (Class<?>) method.invoke(loader, args);
			} finally {
				method.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return clazz;
	}
	
	public static void dump(byte[] bytes) throws Exception {
        int flags = ClassReader.SKIP_DEBUG;
        ClassReader cr;
        cr = new ClassReader(bytes);
        cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), new Attribute[0], flags);
    }
	
	public static void dumpForCompare(byte[] orgs, byte[] bytes) throws Exception {
        int flags = 0;
        ClassReader cr;
        cr = new ClassReader(bytes);
        cr.accept(new TraceClassVisitor(new PrintWriter(new File("out.dump"))), new Attribute[0], flags);
        ClassReader cr2;
        cr2 = new ClassReader(orgs);
        cr2.accept(new TraceClassVisitor(new PrintWriter(new File("in.dump"))), new Attribute[0], flags);        
    }	
	
}
