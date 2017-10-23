package com.parkbenchapi.model;

public class ParkDining extends LocatableResource {

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

	public void setPark(String parkName, String resortId, String parkId) {
		this.park = new NameAndURL(parkName, "/resorts/" + resortId + "/parks/" + parkId);
	}
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	public void generateLinks(String resortId, String parkId, String diningId) {
		addLink(new Link("self", "/resorts/" + resortId + "/parks/" + parkId + "/dining/" + diningId));
	}
	
}
