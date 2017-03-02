package com.parkbenchapi.model;

import java.sql.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Resort {

	public String id;
	public String name;
	public String city;
	public String state;
	public String country;
	public double longitude;
	public double latititude;
	public Date dateOpened;
	public List<LinkedResource> links;

	public Resort() {
		
	}

	public Resort(String id, String name, String city, String state, String country, double longitude, double latititude, Date dateOpened, List<LinkedResource> links) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
		this.dateOpened = dateOpened;
		this.longitude = longitude;
		this.latititude = latititude;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
