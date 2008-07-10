package org.codehaus.groovy.gjit;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;
import org.objectweb.asm.tree.MethodNode;

public class Optimiser extends ClassAdapter {
	
	private ConstantPack pack;
	private String[] siteNames;
	private Map<String, MethodNode> methods;
	private String owner;
	
	public Optimiser(ClassVisitor cv) {
		super(cv);
	}
	
	public Optimiser(ClassVisitor cv, Map<String, MethodNode> methods) {
		super(cv);
		this.methods = methods;
	}

	enum State {
		START,
		FOUND_COMPARE, 
		FOUND_CALLSITE_VAR, 
		LOAD_CALLSITE_VAR
	};	
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodNode mn = methods.get(name+desc);
		try {
			if(name.equals("bottomUpTree"))
				new Transformer(owner, mn, pack, siteNames).transform();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		mn.accept(cv);
		return mn;
	}	

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.owner = name;
		pack = ConstantRecord.v().get(this.owner);
		siteNames = CallSiteArrayPack.v().get(this.owner);
		super.visit(version, access, name, signature, superName, interfaces);
	}

	public static void main(String[] args) throws Throwable {
		RandomAccessFile r = new RandomAccessFile(new File("C:\\groovy-ck1\\groovy\\my\\TreeNode.class"), "r");		
		byte[] bytes = new byte[(int) r.length()];
		r.readFully(bytes);
		ClassReader cr = new ClassReader(bytes);
		PreProcess cv = new PreProcess(new EmptyVisitor());
		cr.accept(cv, 0);				
		
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);		
		Optimiser cv2 = new Optimiser(cw, cv.getMethods());
		cr.accept(cv2, 0);
		byte[] outBytes = cw.toByteArray();
		RandomAccessFile ro = new RandomAccessFile(new File("C:\\groovy-ck1\\groovy\\my\\out2\\TreeNode.class"), "rw");
		ro.write(outBytes);
		ro.close();
	}
	
}
