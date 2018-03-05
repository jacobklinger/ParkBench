package com.parkbenchapi.util;

import org.springframework.stereotype.Component;

@Component
public class GeoUtil {
	
	public double getDistance(double long1, double lat1, double long2, double lat2) {
		double longDiff = long2-long1;
		longDiff = Math.toRadians(longDiff);
		double latDiff = lat2-lat1;
		latDiff = Math.toRadians(latDiff);
		double distance = (Math.sin(latDiff/2) * Math.sin(latDiff/2) + Math.sin(longDiff/2) * Math.sin(longDiff/2)) * Math.cos(lat1) * Math.cos(lat2);
		distance = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1 - distance));
		return distance * 20903520;
	}

}
