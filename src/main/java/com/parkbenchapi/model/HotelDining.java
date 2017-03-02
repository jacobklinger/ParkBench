package com.parkbenchapi.model;

import java.util.List;

public class HotelDining {
	
	private String id;
	private String name;
	private double longitude;
	private double latitude;
	private List<LinkedResource> links;
	
	public HotelDining() {
		
	}
	
	public HotelDining(String id, String name, double longitude,
			double latitude, List<LinkedResource> links) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.links = links;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public List<LinkedResource> getLinks() {
		return links;
	}

	public void setLinks(List<LinkedResource> links) {
		this.links = links;
	}

}
