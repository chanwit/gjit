package helper;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.SimpleVerifier;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

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
        CheckClassAdapter.verify(cr, false, new PrintWriter(new File("out_chk.log")));
        ClassReader cr2;
        cr2 = new ClassReader(orgs);
        cr2.accept(new TraceClassVisitor(new PrintWriter(new File("in.dump"))), new Attribute[0], flags);
    }

    public static void verify(
            final ClassReader cr,
            final boolean dump,
            final PrintWriter pw)
        {
            ClassNode cn = new ClassNode();
            cr.accept(new CheckClassAdapter(cn), 0);

            Type syperType = cn.superName == null
                    ? null
                    : Type.getObjectType(cn.superName);
            List methods = cn.methods;
            for (int i = 0; i < methods.size(); ++i) {
                MethodNode method = (MethodNode) methods.get(i);
                Analyzer a = new Analyzer(new SimpleVerifier(Type.getObjectType(cn.name),
                        syperType,
                        false));
                try {
                    a.analyze(cn.name, method);
                    if (!dump) {
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace(pw);
                }
                Frame[] frames = a.getFrames();

                TraceMethodVisitor mv = new TraceMethodVisitor();

                pw.println(method.name + method.desc);
                for (int j = 0; j < method.instructions.size(); ++j) {
                    method.instructions.get(j).accept(mv);

                    StringBuffer s = new StringBuffer();
                    Frame f = frames[j];
                    if (f == null) {
                        s.append('?');
                    } else {
                        for (int k = 0; k < f.getLocals(); ++k) {
                            s.append(getShortName(f.getLocal(k).toString())).append(' ');
                        }
                        s.append(" : ");
                        for (int k = 0; k < f.getStackSize(); ++k) {
                            s.append(getShortName(f.getStack(k).toString()))
                                    .append(' ');
                        }
                    }
                    while (s.length() < method.maxStack + method.maxLocals + 1) {
                        s.append(' ');
                    }
                    pw.print(Integer.toString(j + 100000).substring(1));
                    // pw.print(" " + s + " : " + mv.buf); // mv.text.get(j));
                }
                for (int j = 0; j < method.tryCatchBlocks.size(); ++j) {
                    ((TryCatchBlockNode) method.tryCatchBlocks.get(j)).accept(mv);
                    // pw.print(" " + mv.buf);
                }
                pw.println();
            }
            pw.flush();
        }

    private static String getShortName(final String name) {
        int n = name.lastIndexOf('/');
        int k = name.length();
        if (name.charAt(k - 1) == ';') {
            k--;
        }
        return n == -1 ? name : name.substring(n + 1, k);
    }


}
