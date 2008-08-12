package org.codehaus.groovy.gjit.test;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestClassDump implements Opcodes {

	public static byte[] dump() throws Exception {
		ClassWriter cw = new ClassWriter(0);
		MethodVisitor mv;
		cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER,
				"org/codehaus/groovy/gjit/test/TestClass", null,
				"java/lang/Object", null);

		cw.visitSource("TestClass.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(3, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
					"()V");
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this",
					"Lorg/codehaus/groovy/gjit/test/TestClass;", null, l0, l1,
					0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "myMethod", "()V", null, new String[] { "java/lang/Throwable" });
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(6, l0);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 1);
			Label l1 = new Label();
			mv.visitLabel(l1);
			Label l2 = new Label();
			mv.visitJumpInsn(GOTO, l2);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(7, l3);
			mv.visitLdcInsn(new Long(100L));
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V");
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLineNumber(8, l4);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitLdcInsn("changed");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
			Label l5 = new Label();
			mv.visitLabel(l5);
			mv.visitLineNumber(6, l5);
			mv.visitIincInsn(1, 1);
			mv.visitLabel(l2);
			mv.visitVarInsn(ILOAD, 1);
			mv.visitIntInsn(SIPUSH, 10000);
			mv.visitJumpInsn(IF_ICMPLT, l3);
			Label l6 = new Label();
			mv.visitLabel(l6);
			mv.visitLineNumber(10, l6);
			mv.visitInsn(RETURN);
			Label l7 = new Label();
			mv.visitLabel(l7);
			mv.visitLocalVariable("this", "Lorg/codehaus/groovy/gjit/test/TestClass;", null, l0, l7, 0);
			mv.visitLocalVariable("i", "I", null, l1, l6, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
			}

		cw.visitEnd();

		return cw.toByteArray();
	}
}
