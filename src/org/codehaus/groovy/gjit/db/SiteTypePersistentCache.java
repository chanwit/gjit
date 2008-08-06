package org.codehaus.groovy.gjit.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SiteTypePersistentCache implements Serializable {
	
	private static final long serialVersionUID = 7031435017464448743L;
	
	private Map<String, ClassEntry> classEntries = new HashMap<String, ClassEntry>();
	
	private static final String CACHE_FILE_NAME = "test.cache";	
	
	public SiteTypePersistentCache(){}
	
	public static void read() throws Throwable {		
		FileInputStream f = new FileInputStream(CACHE_FILE_NAME);
		ObjectInputStream o = new ObjectInputStream(f);
		instance = (SiteTypePersistentCache) o.readObject();
	}
	
	public static void write() throws Throwable {
		FileOutputStream f = new FileOutputStream(CACHE_FILE_NAME);
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(SiteTypePersistentCache.v());
	}

	private static SiteTypePersistentCache instance;
	
	public static SiteTypePersistentCache v() {		
		if(instance==null) instance = new SiteTypePersistentCache();
		return instance;
	}

	public ClassEntry add(String name, long stamp) {
		ClassEntry c = new ClassEntry(name, stamp);
		classEntries.put(name, c);
		return c;
	}

	public Map<String, ClassEntry> getClassEntries() {
		return classEntries;
	}

	public void setClassEntries(Map<String, ClassEntry> classEntries) {
		this.classEntries = classEntries;
	}

	public ClassEntry find(String name) {
		return this.classEntries.get(name);
	}
	
}
