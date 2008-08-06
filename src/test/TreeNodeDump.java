package test;

import org.objectweb.asm.*;

public class TreeNodeDump implements Opcodes {

	public static byte[] dump() throws Exception {

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(V1_3, ACC_PUBLIC, "TreeNode", null, "java/lang/Object",
				new String[] { "groovy/lang/GroovyObject" });

		{
			fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC
					+ ACC_SYNTHETIC, "$ownClass", "Ljava/lang/Class;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "left", "Ljava/lang/Object;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "right", "Ljava/lang/Object;",
					null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE, "item", "I", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC
					+ ACC_SYNTHETIC, "$const$0", "Ljava/lang/Integer;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC
					+ ACC_SYNTHETIC, "$const$1", "Ljava/lang/Integer;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC
					+ ACC_SYNTHETIC, "$const$2", "Ljava/lang/Integer;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC
					+ ACC_SYNTHETIC, "$const$3", "Ljava/lang/Integer;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC
					+ ACC_SYNTHETIC, "$const$4", "Ljava/lang/Integer;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "$staticMetaClass",
					"Ljava/lang/ref/SoftReference;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_TRANSIENT, "metaClass",
					"Lgroovy/lang/MetaClass;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "__timeStamp",
					"Ljava/lang/Long;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PUBLIC + ACC_STATIC,
					"__timeStamp__239_neverHappen1216335190359",
					"Ljava/lang/Long;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$class$TreeNode", "Ljava/lang/Class;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$class$java$lang$Class", "Ljava/lang/Class;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$class$java$lang$Long", "Ljava/lang/Class;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$class$groovy$lang$MetaClass", "Ljava/lang/Class;", null,
					null);
			fv.visitEnd();
		}
		{
			fv = cw
					.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
							"$class$java$lang$Integer", "Ljava/lang/Class;",
							null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$class$java$lang$System", "Ljava/lang/Class;", null, null);
			fv.visitEnd();
		}
		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$callSiteArray", "Ljava/lang/ref/SoftReference;", null,
					null);
			fv.visitEnd();
		}
		dump_init(cw);
		dump_init2(cw);
		dump_bottomUpTree(cw);
		dump_itemCheck(cw);
		dump_main(cw);
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "getProperty",
					"(Ljava/lang/String;)Ljava/lang/Object;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "TreeNode", "getMetaClass",
					"()Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEINTERFACE, "groovy/lang/MetaClass",
					"getProperty",
					"(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l3);
			mv.visitInsn(NOP);
			mv.visitLocalVariable("this", "LTreeNode;", null, l0, l2, 0);
			mv.visitLocalVariable("property", "Ljava/lang/String;", null, l0,
					l2, 1);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "setProperty",
					"(Ljava/lang/String;Ljava/lang/Object;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 3);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "TreeNode", "getMetaClass",
					"()Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitVarInsn(ALOAD, 2);
			mv
					.visitMethodInsn(INVOKEINTERFACE, "groovy/lang/MetaClass",
							"setProperty",
							"(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V");
			mv.visitInsn(RETURN);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitInsn(RETURN);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l3);
			mv.visitInsn(NOP);
			mv.visitLocalVariable("this", "LTreeNode;", null, l0, l2, 0);
			mv.visitLocalVariable("property", "Ljava/lang/String;", null, l0,
					l2, 1);
			mv.visitLocalVariable("value", "Ljava/lang/Object;", null, l0, l2,
					2);
			mv.visitMaxs(4, 4);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$java$lang$Class", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"castToType",
					"(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Class");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Class");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$ownClass",
					"Ljava/lang/Class;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Integer");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(0));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>",
					"(I)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$const$0",
					"Ljava/lang/Integer;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Integer");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(1));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>",
					"(I)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$const$1",
					"Ljava/lang/Integer;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Integer");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(2));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>",
					"(I)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$const$2",
					"Ljava/lang/Integer;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Integer");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(10));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>",
					"(I)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$const$3",
					"Ljava/lang/Integer;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Integer");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(4));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Integer", "<init>",
					"(I)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$const$4",
					"Ljava/lang/Integer;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Long");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Long(1216335190359L));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Long", "<init>",
					"(J)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "__timeStamp",
					"Ljava/lang/Long;");
			mv.visitInsn(POP);
			mv.visitTypeInsn(NEW, "java/lang/Long");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Long(0L));
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Long", "<init>",
					"(J)V");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode",
					"__timeStamp__239_neverHappen1216335190359",
					"Ljava/lang/Long;");
			mv.visitInsn(POP);
			mv.visitInsn(RETURN);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitJumpInsn(GOTO, l2);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l2);
			mv.visitInsn(NOP);
			mv.visitMaxs(4, 0);
			mv.visitEnd();
		}
		{
			mv = cw
					.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC,
							"this$2$bottomUpTree", "(II)Ljava/lang/Object;",
							null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ILOAD, 1);
			mv.visitVarInsn(ILOAD, 2);
			mv.visitMethodInsn(INVOKESPECIAL, "TreeNode", "bottomUpTree",
					"(II)Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$wait",
					"()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv
					.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "wait",
							"()V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$toString",
					"()Ljava/lang/String;", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "toString",
					"()Ljava/lang/String;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$wait",
					"(J)V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(LLOAD, 1);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "wait",
					"(J)V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$wait",
					"(JI)V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(LLOAD, 1);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "wait",
					"(JI)V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(4, 4);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$notify",
					"()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "notify",
					"()V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC,
					"super$1$notifyAll", "()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "notifyAll",
					"()V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$getClass",
					"()Ljava/lang/Class;", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "getClass",
					"()Ljava/lang/Class;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$equals",
					"(Ljava/lang/Object;)Z", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "equals",
					"(Ljava/lang/Object;)Z");
			mv.visitInsn(IRETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$clone",
					"()Ljava/lang/Object;", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "clone",
					"()Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$hashCode",
					"()I", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "hashCode",
					"()I");
			mv.visitInsn(IRETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "super$1$finalize",
					"()V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "finalize",
					"()V");
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$get$$class$TreeNode", "()Ljava/lang/Class;", null, null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$class$TreeNode",
					"Ljava/lang/Class;");
			mv.visitInsn(DUP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFNONNULL, l0);
			mv.visitInsn(POP);
			mv.visitLdcInsn("TreeNode");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$class$TreeNode",
					"Ljava/lang/Class;");
			mv.visitLabel(l0);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$get$$class$java$lang$Class", "()Ljava/lang/Class;", null,
					null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$class$java$lang$Class",
					"Ljava/lang/Class;");
			mv.visitInsn(DUP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFNONNULL, l0);
			mv.visitInsn(POP);
			mv.visitLdcInsn("java.lang.Class");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$class$java$lang$Class",
					"Ljava/lang/Class;");
			mv.visitLabel(l0);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$get$$class$java$lang$Long", "()Ljava/lang/Class;", null,
					null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$class$java$lang$Long",
					"Ljava/lang/Class;");
			mv.visitInsn(DUP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFNONNULL, l0);
			mv.visitInsn(POP);
			mv.visitLdcInsn("java.lang.Long");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$class$java$lang$Long",
					"Ljava/lang/Class;");
			mv.visitLabel(l0);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$get$$class$groovy$lang$MetaClass", "()Ljava/lang/Class;",
					null, null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode",
					"$class$groovy$lang$MetaClass", "Ljava/lang/Class;");
			mv.visitInsn(DUP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFNONNULL, l0);
			mv.visitInsn(POP);
			mv.visitLdcInsn("groovy.lang.MetaClass");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "TreeNode",
					"$class$groovy$lang$MetaClass", "Ljava/lang/Class;");
			mv.visitLabel(l0);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$get$$class$java$lang$Integer", "()Ljava/lang/Class;",
					null, null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode",
					"$class$java$lang$Integer", "Ljava/lang/Class;");
			mv.visitInsn(DUP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFNONNULL, l0);
			mv.visitInsn(POP);
			mv.visitLdcInsn("java.lang.Integer");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "TreeNode",
					"$class$java$lang$Integer", "Ljava/lang/Class;");
			mv.visitLabel(l0);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$get$$class$java$lang$System", "()Ljava/lang/Class;",
					null, null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$class$java$lang$System",
					"Ljava/lang/Class;");
			mv.visitInsn(DUP);
			Label l0 = new Label();
			mv.visitJumpInsn(IFNONNULL, l0);
			mv.visitInsn(POP);
			mv.visitLdcInsn("java.lang.System");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$class$java$lang$System",
					"Ljava/lang/Class;");
			mv.visitLabel(l0);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_STATIC + ACC_SYNTHETIC, "class$",
					"(Ljava/lang/String;)Ljava/lang/Class;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"java/lang/ClassNotFoundException");
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName",
					"(Ljava/lang/String;)Ljava/lang/Class;");
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitVarInsn(ASTORE, 1);
			mv.visitTypeInsn(NEW, "java/lang/NoClassDefFoundError");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL,
					"java/lang/ClassNotFoundException", "getMessage",
					"()Ljava/lang/String;");
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/NoClassDefFoundError",
					"<init>", "(Ljava/lang/String;)V");
			mv.visitInsn(ATHROW);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$createCallSiteArray",
					"()Lorg/codehaus/groovy/runtime/callsite/CallSiteArray;",
					null, null);
			mv.visitCode();
			mv.visitTypeInsn(NEW,
					"org/codehaus/groovy/runtime/callsite/CallSiteArray");
			mv.visitInsn(DUP);
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$ownClass",
					"Ljava/lang/Class;");
			mv.visitLdcInsn(new Integer(40));
			mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(0));
			mv.visitLdcInsn("minus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(1));
			mv.visitLdcInsn("multiply");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(2));
			mv.visitLdcInsn("<$constructor$>");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(3));
			mv.visitLdcInsn("bottomUpTree");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(4));
			mv.visitLdcInsn("minus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(5));
			mv.visitLdcInsn("bottomUpTree");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(6));
			mv.visitLdcInsn("<$constructor$>");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(7));
			mv.visitLdcInsn("minus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(8));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(9));
			mv.visitLdcInsn("itemCheck");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(10));
			mv.visitLdcInsn("itemCheck");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(11));
			mv.visitLdcInsn("currentTimeMillis");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(12));
			mv.visitLdcInsn("length");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(13));
			mv.visitLdcInsn("toInteger");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(14));
			mv.visitLdcInsn("getAt");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(15));
			mv.visitLdcInsn("max");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(16));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(17));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(18));
			mv.visitLdcInsn("itemCheck");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(19));
			mv.visitLdcInsn("bottomUpTree");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(20));
			mv.visitLdcInsn("println");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(21));
			mv.visitLdcInsn("bottomUpTree");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(22));
			mv.visitLdcInsn("leftShift");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(23));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(24));
			mv.visitLdcInsn("minus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(25));
			mv.visitLdcInsn("iterator");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(26));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(27));
			mv.visitLdcInsn("itemCheck");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(28));
			mv.visitLdcInsn("bottomUpTree");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(29));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(30));
			mv.visitLdcInsn("itemCheck");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(31));
			mv.visitLdcInsn("bottomUpTree");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(32));
			mv.visitLdcInsn("println");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(33));
			mv.visitLdcInsn("multiply");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(34));
			mv.visitLdcInsn("plus");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(35));
			mv.visitLdcInsn("currentTimeMillis");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(36));
			mv.visitLdcInsn("println");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(37));
			mv.visitLdcInsn("itemCheck");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(38));
			mv.visitLdcInsn("println");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitLdcInsn(new Integer(39));
			mv.visitLdcInsn("minus");
			mv.visitInsn(AASTORE);
			mv.visitMethodInsn(INVOKESPECIAL,
					"org/codehaus/groovy/runtime/callsite/CallSiteArray",
					"<init>", "(Ljava/lang/Class;[Ljava/lang/String;)V");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(7, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;", null,
					null);
			mv.visitCode();
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$callSiteArray",
					"Ljava/lang/ref/SoftReference;");
			Label l0 = new Label();
			mv.visitJumpInsn(IFNULL, l0);
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$callSiteArray",
					"Ljava/lang/ref/SoftReference;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/ref/SoftReference",
					"get", "()Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST,
					"org/codehaus/groovy/runtime/callsite/CallSiteArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ASTORE, 0);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$createCallSiteArray",
					"()Lorg/codehaus/groovy/runtime/callsite/CallSiteArray;");
			mv.visitVarInsn(ASTORE, 0);
			mv.visitTypeInsn(NEW, "java/lang/ref/SoftReference");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/ref/SoftReference",
					"<init>", "(Ljava/lang/Object;)V");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$callSiteArray",
					"Ljava/lang/ref/SoftReference;");
			mv.visitLabel(l1);
			mv.visitVarInsn(ALOAD, 0);
			mv
					.visitFieldInsn(
							GETFIELD,
							"org/codehaus/groovy/runtime/callsite/CallSiteArray",
							"array",
							"[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(3, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}

	private static void dump_main(ClassWriter cw) {
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_VARARGS, "main",
					"([Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray","()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(11));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode","$get$$class$java$lang$System", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKEINTERFACE,"org/codehaus/groovy/runtime/callsite/CallSite", "call", "(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue","()J");
			mv.visitVarInsn(LSTORE, 2);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(12));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"callGetProperty",
							"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(ICONST_0);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"compareEqual", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
			Label l2 = new Label();
			mv.visitJumpInsn(IFEQ, l2);
			mv.visitIntInsn(BIPUSH, 10);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l2);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(13));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(14));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_0);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitLabel(l3);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue",
					"()I");
			mv.visitVarInsn(ISTORE, 4);
			mv.visitInsn(ICONST_4);
			mv.visitVarInsn(ISTORE, 5);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(15));
			mv.visitInsn(AALOAD);
			mv.visitInsn(ICONST_2);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(IADD);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"createList", "([Ljava/lang/Object;)Ljava/util/List;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue",
					"()I");
			mv.visitVarInsn(ISTORE, 6);
			mv.visitVarInsn(ILOAD, 6);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(IADD);
			mv.visitVarInsn(ISTORE, 7);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(18));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(19));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitInsn(ICONST_0);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitVarInsn(ILOAD, 7);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"call",
							"(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue",
					"()I");
			mv.visitVarInsn(ISTORE, 8);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(20));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitTypeInsn(NEW, "org/codehaus/groovy/runtime/GStringImpl");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_2);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 7);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ILOAD, 8);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(ICONST_3);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitLdcInsn("stretch tree of depth ");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitLdcInsn("\u0009 check: ");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_2);
			mv.visitLdcInsn("");
			mv.visitInsn(AASTORE);
			mv.visitMethodInsn(INVOKESPECIAL,
					"org/codehaus/groovy/runtime/GStringImpl", "<init>",
					"([Ljava/lang/Object;[Ljava/lang/String;)V");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite",
					"callStatic",
					"(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(21));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitInsn(ICONST_0);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitVarInsn(ILOAD, 6);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"call",
							"(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ASTORE, 9);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitVarInsn(ISTORE, 10);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitVarInsn(ILOAD, 10);
			mv.visitVarInsn(ILOAD, 6);
			Label l5 = new Label();
			mv.visitJumpInsn(IF_ICMPGT, l5);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ILOAD, 6);
			mv.visitVarInsn(ILOAD, 10);
			mv.visitInsn(ISUB);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitInsn(IADD);
			mv.visitInsn(ISHL);
			mv.visitVarInsn(ISTORE, 11);
			mv.visitInsn(ICONST_0);
			mv.visitInsn(DUP);
			mv.visitVarInsn(ISTORE, 8);
			mv.visitInsn(POP);
			mv.visitInsn(ACONST_NULL);
			mv.visitVarInsn(ASTORE, 13);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(25));
			mv.visitInsn(AALOAD);
			mv.visitInsn(ICONST_1);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitVarInsn(ILOAD, 11);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(ICONST_1);
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"createRange",
					"(Ljava/lang/Object;Ljava/lang/Object;Z)Ljava/util/List;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ASTORE, 14);
			Label l6 = new Label();
			mv.visitLabel(l6);
			mv.visitVarInsn(ALOAD, 14);
			mv.visitTypeInsn(CHECKCAST, "java/util/Iterator");
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator",
					"hasNext", "()Z");
			Label l7 = new Label();
			mv.visitJumpInsn(IFEQ, l7);
			mv.visitVarInsn(ALOAD, 14);
			mv.visitTypeInsn(CHECKCAST, "java/util/Iterator");
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next",
					"()Ljava/lang/Object;");
			mv.visitVarInsn(ASTORE, 13);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(26));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ILOAD, 8);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(27));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(28));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitVarInsn(ALOAD, 13);
			mv.visitVarInsn(ILOAD, 10);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"call",
							"(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue",
					"()I");
			mv.visitVarInsn(ISTORE, 8);
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(29));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ILOAD, 8);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(30));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(31));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitVarInsn(ALOAD, 13);
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"unaryMinus", "(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ILOAD, 10);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"call",
							"(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue",
					"()I");
			mv.visitVarInsn(ISTORE, 8);
			mv.visitInsn(POP);
			mv.visitJumpInsn(GOTO, l6);
			mv.visitLabel(l7);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(32));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitTypeInsn(NEW, "org/codehaus/groovy/runtime/GStringImpl");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_3);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 11);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(IMUL);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ILOAD, 10);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_2);
			mv.visitVarInsn(ILOAD, 8);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(ICONST_4);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitLdcInsn("");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitLdcInsn("\u0009 trees of depth ");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_2);
			mv.visitLdcInsn("\u0009 check: ");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_3);
			mv.visitLdcInsn("");
			mv.visitInsn(AASTORE);
			mv.visitMethodInsn(INVOKESPECIAL,
					"org/codehaus/groovy/runtime/GStringImpl", "<init>",
					"([Ljava/lang/Object;[Ljava/lang/String;)V");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite",
					"callStatic",
					"(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ILOAD, 10);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(IADD);
			mv.visitInsn(DUP);
			mv.visitVarInsn(ISTORE, 10);
			mv.visitInsn(POP);
			mv.visitJumpInsn(GOTO, l4);
			mv.visitLabel(l5);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(35));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$java$lang$System", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue",
					"()J");
			mv.visitVarInsn(LSTORE, 11);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(36));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitTypeInsn(NEW, "org/codehaus/groovy/runtime/GStringImpl");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_2);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ILOAD, 6);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(37));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 9);
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(ICONST_3);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitLdcInsn("long lived tree of depth ");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitLdcInsn("\u0009 check: ");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_2);
			mv.visitLdcInsn("");
			mv.visitInsn(AASTORE);
			mv.visitMethodInsn(INVOKESPECIAL,
					"org/codehaus/groovy/runtime/GStringImpl", "<init>",
					"([Ljava/lang/Object;[Ljava/lang/String;)V");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite",
					"callStatic",
					"(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(38));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitTypeInsn(NEW, "org/codehaus/groovy/runtime/GStringImpl");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(LLOAD, 11);
			mv.visitVarInsn(LLOAD, 2);
			mv.visitInsn(LSUB);
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf",
					"(J)Ljava/lang/Long;");
			mv.visitInsn(AASTORE);
			mv.visitInsn(ICONST_2);
			mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_0);
			mv.visitLdcInsn("");
			mv.visitInsn(AASTORE);
			mv.visitInsn(DUP);
			mv.visitInsn(ICONST_1);
			mv.visitLdcInsn("ms");
			mv.visitInsn(AASTORE);
			mv.visitMethodInsn(INVOKESPECIAL,
					"org/codehaus/groovy/runtime/GStringImpl", "<init>",
					"([Ljava/lang/Object;[Ljava/lang/String;)V");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite",
					"callStatic",
					"(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(POP);
			mv.visitInsn(RETURN);
			mv.visitInsn(RETURN);
			Label l8 = new Label();
			mv.visitJumpInsn(GOTO, l8);
			mv.visitLabel(l1);
			mv.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l8);
			mv.visitInsn(NOP);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PROTECTED + ACC_SYNTHETIC,
					"$getStaticMetaClass", "()Lgroovy/lang/MetaClass;", null,
					null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$staticMetaClass",
					"Ljava/lang/ref/SoftReference;");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 1);
			Label l2 = new Label();
			mv.visitJumpInsn(IFNULL, l2);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/ref/SoftReference",
					"get", "()Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "groovy/lang/MetaClass");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ASTORE, 1);
			Label l3 = new Label();
			mv.visitJumpInsn(IFNONNULL, l3);
			mv.visitLabel(l2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass",
					"()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/InvokerHelper",
					"getMetaClass",
					"(Ljava/lang/Class;)Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitTypeInsn(NEW, "java/lang/ref/SoftReference");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/ref/SoftReference",
					"<init>", "(Ljava/lang/Object;)V");
			mv.visitFieldInsn(PUTSTATIC, "TreeNode", "$staticMetaClass",
					"Ljava/lang/ref/SoftReference;");
			mv.visitLabel(l3);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitInsn(ARETURN);
			mv.visitInsn(RETURN);
			Label l4 = new Label();
			mv.visitLabel(l4);
			Label l5 = new Label();
			mv.visitJumpInsn(GOTO, l5);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l5);
			mv.visitInsn(NOP);
			mv.visitLocalVariable("this", "LTreeNode;", null, l0, l4, 0);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "getMetaClass",
					"()Lgroovy/lang/MetaClass;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(DUP);
			Label l2 = new Label();
			mv.visitJumpInsn(IFNULL, l2);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l2);
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "TreeNode",
					"$getStaticMetaClass", "()Lgroovy/lang/MetaClass;");
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(ARETURN);
			mv.visitInsn(RETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			Label l4 = new Label();
			mv.visitJumpInsn(GOTO, l4);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l4);
			mv.visitInsn(NOP);
			mv.visitLocalVariable("this", "LTreeNode;", null, l0, l3, 0);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "setMetaClass",
					"(Lgroovy/lang/MetaClass;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitInsn(RETURN);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l3);
			mv.visitInsn(NOP);
			mv.visitLocalVariable("this", "LTreeNode;", null, l0, l2, 0);
			mv.visitLocalVariable("mc", "Lgroovy/lang/MetaClass;", null, l0,
					l2, 1);
			mv.visitMaxs(2, 3);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC, "invokeMethod",
					"(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 3);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "TreeNode", "getMetaClass",
					"()Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitVarInsn(ALOAD, 2);
			mv
					.visitMethodInsn(INVOKEINTERFACE,
							"groovy/lang/MetaObjectProtocol", "invokeMethod",
							"(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l3);
			mv.visitInsn(NOP);
			mv.visitLocalVariable("this", "LTreeNode;", null, l0, l2, 0);
			mv.visitLocalVariable("method", "Ljava/lang/String;", null, l0, l2,
					1);
			mv.visitLocalVariable("arguments", "Ljava/lang/Object;", null, l0,
					l2, 2);
			mv.visitMaxs(4, 4);
			mv.visitEnd();
		}
	}

	private static void dump_itemCheck(ClassWriter cw) {
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, "itemCheck", "()I", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "left",
					"Ljava/lang/Object;");
			mv.visitInsn(ACONST_NULL);
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"compareEqual", "(Ljava/lang/Object;Ljava/lang/Object;)Z");
			Label l2 = new Label();
			mv.visitJumpInsn(IFEQ, l2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "item", "I");
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$java$lang$Integer", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"castToType",
					"(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"intUnbox", "(Ljava/lang/Object;)I");
			mv.visitInsn(IRETURN);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l2);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(7));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(8));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "item", "I");
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(9));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "left",
					"Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(new Integer(10));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "right",
					"Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$java$lang$Integer", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"castToType",
					"(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"intUnbox", "(Ljava/lang/Object;)I");
			mv.visitInsn(IRETURN);
			mv.visitLabel(l3);
			Label l4 = new Label();
			mv.visitJumpInsn(GOTO, l4);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l4);
			mv.visitInsn(NOP);
			mv.visitMaxs(5, 2);
			mv.visitEnd();
		}
	}

	private static void dump_bottomUpTree(ClassWriter cw) {
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PRIVATE + ACC_STATIC, "bottomUpTree",
					"(II)Ljava/lang/Object;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ILOAD, 1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$const$0",
					"Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"compareGreaterThan",
					"(Ljava/lang/Object;Ljava/lang/Object;)Z");
			Label l2 = new Label();
			mv.visitJumpInsn(IFEQ, l2);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(0));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ILOAD, 1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$const$1",
					"Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(DUP);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"intUnbox", "(Ljava/lang/Object;)I");
			mv.visitVarInsn(ISTORE, 1);
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(1));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ILOAD, 0);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$const$2",
					"Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$java$lang$Integer", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"castToType",
					"(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitVarInsn(ASTORE, 3);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(2));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(3));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(4));
			mv.visitInsn(AALOAD);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitFieldInsn(GETSTATIC, "TreeNode", "$const$1",
					"Ljava/lang/Integer;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite", "call",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ILOAD, 1);
			mv.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"callStatic",
							"(Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(5));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ILOAD, 1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv
					.visitMethodInsn(INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"callStatic",
							"(Ljava/lang/Class;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitVarInsn(ILOAD, 0);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv
					.visitMethodInsn(
							INVOKEINTERFACE,
							"org/codehaus/groovy/runtime/callsite/CallSite",
							"callConstructor",
							"(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l2);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitLdcInsn(new Integer(6));
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$TreeNode", "()Ljava/lang/Class;");
			mv.visitVarInsn(ILOAD, 0);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitMethodInsn(INVOKEINTERFACE,
					"org/codehaus/groovy/runtime/callsite/CallSite",
					"callConstructor",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
			mv.visitLabel(l3);
			Label l4 = new Label();
			mv.visitJumpInsn(GOTO, l4);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l4);
			mv.visitInsn(NOP);
			mv.visitMaxs(7, 4);
			mv.visitEnd();
		}
	}

	private static void dump_init2(ClassWriter cw) {
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>",
					"(Ljava/lang/Object;Ljava/lang/Object;I)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
					"()V");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 4);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "TreeNode",
					"$getStaticMetaClass", "()Lgroovy/lang/MetaClass;");
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$groovy$lang$MetaClass", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"castToType",
					"(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "groovy/lang/MetaClass");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(SWAP);
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(SWAP);
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "left",
					"Ljava/lang/Object;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(SWAP);
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "right",
					"Ljava/lang/Object;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ILOAD, 3);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(SWAP);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"intUnbox", "(Ljava/lang/Object;)I");
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "item", "I");
			mv.visitInsn(POP);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitJumpInsn(GOTO, l2);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l2);
			mv.visitInsn(NOP);
			mv.visitMaxs(3, 5);
			mv.visitEnd();
		}
	}

	private static void dump_init(ClassWriter cw) {
		MethodVisitor mv;
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(I)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			mv.visitTryCatchBlock(l0, l1, l1,
					"groovy/lang/GroovyRuntimeException");
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
					"()V");
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode", "$getCallSiteArray",
					"()[Lorg/codehaus/groovy/runtime/callsite/CallSite;");
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKEVIRTUAL, "TreeNode",
					"$getStaticMetaClass", "()Lgroovy/lang/MetaClass;");
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESTATIC, "TreeNode",
					"$get$$class$groovy$lang$MetaClass", "()Ljava/lang/Class;");
			mv.visitMethodInsn(INVOKESTATIC,
					"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
					"castToType",
					"(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "groovy/lang/MetaClass");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(SWAP);
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "metaClass",
					"Lgroovy/lang/MetaClass;");
			mv.visitInsn(POP);
			mv.visitVarInsn(ILOAD, 1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"box", "(I)Ljava/lang/Object;");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(SWAP);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation",
							"intUnbox", "(Ljava/lang/Object;)I");
			mv.visitFieldInsn(PUTFIELD, "TreeNode", "item", "I");
			mv.visitInsn(POP);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitJumpInsn(GOTO, l2);
			mv.visitLabel(l1);
			mv
					.visitMethodInsn(
							INVOKESTATIC,
							"org/codehaus/groovy/runtime/ScriptBytecodeAdapter",
							"unwrap",
							"(Lgroovy/lang/GroovyRuntimeException;)Ljava/lang/Throwable;");
			mv.visitInsn(ATHROW);
			mv.visitLabel(l2);
			mv.visitInsn(NOP);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
	}
}
