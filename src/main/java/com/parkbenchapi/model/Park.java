package com.parkbenchapi.model;

public class Park extends LocatableResource{

	private NameAndURL resort;
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	public void generateLinks(String resortId, String parkId) {
		addLink(new Link("self", "/resorts/" + resortId + "/parks/" + parkId));
		addLink(new Link("dining", "/resorts/" + resortId + "/parks/" + parkId + "/dining"));
		addLink(new Link("attractions", "/resorts/" + resortId + "/parks/" + parkId + "/attractions"));
		addLink(new Link("entertainment", "/resorts/" + resortId + "/parks/" + parkId + "/entertainment"));
		addLink(new Link("location", "/parks/location?longitude=" + getLongitude() + "&latitude=" + getLatitude() + "&radius=0"));
	}
	
}
