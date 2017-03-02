package com.parkbenchapi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LinkedResource {
	
	private String rel;
	private String link;
	
	public LinkedResource() {
		
	}

	public LinkedResource(String rel, String link) {
		this.rel = rel;
		this.link = link;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
