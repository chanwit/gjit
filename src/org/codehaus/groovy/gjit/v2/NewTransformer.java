package org.codehaus.groovy.gjit.v2;

import java.util.ListIterator;

import org.codehaus.groovy.gjit.ConstantPack;
import org.codehaus.groovy.gjit.MyBasicInterpreter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Value;

public class NewTransformer extends BaseTransformer {

	private String[] siteNames;
	private ConstantPack pack;
	private Integer currentSiteIndex;

	public NewTransformer(String owner, MethodNode mn, ConstantPack pack, String[] siteNames) {
		super(new MyBasicInterpreter(),owner, mn);
		this.pack = pack;
		this.siteNames = siteNames;  
	}

	@Override
	public void transform() throws AnalyzerException {
		// 1. gathering data, creating used nodes
		// done by super.transform();
		super.transform();
		// 2. process
		process();
	}

	private void process() {
		ListIterator<?> stmts = units.iterator();
		while(stmts.hasNext()) {
			AbstractInsnNode s = (AbstractInsnNode)stmts.next();
			if(extractCallSiteName(s)) continue;
			if(eliminateBoxCastUnbox(s)) continue;
//			if(unwrapConst(s)) continue;
//			if(unwrapBoxOrUnbox(s)) continue;
			if(unwrapBinaryPrimitiveCall(s)) continue;
//			if(unwrapCompare(s,frame)) continue;
//			if(clearCast(s)) continue;			
//			if(correctNormalCall(s)) continue;
//			if(correctLocalType(s)) continue;			
//			if(correctUnbox(s)) continue;
		}
	}

	private boolean extractCallSiteName(AbstractInsnNode s) {
		if(s.getOpcode() != ALOAD) return false;
		VarInsnNode v = (VarInsnNode)s;
		if(v.var != callSiteVar) return false;
		AbstractInsnNode s1 = s.getNext();
		if(s1.getOpcode() != LDC) return false;
		LdcInsnNode l = (LdcInsnNode)s1;
		currentSiteIndex = (Integer)l.cst;
		return true;
	}
	
	private boolean eliminateBoxCastUnbox(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKESTATIC) return false;
		AbstractInsnNode s1 = s.getNext();    if(s1 == null) return false;
		if(s1.getOpcode() != INVOKESTATIC) return false;
		AbstractInsnNode s2 = s1.getNext();   if(s2 == null) return false;
		if(s2.getOpcode() != INVOKESTATIC) return false;		
		AbstractInsnNode s3 = s2.getNext();   if(s3 == null) return false;
		if(s3.getOpcode() != CHECKCAST) return false;				
		AbstractInsnNode s4 = s3.getNext();	  if(s4 == null) return false;
		if(s4.getOpcode() != INVOKESTATIC) return false;						
		MethodInsnNode m = (MethodInsnNode)s;
		MethodInsnNode m1 = (MethodInsnNode)s1;
		MethodInsnNode m2 = (MethodInsnNode)s2;
		MethodInsnNode m4 = (MethodInsnNode)s4;		
		if(m.name.equals("box") == false) return false;
		if(m1.name.startsWith("$get$$class$") == false) return false;
		if(m2.name.startsWith("castToType") == false) return false;
		if(m4.name.endsWith("Unbox")== false) return false;
		//adjustUsebox(s.getPrevious(),s,s1,s2,s3,s4);
		units.remove(s);
		units.remove(s1);
		units.remove(s2);
		units.remove(s3);
		units.remove(s4);
		return true;
	}
	
	private static final String SCRIPT_BYTECODE_ADAPTER = "org/codehaus/groovy/runtime/ScriptBytecodeAdapter";
	private static final String CALL_SITE_INTERFACE = "org/codehaus/groovy/runtime/callsite/CallSite";
	private static final String DEFAULT_TYPE_TRANSFORMATION = "org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation";	

	private enum BinOp {
		minus,
		plus,
		multiply,
		div,
		leftShift,
		rightShift
	}		
	
	private boolean unwrapBinaryPrimitiveCall(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKEINTERFACE) return false;
		MethodInsnNode iv = (MethodInsnNode)s;
		if(iv.owner.equals(CALL_SITE_INTERFACE) == false) return false;
		if(iv.name.equals("call") == false) return false;
		if(iv.desc.equals("(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;") == false) return false;
		String name = siteNames[currentSiteIndex];
		// System.out.println("frame: " + frame);
		BinOp op=null;
		try {op = BinOp.valueOf(name);} catch(IllegalArgumentException e){}
		if(op == null) return false;
		// TODO check type from "frame"
		Frame frame = frames.get(s);
		System.out.println("frame: " + frame);
		
		int oldIndex = units.indexOf(s);
		
		// if(s.getPrevious().getOpcode()==LLOAD) System.out.println(">> Found it !!");
		Value v2 = frame.getStack(frame.getStackSize()-1); // peek
		Value v1 = frame.getStack(frame.getStackSize()-2); // peek
		// TODO if(v1.sort != v2.sort) do something
		System.out.println("v1:" +v1);
		System.out.println("v2:" +v2);
		int offset = 0;
		if(((BasicValue)v1).getType().equals(Type.getType(Long.class))) offset = 1;
		else if(((BasicValue)v1).getType().equals(Type.getType(Float.class))) offset = 2;
		else if(((BasicValue)v1).getType().equals(Type.getType(Double.class))) offset = 3;
		switch(op) {
			case minus:
				units.set(s, new InsnNode(ISUB + offset)); break;
			case plus:
				units.set(s, new InsnNode(IADD + offset)); break;
			case multiply:
				units.set(s, new InsnNode(IMUL + offset)); break;
			case div:
				units.set(s, new InsnNode(IDIV + offset)); break;
			case leftShift:
				units.set(s, new InsnNode(ISHL + offset)); break;
			case rightShift:
				units.set(s, new InsnNode(ISHR + offset)); break;
		}
		s = units.get(oldIndex);
		if(offset==0) {
		// SWAP,
		// POP
			units.insert(s, new InsnNode(POP));
			units.insert(s, new InsnNode(SWAP));
		} else if(offset < 3){			
			units.insert(s, new InsnNode(POP));
			units.insert(s, new InsnNode(POP2));
			units.insert(s, new InsnNode(DUP2_X1));			
		} else if(offset == 3) {
			// TODO handling "double"
		}
		
		return true;
	}	
	
//	// hold last node
//	class ValuePathMap extends HashMap<AbstractInsnNode,ValuePath> {
//		
//	}
//	
//	class ValuePath extends LinkedList<AbstractInsnNode> {
//		private static final long serialVersionUID = 2295291608329209290L;		
//	}
//
//	private void adjustUsebox(AbstractInsnNode newRef, AbstractInsnNode... nodes) {
//		// use = <s, {v1(s0), v2(s1), v3(s2)}>; data used at s, produced by s0, s1, s2
//		// s0: LDC 20         ;  use = <s0, {v1(null)}>
//		// s1: LDC 10         ;  use = <s1, {v1(null)}>
//		// s2: invoke box(int);  use = <s2, {v1(s1)}> ; path<s2, {null,s1,s2}> 
//		// s3: invoke call(int,Object)  ;  use = <s3, {v1(s0),v2(s2)}> ; path<s3,{}
//		// PATH = 
//		// s4: invoke println ;  use = <s4, {v1(s3)}>
//		// if s2 was deleted, then all value produced by s2 (v?(s2)) must be subst with v?(
//		// if s3 was deleted, then 
//		Set s;
//		
//	}	
	

}
