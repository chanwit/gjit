package org.codehaus.groovy.gjit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Interpreter;
import org.objectweb.asm.tree.analysis.SourceValue;
// import org.objectweb.asm.util.TraceMethodVisitor;
import org.objectweb.asm.tree.analysis.Value;
import org.objectweb.asm.util.AbstractVisitor;


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
	private int[] localTypes;
	
	static class DefValue extends BasicValue {

		private AbstractInsnNode source;

		public DefValue(AbstractInsnNode insn, Type type) {
			super(type);
			this.source = insn;
		}
		
	}
	
	static class MyBasicInterpreter extends BasicInterpreter {

		private Map<AbstractInsnNode, Value[]> use = new HashMap<AbstractInsnNode, Value[]>();
				
		@Override
		public Value binaryOperation(AbstractInsnNode insn, Value value1,
				Value value2) throws AnalyzerException {			
			Value v = super.binaryOperation(insn, value1, value2);
			use.put(insn, new Value[]{value1, value2});
			if(v == null) return new DefValue(insn, null);
			return new DefValue(insn, ((BasicValue)v).getType());
		}

		@Override
		public Value copyOperation(AbstractInsnNode insn, Value value)
				throws AnalyzerException {
			use.put(insn, new Value[]{value});
			Value v = super.copyOperation(insn, value);
			if(v == null) return new DefValue(insn, null);
			return new DefValue(insn, ((BasicValue)v).getType());		}

		@Override
		public Value naryOperation(AbstractInsnNode insn, List values)
				throws AnalyzerException {
			use.put(insn, (Value[])values.toArray(new Value[values.size()]));
			Value v = super.naryOperation(insn, values);
			if(v == null) return new DefValue(insn, null);
			return new DefValue(insn, ((BasicValue)v).getType());
		}

		@Override
		public Value newOperation(AbstractInsnNode insn) {
			Value v = super.newOperation(insn);
			return new DefValue(insn, ((BasicValue)v).getType());
		}

		@Override
		public Value ternaryOperation(AbstractInsnNode insn, Value value1,
				Value value2, Value value3) throws AnalyzerException {
			use.put(insn, new Value[]{value1, value2, value3});
			Value v = super.ternaryOperation(insn, value1, value2, value3);
			if(v == null) return new DefValue(insn, null);
			return new DefValue(insn, ((BasicValue)v).getType());
		}

		@Override
		public Value unaryOperation(AbstractInsnNode insn, Value value)
				throws AnalyzerException {
			use.put(insn, new Value[]{value});
			Value v = super.unaryOperation(insn, value);
			if(v == null) return new DefValue(insn, null);
			return new DefValue(insn, ((BasicValue)v).getType());
		}
		
	}
	
	public Transformer(String owner, MethodNode mn, ConstantPack pack, String[] siteNames) {
		super(new MyBasicInterpreter());
		this.owner = owner;
		this.node = mn;
		this.units = node.instructions;
		this.pack = pack;
		this.siteNames = siteNames;
		this.localTypes = new int[mn.maxLocals];
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
		if(eliminateBoxCastUnbox(s)) return Action.REMOVE;
		if(unwrapConst(s)) return Action.REPLACE;
		if(unwrapBoxOrUnbox(s)) return Action.REMOVE;
		if(unwrapBinaryPrimitiveCall(s, frame)) return Action.REPLACE;
		if(unwrapCompare(s,frame)) return Action.REMOVE;
		if(clearCast(s)) return Action.REMOVE;			
		if(correctNormalCall(s)) return Action.NONE;
		if(correctLocalType(s)) return Action.REPLACE;			
		
		return Action.NONE;
	}
	
	boolean flag = false;
	
	@Override
	protected void postProcess(AbstractInsnNode insnNode,Interpreter interpreter) {
		if(flag == true) {
			MyBasicInterpreter i = (MyBasicInterpreter)interpreter;
//			System.out.println(i.def.size());
//			System.out.println(i.use.size());
			Value[] values = i.use.get(insnNode);
			for (int j = 0; j < values.length; j++) {
				System.out.println(j + ".");
				System.out.print(values[j]+", ");
				System.out.println(values[j].getClass());
				System.out.println(((DefValue)values[j]).source);
				System.out.println(AbstractVisitor.OPCODES[((DefValue)values[j]).source.getOpcode()]);
				System.out.println("=================");
			}
//			System.out.println();
//			for (int j = 0; j < values.length; j++) {
//				List<AbstractInsnNode> s = i.def.get(values[j]);
//				System.out.println("size:" + s.size());
//				// System.out.println("(" + AbstractVisitor.OPCODES[s.getOpcode()] + ") " +s+", ");							
//			}
			flag = false;
		}
	}

	private boolean correctNormalCall(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKEINTERFACE) return false;
		MethodInsnNode iv = (MethodInsnNode)s;
		if(iv.owner.equals(CALL_SITE_INTERFACE) == false) return false;
		if(iv.name.startsWith("call") == false) return false;
		System.out.println(iv.name);
		flag = true;
		return true;
	}

	private boolean correctLocalType(AbstractInsnNode s) {
		if(s.getOpcode() != ALOAD) return false;
		VarInsnNode v = (VarInsnNode)s;
		int type = localTypes[v.var];
		if(type==0) return false;		
		switch(type) {
			case 'I':
			case 'B':
			case 'S': 
			case 'Z':
			case 'C':				
				units.set(s, new VarInsnNode(ILOAD, v.var));
				break;
			case 'J':
				units.set(s, new VarInsnNode(LLOAD, v.var));
				break;
			case 'F':
				units.set(s, new VarInsnNode(FLOAD, v.var));
				break;
			case 'D':
				units.set(s, new VarInsnNode(DLOAD, v.var));
				break;
		}			
		return true;
	}

	private boolean eliminateBoxCastUnbox(AbstractInsnNode s) {
//	    INVOKESTATIC org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation.box(I)Ljava/lang/Object;
//	    INVOKESTATIC TreeNode.$get$$class$java$lang$Integer()Ljava/lang/Class;
//	    INVOKESTATIC org/codehaus/groovy/runtime/ScriptBytecodeAdapter.castToType(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
//	    CHECKCAST java/lang/Integer
//	    INVOKESTATIC org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation.intUnbox(Ljava/lang/Object;)I
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
//		if(m.owner.equals(DEFAULT_TYPE_TRANSFORMATION)==false) return false;
		if(m.name.equals("box") == false) return false;
		if(m1.name.startsWith("$get$$class$") == false) return false;
		if(m2.name.startsWith("castToType") == false) return false;
		if(m4.name.endsWith("Unbox")== false) return false;
		units.remove(s);
		units.remove(s1);
		units.remove(s2);
		units.remove(s3);
		units.remove(s4);
		return true;
	}
	
	private int getPrimitive(String className) {
		if(className.equals("java/lang/Integer")) return 'I';
		if(className.equals("java/lang/Long")) return 'J';
		if(className.equals("java/lang/Boolean")) return 'Z';
		if(className.equals("java/lang/Byte")) return 'B';
		if(className.equals("java/lang/Character")) return 'C';
		if(className.equals("java/lang/Short")) return 'S';
		if(className.equals("java/lang/Float")) return 'F';
		if(className.equals("java/lang/Double")) return 'D';
		return 0;
	}

	private boolean clearCast(AbstractInsnNode s) {
//	    INVOKESTATIC TreeNode.$get$$class$java$lang$Integer()Ljava/lang/Class;
//	    INVOKESTATIC org/codehaus/groovy/runtime/ScriptBytecodeAdapter.castToType(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
//	    CHECKCAST java/lang/Integer
		if(s.getOpcode() != INVOKESTATIC) return false;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.name.startsWith("$get$$class$java$lang$")==false) return false;
		AbstractInsnNode s1 = s.getNext(); if(s1 ==null ) return false;
		if(s1.getOpcode() != INVOKESTATIC) return false;
		MethodInsnNode m1 = (MethodInsnNode)s1;
		if(m1.name.equals("castToType")==false) return false;
		AbstractInsnNode s2 = s1.getNext(); if(s2 == null) return false;
		if(s2.getOpcode() != CHECKCAST) return false;
		TypeInsnNode t2 = (TypeInsnNode)s2;
		if(t2.desc.startsWith("java/lang")==false) return false;

		int type = getPrimitive(t2.desc);
		
		AbstractInsnNode s3 = s2.getNext();
		if(s3.getOpcode()==ASTORE) {
			VarInsnNode v3 = (VarInsnNode)s3;
			switch(type) {
				case 'I':
				case 'B':
				case 'S': 
				case 'Z':
				case 'C':				
					units.set(s3, new VarInsnNode(ISTORE, v3.var));
					break;
				case 'J':
					units.set(s3, new VarInsnNode(LSTORE, v3.var));
					break;
				case 'F':
					units.set(s3, new VarInsnNode(FSTORE, v3.var));
					break;
				case 'D':
					units.set(s3, new VarInsnNode(DSTORE, v3.var));
					break;
			}		
			localTypes[v3.var] = type;
			correctLocalVarInfo(type, v3);
			
		}
		units.remove(s);
		units.remove(s1);
		units.remove(s2);
		// TODO: change the next instruction to deal with PRIMITIVE		
		return true;
	}

	private void correctLocalVarInfo(int type, VarInsnNode v3) {
		List<?> vars = node.localVariables;
		if(vars != null) {
			for(int i=0;i<vars.size();i++) {
				LocalVariableNode l = (LocalVariableNode)vars.get(i);
				if(l.index==v3.var) {
					l.desc = String.valueOf((char)type);
					break;
				}
			}
		}
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
		ComparingMethod compare;
		try { compare = ComparingMethod.valueOf(m.name); } 
		catch(IllegalArgumentException e) {
			return false;
		}
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

	private void preTransform() {
		ListIterator<?> stmts = units.iterator();
		while(stmts.hasNext()) {
			AbstractInsnNode s = (AbstractInsnNode)stmts.next();
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
		// TODO check type from "frame"		
		int oldIndex = units.indexOf(s);
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
		s = units.get(oldIndex);
		// SWAP,
		// POP
		units.insert(s, new InsnNode(POP));
		units.insert(s, new InsnNode(SWAP));
		
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
		FieldInsnNode f = (FieldInsnNode)s;
		if(f.name.startsWith("$const$")) {
			LdcInsnNode newS = new LdcInsnNode(pack.get(f.name));
			units.set(s, newS);
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
