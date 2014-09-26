/*
 * Created on 2005-7-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.hbgz.pub.xml;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * 存放2个XML文件比较的结果
 */
public class XmlCompResult {
	private String path="";
	private String oldValue="";
	private String newValue="";
	private int oldNodes=0;
	private int newNodes=0;
	
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path=path;
	}
	public String getOldValue(){
		return this.oldValue;
	}
	public void setOldValue(String oldValue){
		this.oldValue=oldValue;
	}
	public String getNewValue(){
		return this.newValue;
	}
	public void setNewValue(String newValue){
		this.newValue=newValue;
	}
	public int getOldNodes(){
		return this.oldNodes;
	}
	public void setOldNodes(int oldNodes){
		this.oldNodes=oldNodes;
	}
	
	public int getNewNodes(){
		return this.newNodes;
	}
	public void setNewNodes(int newNodes){
		this.newNodes=newNodes;
	}
}

