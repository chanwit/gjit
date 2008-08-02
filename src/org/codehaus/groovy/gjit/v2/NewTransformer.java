package org.codehaus.groovy.gjit.v2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.codehaus.groovy.gjit.ConstantPack;
import org.codehaus.groovy.gjit.DefValue;
import org.codehaus.groovy.gjit.MyBasicInterpreter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Value;
import org.objectweb.asm.util.AbstractVisitor;

public class NewTransformer extends BaseTransformer {

	private String[] siteNames;
	private ConstantPack pack;
	private Integer currentSiteIndex;
	private HashMap<AbstractInsnNode, AbstractInsnNode> removedNodes = new HashMap<AbstractInsnNode, AbstractInsnNode>();
	private int[] localTypes;

	public NewTransformer(String owner, MethodNode mn, ConstantPack pack, String[] siteNames) {
		super(new MyBasicInterpreter(),owner, mn);
		this.pack = pack;
		this.siteNames = siteNames;
		localTypes = new int[mn.maxLocals];
	}

	@Override
	public void transform() throws AnalyzerException {
		// 1. gathering data, creating used nodes
		// done by super.transform();
		super.transform();
		// assume all wrappers to be primitives
		// convertLocalVars();
		// 2. process
		process();
	}

//	private void convertLocalVars() {
//		if(node.localVariables != null) {
//			List localVars = node.localVariables; 			
//			for (int i=0;i<localVars.size();i++) {
//				LocalVariableNode l = (LocalVariableNode)localVars.get(i);
//				if(l.signature!=null) {
//					int t = getPrimitive(l.signature);
//					if(t != 0) {
//						l.signature = String.valueOf((char)t);
//					}
//				}
//			}
//		}
//		
//	}

	private void process() {
		int index = 0;
		while(true) {
			AbstractInsnNode s;
			try {s = units.get(index++);} catch(IndexOutOfBoundsException e) {break;}
			if(s==null) break;
			if(s.getOpcode()!=-1) System.out.println(AbstractVisitor.OPCODES[s.getOpcode()]);
			if(extractCallSiteName(s)) continue;
			int r=0;
			if((r=eliminateBoxCastUnbox(s))!=0) {
				index += r;
				continue;
			}
			if((r=unwrapConst(s))!=0) {
				index += r;
				continue;			
			}
			if((r=unwrapBoxOrUnbox(s))!=0) {
				index += r;
				continue;
			}
			if(unwrapBinaryPrimitiveCall(s)) continue;
			if(unwrapCompare(s)) {
				index--;
				continue;
			}
			if(clearCast(s)) {
				index--;
				continue;		
			}
			if((r=correctNormalCall(s))!=0) {
				index += r;
				continue;
			}
			if((r=correctLocalType(s))!=0) {
				index += r;
				continue;			
			}
			if(correctUnbox(s)) continue;
		}
	}
	
	private int correctLocalType(AbstractInsnNode s) {
		if(s.getOpcode() != ALOAD) return 0;
		VarInsnNode v = (VarInsnNode)s;
		int type = localTypes[v.var];
		if(type==0) return 0;		
		switch(type) {
			case 'I':
			case 'B':
			case 'S': 
			case 'Z':
			case 'C':				
				unit_set(s, new VarInsnNode(ILOAD, v.var));
				break;
			case 'J':
				unit_set(s, new VarInsnNode(LLOAD, v.var));
				break;
			case 'F':
				unit_set(s, new VarInsnNode(FLOAD, v.var));
				break;
			case 'D':
				unit_set(s, new VarInsnNode(DLOAD, v.var));
				break;
		}			
		return -1;
	}

	private boolean correctUnbox(AbstractInsnNode s) {
		Value v;
		switch(s.getOpcode()) {
			case ISTORE:
			case IRETURN:
				v = use.get(s)[0];
				System.out.println(v);
				break;
			case LRETURN:
			case LSTORE: 
				System.out.println("correctUnbox");
				v = use.get(s)[0];
				System.out.println(AbstractVisitor.OPCODES[((DefValue)v).source.getOpcode()]);
				break;
			case FRETURN:
			case FSTORE: 
				v = use.get(s)[0];
				System.out.println(v);
				break;
			case DRETURN:
			case DSTORE: 
				v = use.get(s)[0];
				System.out.println(v);
				break;
		}
		return false;
	}	
	
	private int correctNormalCall(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKEINTERFACE && 
		   s.getOpcode() != INVOKESTATIC) return 0;
		MethodInsnNode iv = (MethodInsnNode)s;
		boolean edit=false;
		if(iv.owner.equals(CALL_SITE_INTERFACE) && iv.name.startsWith("call")) {
			edit = true;
		} else if (iv.owner.equals(SCRIPT_BYTECODE_ADAPTER) 
				&& !iv.name.equals("unwrap")
				&& !iv.name.equals("castToType")) {
			edit = true;
		}		
		if(edit) {
			System.out.println("--------------------");
			System.out.println(iv.name+iv.desc);
			Value[] values = use.get(iv);
			System.out.println(frames.get(iv));
			Value[] args = getFromFrame(values.length, frames.get(iv));
			int start=0;
			int count = 0;
			if(iv.getOpcode() == INVOKESTATIC) { start = 0; }
			else if(iv.getOpcode() == INVOKEINTERFACE) {start = 1;}
			for (int j = start; j < args.length; j++) {
				System.out.print(j + ".");
				BasicValue bv = ((BasicValue)args[j]);
				if(bv.isReference()==false) continue;
				String className = bv.getType().getInternalName();
				System.out.println(className);
				int primType = getPrimitive(className);
				if(primType != 0) { 
					AbstractInsnNode source = ((DefValue)bv).source;
					MethodInsnNode valueOf = new MethodInsnNode(INVOKESTATIC, className, "valueOf", "("+ ((char)primType) +")L"+className+";");
					if(source.getOpcode()==SWAP) source = source.getPrevious(); // work around for inserted SWAP,POP
					units.insert(source, valueOf);
					count++;
				}
			}
			return count;
//			if(iv.getOpcode() == INVOKESTATIC) {
//				for (int j = 0; j < values.length; j++) {
//					BasicValue bv = ((BasicValue)values[j]);
//					String className = bv.getType().getClassName();
//					int primType = getPrimitive(className);
//					if(primType != 0) { 
//						AbstractInsnNode source = ((DefValue)bv).source;
//						MethodInsnNode valueOf = new MethodInsnNode(INVOKESTATIC, className, "valueOf", "("+ ((char)primType) +")L"+className+";");
//						if(source.getOpcode()==SWAP) source = source.getPrevious(); // work around for inserted SWAP,POP
//						units.insert(source, valueOf);						
//					}
//				}				
//			} else if(iv.getOpcode() == INVOKEINTERFACE){
//				for (int j = 1; j < values.length; j++) {
//					BasicValue bv = ((BasicValue)values[j]);					
//					String className = bv.getType().getClassName();
//					int primType = getPrimitive(className);
//					if(primType != 0) { 
//						AbstractInsnNode source = ((DefValue)bv).source;
//						MethodInsnNode valueOf = new MethodInsnNode(INVOKESTATIC, className, "valueOf", "("+ ((char)primType) +")L"+className+";");
//						if(source.getOpcode()==SWAP) source = source.getPrevious(); // work around for inserted SWAP,POP
//						units.insert(source, valueOf);						
//					}
//				}								
//			}			
		}
		return 0;
	}	
	
	private Value[] getFromFrame(int size, Frame frame) {
		Value[] result = new Value[size];
		int i = 0;
		while(size > 0) {
			size--; i++; 
			result[size] = frame.getStack(frame.getStackSize()-i); // peek
		}
		return result;
	}

	private enum ComparingMethod { 
		compareLessThan, 
		compareGreaterThan, 
		compareLessThanEqual, 
		compareGreaterThanEqual
	};		
	
	private boolean unwrapCompare(AbstractInsnNode s) {
		if(s.getOpcode() != Opcodes.INVOKESTATIC) return false;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.owner.equals(SCRIPT_BYTECODE_ADAPTER)==false) return false;
		if(m.name.startsWith("compare")==false) return false;
		if(m.desc.equals("(Ljava/lang/Object;Ljava/lang/Object;)Z")==false) return false;
		JumpInsnNode s1 = (JumpInsnNode)s.getNext();
		ComparingMethod compare;
		try { compare = ComparingMethod.valueOf(m.name); } 
		catch(IllegalArgumentException e) {
			return false;
		}
		Frame frame = frames.get(s);
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
		fixASTORE(type, s3);
		units.remove(s);
		units.remove(s1);
		units.remove(s2);
//		System.out.println("used by s" + use.get(s));
//		System.out.println("used by s" + use.get(s).length);
//		// TODO: change the next instruction to deal with PRIMITIVE		
		return true;
	}	
	
	private int unwrapBoxOrUnbox(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKESTATIC) return 0;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.owner.equals(DEFAULT_TYPE_TRANSFORMATION)==false) return 0;
		if(m.name.equals("box")) {
			//units.remove(s);
			unit_remove(s,s.getPrevious());
			return -1;
		} else if(m.name.endsWith("Unbox")) {
			unit_remove(s,s.getPrevious());
			return -1;
		}
		return 0;
	}	
	
	private int unwrapConst(AbstractInsnNode s) {		
		if(s.getOpcode() != GETSTATIC) return 0;		
		FieldInsnNode f = (FieldInsnNode)s;
		if(f.name.startsWith("$const$")) {			
			LdcInsnNode newS = new LdcInsnNode(pack.get(f.name));
			AbstractInsnNode s1 = s.getNext();			
			fixASTORE(getPrimitive(f.desc), s1);
			unit_set(s, newS);
			return -1;
		}
		return 0;
	} 
	
	private void unit_set(AbstractInsnNode s, AbstractInsnNode newS) {
		removedNodes.put(s, newS);
		use.put(newS, use.get(s));
		use.remove(s);
		
		units.set(s, newS);		
	}
	
	private void unit_remove(AbstractInsnNode s, AbstractInsnNode ref) {
		removedNodes.put(s, ref);
		units.remove(s);
		use.remove(s);
	}	

	private void fixASTORE(int type, AbstractInsnNode s) {
		if(s.getOpcode()==ASTORE) {
			VarInsnNode v3 = (VarInsnNode)s;
			VarInsnNode newNode=null;
			switch(type) {
				case 'I':
				case 'B':
				case 'S': 
				case 'Z':
				case 'C':				
					newNode = new VarInsnNode(ISTORE, v3.var);
					unit_set(s, newNode);
					break;
				case 'J':
					newNode = new VarInsnNode(LSTORE, v3.var);
					unit_set(s, newNode);
					System.out.println("fixed ASTORE to LSTORE: " + v3.var);
					break;
				case 'F':		
					newNode = new VarInsnNode(FSTORE, v3.var);					
					unit_set(s, newNode);
					break;
				case 'D':
					newNode = new VarInsnNode(DSTORE, v3.var);
					unit_set(s, newNode);
					break;
			}
			localTypes[v3.var] = type;
			correctLocalVarInfo(type, v3);			
		}
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

	private int getPrimitive(String className) {
		if(className.charAt(0)=='L' && className.charAt(className.length()-1)==';') {
			className = className.substring(1,className.length()-1);
		}
		// System.out.println("getPrimitive: " + className);
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
	
	private int eliminateBoxCastUnbox(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKESTATIC) return 0;
		AbstractInsnNode s1 = s.getNext();    if(s1 == null) return 0;
		if(s1.getOpcode() != INVOKESTATIC) return 0;
		AbstractInsnNode s2 = s1.getNext();   if(s2 == null) return 0;
		if(s2.getOpcode() != INVOKESTATIC) return 0;		
		AbstractInsnNode s3 = s2.getNext();   if(s3 == null) return 0;
		if(s3.getOpcode() != CHECKCAST) return 0;				
		AbstractInsnNode s4 = s3.getNext();	  if(s4 == null) return 0;
		if(s4.getOpcode() != INVOKESTATIC) return 0;						
		MethodInsnNode m = (MethodInsnNode)s;
		MethodInsnNode m1 = (MethodInsnNode)s1;
		MethodInsnNode m2 = (MethodInsnNode)s2;
		MethodInsnNode m4 = (MethodInsnNode)s4;		
		if(m.name.equals("box") == false) return 0;
		if(m1.name.startsWith("$get$$class$") == false) return 0;
		if(m2.name.startsWith("castToType") == false) return 0;
		if(m4.name.endsWith("Unbox")== false) return 0;
		//adjustUsebox(s.getPrevious(),s,s1,s2,s3,s4);
		AbstractInsnNode prev = s.getPrevious();
		unit_remove(s, prev);
		unit_remove(s1, prev);
		unit_remove(s2, prev);
		unit_remove(s3, prev);
		unit_remove(s4, prev);
		return -1;
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
		Type arg1Type = ((BasicValue)v1).getType();
		if(arg1Type.equals(Type.getType(Long.class))) offset = 1;
		else if(arg1Type.equals(Type.getType(Float.class))) offset = 2;
		else if(arg1Type.equals(Type.getType(Double.class))) offset = 3;
		switch(op) {
			case minus:
				unit_set(s, new InsnNode(ISUB + offset)); break;
			case plus:
				unit_set(s, new InsnNode(IADD + offset)); break;
			case multiply:
				unit_set(s, new InsnNode(IMUL + offset)); break;
			case div:
				unit_set(s, new InsnNode(IDIV + offset)); break;
			case leftShift:
				unit_set(s, new InsnNode(ISHL + offset)); break;
			case rightShift:
				unit_set(s, new InsnNode(ISHR + offset)); break;
		}
		s = units.get(oldIndex);
		if(offset==0 || offset == 2) { // category 1
			units.insert(s, new InsnNode(POP));     // SWAP
			units.insert(s, new InsnNode(SWAP));    // POP
		} else if(offset == 1 || offset == 3){ // category 2	
			units.insert(s, new InsnNode(POP));     // DUP2_X1
			units.insert(s, new InsnNode(POP2));    // POP2
			units.insert(s, new InsnNode(DUP2_X1)); // POP1			
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
