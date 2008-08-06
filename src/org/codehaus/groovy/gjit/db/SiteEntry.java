package org.codehaus.groovy.gjit.db;

import java.io.Serializable;

public class SiteEntry implements Serializable {
	
	private static final long serialVersionUID = 4249163116987036739L;		
	
	private int callSiteIndex;
	private String returnTypeDesc;
	
	public SiteEntry(){}
	
	public SiteEntry(int i, String returnTypeDesc) {
		callSiteIndex = i;
		this.returnTypeDesc = returnTypeDesc;
	}

	public int getCallSiteIndex() {
		return callSiteIndex;
	}
	public void setCallSiteIndex(int callSiteIndex) {
		this.callSiteIndex = callSiteIndex;
	}
	public String getTypeDesc() {
		return returnTypeDesc;
	}
	public void setTypeDesc(String returnTypeDesc) {
		this.returnTypeDesc = returnTypeDesc;
	}
	
}