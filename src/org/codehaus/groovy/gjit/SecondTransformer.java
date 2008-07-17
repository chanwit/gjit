package org.codehaus.groovy.gjit;

import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Value;
import org.objectweb.asm.util.AbstractVisitor;

public class SecondTransformer extends BaseTransformer {

	private ConstantPack pack;
	private String[] siteNames;
	private int[] localTypes;
	private Integer currentSiteIndex;
	
	private static final String SCRIPT_BYTECODE_ADAPTER = "org/codehaus/groovy/runtime/ScriptBytecodeAdapter";
	private static final String CALL_SITE_INTERFACE = "org/codehaus/groovy/runtime/callsite/CallSite";
	private static final String DEFAULT_TYPE_TRANSFORMATION = "org/codehaus/groovy/runtime/typehandling/DefaultTypeTransformation";	

	public SecondTransformer(String owner, MethodNode mn, ConstantPack pack, String[] siteNames) {
		super(owner, mn);
		this.pack = pack;
		this.siteNames = siteNames;
		this.localTypes = new int[mn.maxLocals];
		this.interpreter = new FixableInterpreter();
	}
	
	class FixableInterpreter extends MyBasicInterpreter {

		@Override
		protected boolean handleNaryOperation(AbstractInsnNode insn, int j,Value expected, Value encountered) {
			DefValue vs;
			System.out.println(AbstractVisitor.OPCODES[insn.getOpcode()]);
			switch(insn.getOpcode()) {
				case INVOKEINTERFACE:
				case INVOKEVIRTUAL:				
					System.out.print(((MethodInsnNode)insn).name);
					System.out.println(((MethodInsnNode)insn).desc);
					vs = (DefValue)use.get(insn)[j];
	//				System.out.println(((DefValue)vs[((NaryOperationException)e).getIndex()]).getType().getDescriptor());
	//				System.out.println(((DefValue)vs[((NaryOperationException)e).getIndex()]).source);
					box(vs.source, vs.getType());
					System.out.println("handle !!");
					return true;
				case INVOKESTATIC:
					System.out.print(((MethodInsnNode)insn).name);
					System.out.println(((MethodInsnNode)insn).desc);
					vs = (DefValue)use.get(insn)[j-1];
					box(vs.source, vs.getType());
					return true;
			}
			return false;			
		}

		@Override
		protected boolean handleCopyOperation(AbstractInsnNode insn, Value value) {
			switch(insn.getOpcode()) {
				case ILOAD:
					System.out.println("copy to ILOAD");
					unbox(insn, Type.INT_TYPE);
					return true;
			}
			return false;
		}

		@Override
		protected boolean handleBinaryOperation(AbstractInsnNode insn, int i,Value value) {
			System.out.println(AbstractVisitor.OPCODES[insn.getOpcode()]);
			DefValue v;
//			switch(insn.getOpcode()) {
//				case ISUB: 
//				case IADD:
//					v = (DefValue)value;
//					unbox(v.source, Type.INT_TYPE);
//					return true;
//				case IF_ICMPGT:
//					v = (DefValue)value;
//					unbox(v.source, Type.INT_TYPE);
//					return true;
//			}
			return false;
		}

		@Override
		protected boolean handleUnaryOperation(AbstractInsnNode insn,Value value) {
			switch(insn.getOpcode()) {
				case IRETURN:
					System.out.println(AbstractVisitor.OPCODES[insn.getOpcode()]);
					DefValue v = (DefValue)value;
					System.out.println(AbstractVisitor.OPCODES[v.source.getOpcode()]);
					unbox(insn, Type.INT_TYPE);
					return true;
			}
			return false;
		}

		@Override
		protected boolean handleTernaryOperation(AbstractInsnNode insn, int i,
				Value value) {
			DefValue vs;
			System.out.println(AbstractVisitor.OPCODES[insn.getOpcode()]);			
			System.out.println(value);			
			switch(insn.getOpcode()) {
				case AASTORE:
					vs = (DefValue)value;
					// System.out.println(AbstractVisitor.OPCODES[vs.source.getOpcode()]);
					box(insn, vs.getType());
					return true;
			}
			return false;
		}
		
	}
	
	@Override
	public Action process(AbstractInsnNode s, Map<AbstractInsnNode, Frame> frames) {
		if(extractCallSiteName(s)) return Action.NONE;
		if(eliminateBoxCastUnbox(s)) return Action.REMOVE;
		if(unwrapConst(s)) return Action.REPLACE;
		if(unwrapBoxOrUnbox(s)) return Action.REMOVE;		
		if(unwrapBinaryPrimitiveCall(s, frames.get(s))) return Action.REPLACE;
		if(unwrapCompare(s,frames.get(s))) return Action.REMOVE;
		if(clearWrapperCast(s)) return Action.REMOVE;	
		if(fixASTORE(s,frames.get(s))) return Action.REPLACE;
		if(fixALOAD(s)) return Action.REPLACE;
		return Action.NONE;
	}
		
	private boolean fixASTORE(AbstractInsnNode s, Frame frame) {
		if(s.getOpcode()!=ASTORE) return false;
		BasicValue top = (BasicValue)frame.getStack(frame.getStackSize()-1);		
		return fixASTORE(s, top.getType());
	}
	
	private boolean fixASTORE(AbstractInsnNode s, Type type) {
		if(type == null) return false;
		VarInsnNode v = (VarInsnNode)s;
		switch(type.getSort()) {
			case Type.INT:		units.set(s, new VarInsnNode(ISTORE, v.var));
								localTypes[v.var] = Type.INT;
								break; 
			case Type.LONG:		units.set(s, new VarInsnNode(LSTORE, v.var));
								localTypes[v.var] = Type.LONG;
								break;								
			case Type.FLOAT:	units.set(s, new VarInsnNode(FSTORE, v.var));
								localTypes[v.var] = Type.FLOAT;
								break;
			case Type.DOUBLE:	units.set(s, new VarInsnNode(DSTORE, v.var));
								localTypes[v.var] = Type.DOUBLE;
								break;
			default:
					return false;
		}		
		return true;
	}	
	
	private boolean fixALOAD(AbstractInsnNode s) {
		if(s.getOpcode()!=ALOAD) return false;
		VarInsnNode v = (VarInsnNode)s;
		switch(localTypes[v.var]) {
			case Type.INT:		units.set(s, new VarInsnNode(ILOAD, v.var));
								System.out.println("fix ALOAD" + v.var);
								return true; 
			case Type.LONG:		units.set(s, new VarInsnNode(LLOAD, v.var));
								return true;
			case Type.FLOAT:	units.set(s, new VarInsnNode(FLOAD, v.var));
								return true;
			case Type.DOUBLE:	units.set(s, new VarInsnNode(DLOAD, v.var));
								return true;
			default:
				return false;
		}		
	}
	
	private void box(AbstractInsnNode source, Type t) {
		String boxType=null;
		String primType=null;
		switch(t.getSort()) {			
		case Type.INT: boxType = "java/lang/Integer";
					   primType = "I";
					   break;
		case Type.LONG: boxType = "java/lang/Long";
					   primType = "J";
					   break;
		}
		MethodInsnNode iv = new MethodInsnNode(INVOKESTATIC, boxType, "valueOf", "("+ primType +")L"+boxType+";");
		if(source.getOpcode()==SWAP) source = source.getPrevious(); // work around for inserted SWAP,POP
		units.insert(source, iv);		
	}
	
	private void unbox(AbstractInsnNode s, Type t) {
		String boxType=null;
		String primType = null;
		String primTypeName = null;
		switch(t.getSort()) {
			case Type.INT:  boxType = "java/lang/Integer";
							primType = "I";
							primTypeName = "int";
							break;
			case Type.LONG:  boxType = "java/lang/Long";
							primType = "J";
							primTypeName = "long";
							break;
			case Type.FLOAT:  boxType = "java/lang/Float";
							primType = "F";
							primTypeName = "float";
							break;
			case Type.DOUBLE:  boxType = "java/lang/Double";
							primType = "D";
							primTypeName = "double";
							break;				
		}
		TypeInsnNode tnode = new TypeInsnNode(CHECKCAST, boxType);
		MethodInsnNode iv = new MethodInsnNode(INVOKEVIRTUAL, boxType, primTypeName + "Value", "()" + primType);
		units.insertBefore(s, tnode);
		units.insert(tnode, iv);	
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
		if(m.owner.equals(DEFAULT_TYPE_TRANSFORMATION)==false) return false;
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
	
	private boolean unwrapBoxOrUnbox(AbstractInsnNode s) {
		if(s.getOpcode() != INVOKESTATIC) return false;
		MethodInsnNode m = (MethodInsnNode)s;
		if(m.owner.equals(DEFAULT_TYPE_TRANSFORMATION)==false) return false;
		if(m.name.equals("box")) {
			// unit_remove(s,s.getPrevious());
			units.remove(s);
			return true;
		} else if(m.name.endsWith("Unbox")) {
			//unit_remove(s,s.getPrevious());
			units.remove(s);
			return true;
		}
		return false;
	}	
	
	private boolean unwrapConst(AbstractInsnNode s) {		
		if(s.getOpcode() != GETSTATIC) return false;		
		FieldInsnNode f = (FieldInsnNode)s;
		if(f.name.startsWith("$const$")) {			
			Object constValue = pack.get(f.name);
			LdcInsnNode newS = new LdcInsnNode(constValue);
			AbstractInsnNode s1 = s.getNext();
			units.set(s, newS);
			if(s1.getOpcode()==ASTORE) {
				Type type = Type.getType(constValue.getClass());				
				fixASTORE(s1, getPrimitiveType(type));
			}
			return true;
		}
		return false;
	}	
	
	private Type getPrimitiveType(Type type) {
		return getPrimitiveType(type.getDescriptor());
	}
	
	private Type getPrimitiveType(String desc) {
		if(desc.equals("Ljava/lang/Integer;")) return Type.INT_TYPE;
		if(desc.equals("Ljava/lang/Long;")) return Type.LONG_TYPE;
		if(desc.equals("Ljava/lang/Float;")) return Type.FLOAT_TYPE;
		if(desc.equals("Ljava/lang/Double;")) return Type.DOUBLE_TYPE;
		return null;
	}	

	private enum BinOp {
		minus,
		plus,
		multiply,
		div,
		leftShift,
		rightShift
	}	
	
	private static final String CALL_SITE_BIN_SIGNATURE = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
		
	private boolean unwrapBinaryPrimitiveCall(AbstractInsnNode s, Frame frame) {
		if(s.getOpcode() != INVOKEINTERFACE) return false;
		MethodInsnNode iv = (MethodInsnNode)s;
		if(iv.owner.equals(CALL_SITE_INTERFACE) == false) return false;
		if(iv.name.equals("call") == false) return false;
		if(iv.desc.equals(CALL_SITE_BIN_SIGNATURE) == false) return false;
		String name = siteNames[currentSiteIndex];
		BinOp op=null;
		try {op = BinOp.valueOf(name);} catch(IllegalArgumentException e){}
		if(op == null) return false;
		int oldIndex = units.indexOf(s);
		// if(s.getPrevious().getOpcode()==LLOAD) System.out.println(">> Found it !!");
		Value v2 = frame.getStack(frame.getStackSize()-1); // peek
		Value v1 = frame.getStack(frame.getStackSize()-2); // peek
		// TODO if(v1.sort != v2.sort) do something
		int offset = 0;
		System.out.println("v1:" +v1);
		System.out.println("v2:" +v2);
		if(((BasicValue)v1).getType().equals(Type.LONG_TYPE)) offset = 1;
		else if(((BasicValue)v1).getType().equals(Type.FLOAT_TYPE)) offset = 2;
		else if(((BasicValue)v1).getType().equals(Type.DOUBLE_TYPE)) offset = 3;
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
		if(v1.getSize()==1) {
		// SWAP,
		// POP
			units.insert(s, new InsnNode(POP));
			units.insert(s, new InsnNode(SWAP));
		} else if(v1.getSize()==2){
			units.insert(s, new InsnNode(POP));
			units.insert(s, new InsnNode(POP2));
			units.insert(s, new InsnNode(DUP2_X1));			
		}
		
		return true;
	}	

	private boolean clearWrapperCast(AbstractInsnNode s) {
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
		AbstractInsnNode s3 = s2.getNext();
		units.remove(s);
		units.remove(s1);
		units.remove(s2);
		if(s3.getOpcode()==ASTORE) {
			fixASTORE(s1, getPrimitiveType(t2.desc));			
		}
		return true;
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
		if(m.owner.equals(SCRIPT_BYTECODE_ADAPTER)==false) return false;
		if(m.name.startsWith("compare")==false) return false;
		if(m.desc.equals("(Ljava/lang/Object;Ljava/lang/Object;)Z")==false) return false;
		JumpInsnNode s1 = (JumpInsnNode)s.getNext();
		ComparingMethod compare;
		try { compare = ComparingMethod.valueOf(m.name); } 
		catch(IllegalArgumentException e) {
			return false;
		}

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
	
	
}


