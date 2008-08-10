package org.codehaus.groovy.gjit.db;

import java.io.File;
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
	
	// private static final String CACHE_FILE_NAME = "test.cache";	
	
	public SiteTypePersistentCache(){}
	
//	public static void read() throws Throwable {				
//		FileInputStream f = new FileInputStream(CACHE_FILE_NAME);
//		ObjectInputStream o = new ObjectInputStream(f);
//		instance = (SiteTypePersistentCache) o.readObject();		
//	}
//	
//	public static void write() throws Throwable {
//		FileOutputStream f = new FileOutputStream(CACHE_FILE_NAME);
//		ObjectOutputStream o = new ObjectOutputStream(f);
//		o.writeObject(SiteTypePersistentCache.v());
//	}

	private static SiteTypePersistentCache instance;
	
	public static SiteTypePersistentCache v() {		
		if(instance==null) instance = new SiteTypePersistentCache();
		return instance;
	}

	public Map<String, ClassEntry> getClassEntries() {
		return classEntries;
	}

	public void setClassEntries(Map<String, ClassEntry> classEntries) {
		this.classEntries = classEntries;
	}

	// TODO changed cache root
	private static final String ROOT_DIR = "/temp/";
	// private static final String ROOT_DIR = System.getProperty("user.home") + "/.gjit/";	
	private static final String CACHE_EXT = ".cache";
	
	public ClassEntry find(String name) throws Throwable {
		String filename = ROOT_DIR + name + CACHE_EXT;
		if(new File(filename).exists()) {			
			FileInputStream f = new FileInputStream(filename);
			ObjectInputStream o = new ObjectInputStream(f);
			ClassEntry ce = (ClassEntry) o.readObject();
			this.classEntries.put(name, ce);
			return ce;
		} else {
			return null;
		}
	}
	
	public ClassEntry add(String name, long stamp) {
		int i = name.lastIndexOf('/');
		boolean newlyCreated = false;
		if(i!=-1) {
			String dir = name.substring(0, i);
			// System.out.println(dir);
			File fdir = new File(ROOT_DIR + dir);
			if(fdir.exists() == false) {
				// System.out.println("fdir exists == false");
				fdir.mkdirs();
				newlyCreated = true;
			}
		}
		ClassEntry ce = new ClassEntry(name, stamp);
		String filename = ROOT_DIR + name + CACHE_EXT;
		try {
			if(newlyCreated) {
				FileOutputStream f = new FileOutputStream(filename);
				ObjectOutputStream o = new ObjectOutputStream(f);
				o.writeObject(ce);
			} else {
				// read and compare
				FileInputStream f = new FileInputStream(filename);
				ObjectInputStream o = new ObjectInputStream(f);
				ClassEntry objOnDisk = (ClassEntry) o.readObject();
				if(objOnDisk.getTimeStamp() > ce.getTimeStamp()) {
					throw new RuntimeException("Cache on disk is created from a compiled class file in the future: " + filename);
				} else if(objOnDisk.getTimeStamp() == ce.getTimeStamp()){
					ce = objOnDisk;
					System.out.println("reading from cache, instead of creating the new one");
				}
			}
			this.classEntries.put(name, ce);			
			return ce;
		} catch(Throwable e) {
			return null;
		}
	}	
	
}
