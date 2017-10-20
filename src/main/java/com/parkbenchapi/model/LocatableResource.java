package com.parkbenchapi.model;

public abstract class LocatableResource extends Resource {
	
	private double longitude;
	private double latitude;
	
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
	
	public double getDistance(double longitude, double latitude) {
		
		double longDiff = Math.abs(this.longitude - longitude);
		double latDiff = Math.abs(this.latitude - latitude);
		double distance = Math.sqrt(longDiff * longDiff + latDiff * latDiff);
		
		return distance;
	}
	
}
