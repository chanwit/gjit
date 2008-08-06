package org.codehaus.groovy.gjit.db;

import org.codehaus.groovy.runtime.callsite.CallSite;

import groovy.lang.MetaMethod;

public class SiteTypeHelper {

	public static void record(MetaMethod metaMethod) {
		System.out.println("MetaMethod: " + metaMethod);
	}
	
	public static void record(MetaMethod metaMethod, Class<?> clazz, CallSite site) {
		System.out.println("===============");
		//System.out.println("MetaMethod: " + metaMethod.getDescriptor());
		System.out.println("MetaMethod: " + metaMethod.getName());
		System.out.println("MetaMethod: " + metaMethod.getSignature());
		System.out.println("Class: " + clazz);
		System.out.println("Site Name: " + site.getName());
		System.out.println("Site Index: " + site.getIndex());
		System.out.println("===============");
	}
	
}
