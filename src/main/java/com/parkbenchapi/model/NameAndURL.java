package com.parkbenchapi.model;

public class NameAndURL {
	
	private String name;
	private String url;
	
	public NameAndURL(String name, String url) {
		this.name = name;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getURL() {
		return url;
	}
	
	public void setURL(String url) {
		this.url = url;
	}

}
