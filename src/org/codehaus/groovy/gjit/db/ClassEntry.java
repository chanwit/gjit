package org.codehaus.groovy.gjit.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassEntry implements Serializable {
	
	private static final long serialVersionUID = 217019196144747303L;
	
	private String name;
	private long timeStamp;
	private List<SiteEntry> siteEntries = new ArrayList<SiteEntry>();
	
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
	public List<SiteEntry> getSiteEntries() {
		return siteEntries;
	}
	public void setSiteEntries(List<SiteEntry> siteEntries) {
		this.siteEntries = siteEntries;
	}

	public void add(SiteEntry s) {
		this.siteEntries.add(s);
	}

	public ClassEntry add(int callsiteIndex, String returnTypeDesc) {
		this.siteEntries.add(new SiteEntry(callsiteIndex, returnTypeDesc));
		return this;
	}
	
}