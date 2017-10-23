package com.parkbenchapi.model;

import java.util.ArrayList;

public class Snack {

	private ArrayList<Link> links = new ArrayList<Link>();
	private String name;
	private NameAndURL resort;
	
	public String getName() {
		return name;
	}
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Link> getLinks() {
		return links;
	}
	
	protected void addLink(Link link) {
		links.add(link);
	}
	
	public void generateLinks(String resortId, String snackId) {
		addLink(new Link("self", "/resorts/" + resortId + "/snacks/" + snackId));
	}
	
}
