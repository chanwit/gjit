package org.codehaus.groovy.gjit.db;

public class Test {
	
	public static void main(String[] args) throws Throwable {
		SiteTypePersistentCache.v().add("Test",10L)
		.add(0, "V")
		.add(1, "I")
		.add(2, "B")
		.add(3, "J");		
//		PersistentCache.write();
		
		SiteTypePersistentCache.read();
		ClassEntry c = SiteTypePersistentCache.v().find("Test");
		System.out.println(c.getTimeStamp());
	}

}
