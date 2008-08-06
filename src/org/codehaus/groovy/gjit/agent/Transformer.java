package org.codehaus.groovy.gjit.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Stack;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Transformer implements ClassFileTransformer {
				
	public Transformer() {
		super();
	}

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		//
		// check the condition which class is going to be transform
		// do transformation
		//
		if(className.equals("groovy/lang/MetaClassImpl")) {
			return instrumentingMetaClass(classfileBuffer);
		} else if(className.startsWith("java") || className.startsWith("sun") || className.startsWith("soot")) {
			return classfileBuffer;
		} else {
			// TODO scan file here
			return classfileBuffer;
		}
		
	}
	
	class ProfilerMethodAdapter extends MethodAdapter implements Opcodes {

		private Stack<Integer> params = new Stack<Integer>();
		private String mname; 
		
		public ProfilerMethodAdapter(String name, MethodVisitor mv) {
			super(mv);
			this.mname = name;			
		}
				
		@Override
		public void visitVarInsn(int opcode, int var) {
			// keep track of ALOAD x to use in visitMethodInsn
			if(opcode==ALOAD) params.push(var);
			super.visitVarInsn(opcode, var);
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name,String desc) {
			if(opcode == INVOKESPECIAL) {
				if(name.equals("getMethodWithCachingInternal")) {
					if(desc.equals("(Ljava/lang/Class;Lorg/codehaus/groovy/runtime/callsite/CallSite;[Ljava/lang/Class;)Lgroovy/lang/MetaMethod;")) {
						super.visitMethodInsn(opcode, owner, name, desc);
						params.pop(); // params
						mv.visitInsn(DUP); // dup result (MetaMethod)
						if(mname.equals("createPojoCallSite")) {
							int arg1 = params.pop(); // site
							mv.visitVarInsn(ALOAD, 0);
							mv.visitMethodInsn(INVOKEVIRTUAL, "groovy/lang/MetaClassImpl", "getTheClass", "()Ljava/lang/Class;");
							mv.visitVarInsn(ALOAD, arg1);
						} else if(mname.equals("createPogoCallSite")) {
							int arg1 = params.pop(); // site							
							mv.visitVarInsn(ALOAD, 0);
							mv.visitFieldInsn(GETFIELD, "groovy/lang/MetaClassImpl", "theClass", "Ljava/lang/Class;");
							mv.visitVarInsn(ALOAD, arg1);
						} else {
							int arg1 = params.pop(); // site
							int arg0 = params.pop(); // sender							
							mv.visitVarInsn(ALOAD, arg0);
							mv.visitVarInsn(ALOAD, arg1);
						}
						// public static record(Lgroovy/lang/MetaMethod;Ljava/lang/Class;Lorg/codehaus/groovy/runtime/callsite/CallSite;)V						
						mv.visitMethodInsn(INVOKESTATIC, 
								"org/codehaus/groovy/gjit/db/SiteTypeHelper", 
								"record", 
								"(Lgroovy/lang/MetaMethod;Ljava/lang/Class;Lorg/codehaus/groovy/runtime/callsite/CallSite;)V");
						return;
					}
				}
			}
			super.visitMethodInsn(opcode, owner, name, desc);
		}		
	}
	
	class MetaClassInstument extends ClassAdapter {

		public MetaClassInstument(ClassVisitor cv) {
			super(cv);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc,
				String signature, String[] exceptions) {
			if(name.equals("createPogoCallCurrentSite") ||
				name.equals("createPogoCallSite") || 
				name.equals("createPojoCallSite")) {				
				return new ProfilerMethodAdapter(name, super.visitMethod(access, name, desc, signature, exceptions));
			}
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
	}

	private byte[] instrumentingMetaClass(byte[] classfileBuffer) {		
		ClassReader cr = new ClassReader(classfileBuffer);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		MetaClassInstument mci = new MetaClassInstument(cw);
		cr.accept(mci, 0);
		return cw.toByteArray();
	}

}
