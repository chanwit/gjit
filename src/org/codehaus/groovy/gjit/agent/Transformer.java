package org.codehaus.groovy.gjit.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class Transformer implements ClassFileTransformer {
		
	private static final String SCRIPT_BYTECODE_ADAPTER = "org/codehaus/groovy/runtime/ScriptBytecodeAdapter";
		
	public Transformer() {
		super();
	}

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		//
		// check the condition which class is going to be transform
		// do trasformation
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

	private byte[] instrumentingMetaClass(byte[] classfileBuffer) {		
		return null;
	}

}
