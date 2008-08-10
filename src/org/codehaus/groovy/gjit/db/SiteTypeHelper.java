package org.codehaus.groovy.gjit.db;

import org.codehaus.groovy.runtime.callsite.CallSite;
import org.objectweb.asm.Type;

import groovy.lang.MetaMethod;

public class SiteTypeHelper {
	
	public static void record(MetaMethod metaMethod, Class<?> clazz, CallSite site) {
//		System.out.println("===============");
//		System.out.println("ReturnType: " + metaMethod.getReturnType());
//		System.out.println("Declaring Class: " + metaMethod.getDeclaringClass());
//		System.out.println("Name: " + metaMethod.getName());
//		System.out.println("Signature: " + metaMethod.getSignature());
//		System.out.println("Class: " + clazz);
//		System.out.println("Site Name: " + site.getName());
//		System.out.println("Site Index: " + site.getIndex());
//		System.out.println("===============");
	}
	
	public static Object record(Object returnObject, CallSite site) {
//		System.out.println("Site Owner: " + site.getArray().owner);
//		System.out.println("Site Name:  " + site.getName());
//		System.out.println("Site Index: " + site.getIndex());
//		if(returnObject == null) {
//			System.out.println("return type: null");
//		} else {
//			System.out.println("return type: " + returnObject.getClass());
//		}		
		String name = Type.getInternalName(site.getArray().owner.getClass());
		try {
			ClassEntry c = SiteTypePersistentCache.v().find(name);
			if(c==null) {
				c.add(site.getIndex(), Type.getInternalName(returnObject.getClass()));
			}		
		} catch(Throwable e) {			
		}
		return returnObject;
	}
		
}
