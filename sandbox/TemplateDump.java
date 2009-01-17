import java.util.*;
import org.objectweb.asm.*;

public class TemplateDump implements Opcodes {

	public static byte[] dump() throws Exception {

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER, "Template", null,
				"java/lang/Object", null);

		cw.visitSource("Template.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(2, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "LTemplate;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "a", "(III)V", null,
					null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(5, l0);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitVarInsn(ILOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(I)V");
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(6, l1);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(I)V");
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(7, l2);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitVarInsn(ILOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(I)V");
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(8, l3);
			mv.visitInsn(RETURN);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLocalVariable("i", "I", null, l0, l4, 0);
			mv.visitLocalVariable("j", "I", null, l0, l4, 1);
			mv.visitLocalVariable("k", "I", null, l0, l4, 2);
			mv.visitMaxs(2, 3);
			mv.visitEnd();
		}
		{
			mv = cw
					.visitMethod(
							ACC_PUBLIC + ACC_STATIC,
							"b",
							"(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V",
							null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(11, l0);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/Object;)V");
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(12, l1);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/Object;)V");
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(13, l2);
			mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
					"Ljava/io/PrintStream;");
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
					"(Ljava/lang/Object;)V");
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(14, l3);
			mv.visitInsn(RETURN);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLocalVariable("i", "Ljava/lang/Integer;", null, l0, l4, 0);
			mv.visitLocalVariable("j", "Ljava/lang/Integer;", null, l0, l4, 1);
			mv.visitLocalVariable("k", "Ljava/lang/Integer;", null, l0, l4, 2);
			mv.visitMaxs(2, 3);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
					"([Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(17, l0);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(ICONST_3);
			mv.visitMethodInsn(INVOKESTATIC, "Template", "a", "(III)V");
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(18, l1);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(ICONST_3); // 3,2,1	
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;"); // (3),2,1 
			mv.visitInsn(SWAP);  // 2,(3),1
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;"); // (2),(3),1
			mv.visitInsn(SWAP);  // (3),(2),1
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKESTATIC, "Template", "b", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V");
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(19, l2);
			mv.visitInsn(RETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l3,
					0);
			mv.visitMaxs(3, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}