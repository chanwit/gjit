package org.codehaus.groovy.gjit;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

public class GJITPreProcess extends ClassAdapter {
	
	private String className;
	private boolean optimisable = false;
	// private List<MethodNode> methods = new ArrayList<MethodNode>();
	private Map<String, MethodNode> methods = new HashMap<String, MethodNode>(); 

	public GJITPreProcess(ClassVisitor cv) {
		super(cv);
	}
	
	enum CallSiteCollectingState {
		START,
		GOT_SIZE,
		GOT_INDEX,
		GOT_NAME
	};
			
	class CallSiteCollectorMV extends MethodNode {
	
		CallSiteCollectingState state = CallSiteCollectingState.START;
		
		private int size;
		private int index;
		private String[] names;

		public CallSiteCollectorMV(int access, String name, String desc,
				String signature, String[] exceptions) {
			super(access, name, desc, signature, exceptions);
		}

		@Override
		public void visitLdcInsn(Object cst) {
			switch(state) {
				case START: size = (Integer)cst;
							names = new String[size];
							state = CallSiteCollectingState.GOT_SIZE;
							break;
				case GOT_SIZE:
				case GOT_NAME:	index = (Integer)cst;
								state = CallSiteCollectingState.GOT_INDEX;
								break;
				case GOT_INDEX: String name = (String)cst;
								names[index] = name;
								state = CallSiteCollectingState.GOT_NAME;
								break;							
			}
			super.visitLdcInsn(cst);
		}

		@Override
		public void visitEnd() {
			super.visitEnd();
			CallSiteArrayPack.v().put(className, names);
		}
		
	}	
	
	enum ConstantCollectingState {
		START,
		GOT_NAME,
		GOT_VALUE
	};	
	
	class ConstantCollectorMV extends MethodNode {

		private ConstantCollectingState state = ConstantCollectingState.START;
		private ConstantPack pack = new ConstantPack();
		private String key;
		
		public ConstantCollectorMV(int access, String name, String desc,
				String signature, String[] exceptions) {
			super(access, name, desc, signature, exceptions);
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name,
				String desc) {
			if(state == ConstantCollectingState.START || 
			   state == ConstantCollectingState.GOT_VALUE) {
				if(name.startsWith("$const$")) {
					key = name;
					state = ConstantCollectingState.GOT_NAME;
				}
				// TODO get __timeStamp
			}
			super.visitFieldInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitLdcInsn(Object cst) {
			if(state == ConstantCollectingState.GOT_NAME) {
				pack.put(key, cst);
				state = ConstantCollectingState.GOT_VALUE;
			}
			super.visitLdcInsn(cst);
		}

		@Override
		public void visitEnd() {
			super.visitEnd();
			ConstantRecord.v().put(className, pack);
		}
		
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.className = name;
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc,String signature, Object value) {
		if(name.startsWith("__timeStamp__")==true) {
			this.optimisable = true;
		}
		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		if(this.optimisable == true) {
			if(name.equals("$createCallSiteArray")) {
				MethodNode mn = new CallSiteCollectorMV(access, name, desc, signature, exceptions);
				MethodNode mn2 = new MethodNode(access, name, desc, signature, exceptions);
				mn2.accept(cv);
				methods.put(name+desc, mn2);
				return mn;
			} else if(name.equals("<clinit>")) {
				MethodNode mn = new ConstantCollectorMV(access, name, desc, signature, exceptions);
				MethodNode mn2 = new MethodNode(access, name, desc, signature, exceptions);
				mn2.accept(cv);
				methods.put(name+desc, mn2);
				return mn;
			} 			
		} 
		MethodNode mn = new MethodNode(access, name, desc, signature, exceptions);
		methods.put(name+desc, mn);
		return mn;
	}

	public static void main(String[] args) throws Throwable {
		RandomAccessFile r = new RandomAccessFile(new File("C:\\groovy-ck1\\groovy\\my\\TreeNode.class"), "r");		
		byte[] bytes = new byte[(int) r.length()];
		r.readFully(bytes);
		ClassReader cr = new ClassReader(bytes);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);		
		GJITPreProcess cv = new GJITPreProcess(cw);
		cr.accept(cv, 0);
	}

	public Map<String, MethodNode> getMethods() {
		return methods;
	}

}
