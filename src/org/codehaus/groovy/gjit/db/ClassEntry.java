package org.codehaus.groovy.gjit.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ClassEntry implements Serializable {
	
	private static final long serialVersionUID = 217019196144747303L;
	
	private String name;
	private long timeStamp;
	//private List<SiteEntry> siteEntries = new ArrayList<SiteEntry>();
	private Map<Integer, SiteEntry> siteEntries = new HashMap<Integer, SiteEntry>();
	
	public ClassEntry(){}
	
	public ClassEntry(String name, long stamp) {
		this.name = name;
		this.timeStamp = stamp;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void add(SiteEntry s) {
		this.siteEntries.put(s.getCallSiteIndex(), s);
	}

	public ClassEntry add(int callsiteIndex, String returnTypeDesc) {
		this.siteEntries.put(callsiteIndex, new SiteEntry(callsiteIndex, returnTypeDesc));
		return this;
	}

	public String getReturnType(int op1_index) {		
		return this.siteEntries.get(op1_index).getTypeDesc();
	}
	
}