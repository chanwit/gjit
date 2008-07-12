package org.codehaus.groovy.gjit;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.analysis.BasicValue;

public class DefValue extends BasicValue {

	final AbstractInsnNode source;

	public DefValue(AbstractInsnNode insn, Type type) {
		super(type);
		this.source = insn;
	}
	
}
