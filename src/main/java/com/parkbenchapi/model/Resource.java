package com.parkbenchapi.model;

import java.util.ArrayList;

public abstract class Resource {
	
	private String name;
	private String shortName;
	private ArrayList<Link> links = new ArrayList<Link>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public ArrayList<Link> getLinks() {
		return links;
	}
	
	protected void addLink(Link link) {
		links.add(link);
	}
	
	public abstract void generateLinks(String id);

}
