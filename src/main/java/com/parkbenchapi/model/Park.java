package com.parkbenchapi.model;

import java.sql.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Park {

	private String id;
	private String name;
	private double longitude;
	private double latititude;
	private Date dateOpened;
	private List<LinkedResource> links;

	public Park() {
		
	}

	public Park(String id, String name, double longitude, double latititude, Date dateOpened, List<LinkedResource> links) {
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latititude = latititude;
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

	public double getLatititude() {
		return latititude;
	}

	public void setLatititude(double latititude) {
		this.latititude = latititude;
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
