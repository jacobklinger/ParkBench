package com.parkbenchapi.model;

public class HotelDining extends LocatableResource {

	private NameAndURL hotel;
	private NameAndURL resort;
	
	public NameAndURL getHotel() {
		return hotel;
	}

	public void setHotel(String hotelName, String resortId, String hotelId) {
		this.hotel = new NameAndURL(hotelName, "/resorts/" + resortId + "/parks/" + hotelId);
	}
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	public void generateLinks(String resortId, String hotelId, String diningId) {
		addLink(new Link("self", "/resorts/" + resortId + "/hotels/" + hotelId + "/dining/" + diningId));
	}
	
}
