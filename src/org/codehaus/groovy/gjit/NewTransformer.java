package org.codehaus.groovy.gjit;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Interpreter;
import org.objectweb.asm.tree.analysis.Value;

public class NewTransformer extends Analyzer implements Opcodes {

	private int callSiteVar = -1;
	private ConstantPack pack;
	private String[] siteNames;
	private Integer currentSiteIndex = -1;
	private String owner;
	
	private MethodNode node;
	private InsnList units;
	private Map<AbstractInsnNode, Value[]> use;		
	
	public NewTransformer(String owner, MethodNode mn, ConstantPack pack, String[] siteNames) {
		super(new MyBasicInterpreter());		
		this.owner = owner;
		this.node = mn;
		this.units = node.instructions;
		this.pack = pack;
		this.siteNames = siteNames;
	}
	
	public void transform() throws AnalyzerException {
		preTransform();
		this.analyze(this.owner, this.node);
		this.use = ((MyBasicInterpreter)this.interpreter).use;
	}	
	
	
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
	
	private void preTransform() {
		ListIterator<?> stmts = units.iterator();
		while(stmts.hasNext()) {
			AbstractInsnNode s = (AbstractInsnNode)stmts.next();
			switch(phase) {
				case PHASE_CALLSITE: phase = processPhaseCallSite(s); break;
			}	
		}
		//node.localVariables.add(null);
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

}
