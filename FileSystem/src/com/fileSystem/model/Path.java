package com.fileSystem.model;

import java.util.ArrayList;

public class Path {
	private String name;
	private String parent;
	private ArrayList<String> children;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
	/**
	 * @return the children
	 */
	public ArrayList<String> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}
	
	
}