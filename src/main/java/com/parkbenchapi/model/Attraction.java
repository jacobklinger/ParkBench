package com.parkbenchapi.model;

import java.sql.Date;
import java.util.List;

public class Attraction {
	
	private String id;
	private String name;
	private double longitude;
	private double latitude;
	private Date dateOpened;
	private List<LinkedResource> links;
	
	public Attraction() {
		
	}
	
	public Attraction(String id, String name, double longitude,
			double latitude, Date dateOpened, List<LinkedResource> links) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.dateOpened = dateOpened;
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

	public Date getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	public List<LinkedResource> getLinks() {
		return links;
	}

	public void setLinks(List<LinkedResource> links) {
		this.links = links;
	}

}
