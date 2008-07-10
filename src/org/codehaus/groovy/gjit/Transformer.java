package org.codehaus.groovy.gjit;
import java.util.ListIterator;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.Frame;
// import org.objectweb.asm.util.TraceMethodVisitor;


public class Transformer extends Analyzer implements Opcodes {

	private static final String CALL_SITE_INTERFACE = "org/codehaus/groovy/runtime/callsite/CallSite";
	private static final String DEFAULT_TYPE_TRANSFORMATION = "org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation";
	private InsnList units;
	private MethodNode node;

	private enum Phase {
		PHASE_CALLSITE,
		PHASE_NEXT_1,
		PHASE_NEXT_2,
		
		PHASE_ERROR
	}
	
	private enum CallSiteState {
		START,
		FOUND_CALLSITE_INST,
		END, 
		
		ERROR,
	};
	
	private Phase phase = Phase.PHASE_CALLSITE;
	// private CallSiteState state = CallSiteState.START;
	
	private int callSiteVar = -1;
	private ConstantPack pack;
	private String[] siteNames;
	private Integer currentSiteIndex = -1;
	private String owner;
	
	public Transformer(String owner, MethodNode mn, ConstantPack pack, String[] siteNames) {
		super(new BasicInterpreter());
		this.owner = owner;
		this.node = mn;
		this.units = node.instructions;
		this.pack = pack;
		this.siteNames = siteNames;
	}
	
	public void transform() throws AnalyzerException {
		preTransform();
		this.analyze(this.owner, this.node);
//		TraceMethodVisitor t = new TraceMethodVisitor(null);
//		node.accept(t);
//		System.out.println(t.text);		
	}	
	   
	@Override
	public Action process(InsnList units, Map<AbstractInsnNode, Frame> frames, AbstractInsnNode s) {		
		Frame frame = frames.get(s);
//		 System.out.println(frame);
//		 System.out.println("index: " + units.indexOf(s));
		if(extractCallSiteName(s)) return Action.NONE;
		if(unwrapConst(s)) return Action.REPLACE;
		if(unwrapBoxOrUnbox(s)) return Action.REMOVE;
		if(unwrapBinaryPrimitiveCall(s, frame)) return Action.REPLACE;
		if(unwrapCompare(s,frame)) return Action.REMOVE;
// TODO: clearCast(s);			
//		if(correctNormalCall(s)) continue;
//		correctTypeIfPrimitive(s);			
		
		return Action.NONE;
	}

	private enum ComparingMethod { 
		compareLessThan, 
		compareGreaterThan, 
		compareLessThanEqual, 
		compareGreaterThanEqual
	};	
	
	private boolean unwrapCompare(AbstractInsnNode s, Frame frame) {
		if(s.getOpcode() != Opcodes.INVOKESTATIC) return false;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.owner.equals("org/codehaus/groovy/runtime/ScriptBytecodeAdapter")==false) return false;
		if(m.name.startsWith("compare")==false) return false;
		if(m.desc.equals("(Ljava/lang/Object;Ljava/lang/Object;)Z")==false) return false;
		JumpInsnNode s1 = (JumpInsnNode)s.getNext();
		ComparingMethod compare = ComparingMethod.valueOf(m.name);
//		System.out.println(">>>>> did unwrapping compare");
		switch(compare) {
			case compareGreaterThan:
				units.set(s1, new JumpInsnNode(IF_ICMPLE, s1.label)); break;
			case compareGreaterThanEqual:
				units.set(s1, new JumpInsnNode(IF_ICMPLT, s1.label)); break;
			case compareLessThan:				
				units.set(s1, new JumpInsnNode(IF_ICMPGE, s1.label)); break;
			case compareLessThanEqual:
				units.set(s1, new JumpInsnNode(IF_ICMPGT, s1.label)); break;				
			}
		units.remove(s);
		return true;
	}

	public void preTransform() {
		ListIterator<AbstractInsnNode> stmts = units.iterator();
		while(stmts.hasNext()) {
			AbstractInsnNode s = stmts.next();
			switch(phase) {
				case PHASE_CALLSITE: phase = processPhaseCallSite(s); break;
			}	
		}				
	}

	private Phase processPhaseCallSite(AbstractInsnNode s0) {
		CallSiteState state = CallSiteState.START;
		AbstractInsnNode s = s0;
		while(true) {
			switch(state) {
				case START: 
					state = detectCallSiteInst(state, s); 
					break;
				case FOUND_CALLSITE_INST: 
					state = detectCallSiteVar(state, s); 
					break;
				
				case END: return Phase.PHASE_NEXT_1;
				case ERROR: return Phase.PHASE_ERROR;				
			}
			s = s.getNext();
			if(s == null) state = CallSiteState.ERROR;
		}
	}

	private CallSiteState detectCallSiteVar(CallSiteState state, AbstractInsnNode s) {
		if(s.getOpcode() != ASTORE) return state;
		VarInsnNode v = (VarInsnNode)s;
		callSiteVar = v.var;
		return CallSiteState.END;
	}

	private CallSiteState detectCallSiteInst(CallSiteState state, AbstractInsnNode s) {
		if(s.getOpcode() != INVOKESTATIC) return state;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.name.equals("$getCallSiteArray")) return CallSiteState.FOUND_CALLSITE_INST;
		return state;
	}
	
	private enum BinOp {
		minus,
		plus,
		multiply,
		div,
		leftShift,
		rightShift
	}		

	private boolean unwrapBinaryPrimitiveCall(AbstractInsnNode s, Frame frame) {
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
		switch(op) {
			case minus:
				units.set(s, new InsnNode(ISUB)); break;
			case plus:
				units.set(s, new InsnNode(IADD)); break;
			case multiply:
				units.set(s, new InsnNode(IMUL)); break;
			case div:
				units.set(s, new InsnNode(IDIV)); break;
			case leftShift:
				units.set(s, new InsnNode(ISHL)); break;
			case rightShift:
				units.set(s, new InsnNode(ISHR)); break;
		}
		return true;
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

	private boolean unwrapConst(AbstractInsnNode s) {		
		if(s.getOpcode() != GETSTATIC) return false;		
		// System.out.println(">> called unwrapConst ");
		FieldInsnNode f = (FieldInsnNode)s;
		System.out.println(f.name);
		if(f.name.startsWith("$const$")) {
			LdcInsnNode newS = new LdcInsnNode(pack.get(f.name));
			// System.out.println(newS);
			units.set(s, newS);
			// System.out.println(">> replaced !");
			return true;
		}
		return false;
	} 
	
	private boolean unwrapBoxOrUnbox(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKESTATIC) return false;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.owner.equals(DEFAULT_TYPE_TRANSFORMATION)==false) return false;
		if(m.name.equals("box")) {
			units.remove(s);
			return true;
		} else if(m.name.endsWith("Unbox")) {
			units.remove(s);
			return true;
		}
		return false;
	}

}
