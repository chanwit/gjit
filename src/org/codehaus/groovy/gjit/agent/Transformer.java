package org.codehaus.groovy.gjit.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Stack;

import org.codehaus.groovy.gjit.agent.instrumentor.CallSiteArrayInstrumentor;
import org.codehaus.groovy.gjit.agent.instrumentor.MetaClassInstumentor;
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
		}else if(className.equals("org/codehaus/groovy/runtime/callsite/CallSiteArray")) {
			return instrumentingCallSiteArray(classfileBuffer);
		} else if(className.startsWith("java") || className.startsWith("sun") || className.startsWith("soot")) {
			return classfileBuffer;
		} else {
			// TODO scan file here
			return classfileBuffer;
		}
		
	}
	
	private byte[] instrumentingCallSiteArray(byte[] classfileBuffer) {
		ClassReader cr = new ClassReader(classfileBuffer);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		CallSiteArrayInstrumentor csai = new CallSiteArrayInstrumentor(cw);
		cr.accept(csai, 0);
		return cw.toByteArray();	
	}

	private byte[] instrumentingMetaClass(byte[] classfileBuffer) {		
		ClassReader cr = new ClassReader(classfileBuffer);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		MetaClassInstumentor mci = new MetaClassInstumentor(cw);
		cr.accept(mci, 0);
		return cw.toByteArray();
	}

}
