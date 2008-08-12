package org.codehaus.groovy.gjit.test;

import org.codehaus.groovy.gjit.db.SiteTypePersistentCache;

public class Merger {
	
	public static void main(String[] args) throws Throwable {
		SiteTypePersistentCache.v().find("a");
	}

}
