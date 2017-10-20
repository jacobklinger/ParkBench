package com.parkbenchapi.model;

import java.util.ArrayList;

public class Attraction extends LocatableResource {

	private String land;
	private NameAndURL park;
	private NameAndURL resort;
	
	public String getLand() {
		return land;
	}

	public void setLand(String park) {
		this.land = park;
	}
	
	public NameAndURL getPark() {
		return park;
	}

	public void setPark(String parkName, String parkId) {
		this.park = new NameAndURL(parkName, "/parks/" + parkId);
	}
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	public void generateLinks(String id) {
		addLink(new Link("self", "/attractions/" + id));
	}
	
}
