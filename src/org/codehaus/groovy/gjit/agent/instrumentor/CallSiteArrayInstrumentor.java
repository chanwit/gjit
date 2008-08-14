package org.codehaus.groovy.gjit.agent.instrumentor;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CallSiteArrayInstrumentor extends ClassAdapter implements Opcodes {

    class AMethodAdapter extends MethodAdapter {

        public AMethodAdapter(MethodVisitor mv) {
            super(mv);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            // INVOKEINTERFACE org/codehaus/groovy/runtime/callsite/CallSite.call(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
            if(opcode == INVOKEINTERFACE && name.startsWith("call")) {
                super.visitMethodInsn(opcode, owner, name, desc);
                mv.visitVarInsn(ALOAD, 0); // arg[0] = callsite
                mv.visitMethodInsn(INVOKESTATIC,
                        "org/codehaus/groovy/gjit/db/SiteTypeHelper",
                        "record",
                        "(Ljava/lang/Object;Lorg/codehaus/groovy/runtime/callsite/CallSite;)Ljava/lang/Object;");
                return;
            }
            super.visitMethodInsn(opcode, owner, name, desc);
        }
    }

    public CallSiteArrayInstrumentor(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
            String signature, String[] exceptions) {
        if(name.startsWith("default") && desc.startsWith("(Lorg/codehaus/groovy/runtime/callsite/CallSite;")) {
            return new AMethodAdapter(super.visitMethod(access, name, desc, signature, exceptions));
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

}
