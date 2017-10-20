package com.parkbenchapi.model;

public class Dining extends LocatableResource {

	private String park;
	private String land;
	private String hotel;

	public String getPark() {
		return park;
	}

	public void setPark(String park) {
		this.park = park;
	}
	
	public String getLand() {
		return land;
	}

	public void setLand(String park) {
		this.land = park;
	}
	
	public String getHotel() {
		return hotel;
	}
	
	public void setHotel(String hotel) {
		this.land = hotel;
	}

	@Override
	public void generateLinks(String id) {
		// TODO Auto-generated method stub
		
	}
	
}
