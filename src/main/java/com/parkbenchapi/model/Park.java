package com.parkbenchapi.model;

public class Park extends LocatableResource{

	private NameAndURL resort;
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	@Override
	public void generateLinks(String id) {
		addLink(new Link("self", "/parks/" + id));
		addLink(new Link("dining", "/parks/" + id + "/dining/"));
		addLink(new Link("attractions", "/parks/" + id + "/attractions/"));
		addLink(new Link("entertainment", "/parks/" + id + "/entertainment/"));
		addLink(new Link("location", "/parks/location?longitude=" + getLongitude() + "&latitude=" + getLatitude() + "&radius=0"));
	}
	
}
