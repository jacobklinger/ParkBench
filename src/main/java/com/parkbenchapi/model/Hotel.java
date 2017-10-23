package com.parkbenchapi.model;

public class Hotel extends LocatableResource{

	private String resortArea;
	private NameAndURL resort;
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}

	public String getResortArea() {
		return resortArea;
	}

	public void setResortArea(String resortArea) {
		this.resortArea = resortArea;
	}

//	@Override
	public void generateLinks(String resortId, String hotelId) {
		addLink(new Link("self", "/resorts/" + resortId + "/hotels/" + hotelId));
		addLink(new Link("dining", "/resorts/" + resortId + "/hotels/" + hotelId + "/dining/"));
		addLink(new Link("location", "/hotels/location?longitude=" + getLongitude() + "&latitude=" + getLatitude() + "&radius=0"));
	}
	
}
