package com.parkbenchapi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.parkbenchapi.model.LinkedResource;
import com.parkbenchapi.model.Park;
import com.parkbenchapi.model.Resort;

public class ParkService extends ConnectionService{
	
	private String getAll = "SELECT * FROM Parks WHERE resortID = ? ORDER BY date_opened";
	private String getPark = "SELECT * FROM Parks WHERE resortID = ? AND parkID = ?";
	//private String getAttractions = "SELECT attractionID, attraction_name FROM Attractions WHERE parkID = ?";
	
	public ParkService() {
		
	}
	
	public List<Park> getParks(String resortID) {
		List<Park> parks = new ArrayList<Park>();
		try {
			PreparedStatement query = conn.prepareStatement(getAll);
			query.setString(1, resortID);
			ResultSet queryResults = query.executeQuery();
			while(queryResults.next()) {
				Park current = entityToObject(queryResults);
				parks.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return parks;
	}
	
	public Park getPark(String resortID, String parkID) {
		Park result = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getPark);
			query.setString(1, resortID);
			query.setString(2, parkID);
			ResultSet queryResults = query.executeQuery();
			if (queryResults.next()) {
				result = entityToObject(queryResults);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Park entityToObject(ResultSet queryResults) throws SQLException {
		List<LinkedResource> links = getLinks(queryResults.getString("resortID"), queryResults.getString("parkID"));
		Park result = new Park(queryResults.getString("parkID"), queryResults.getString("park_name"), queryResults.getDouble("longitude"), queryResults.getDouble("latitude"), queryResults.getDate("date_opened"), links);
		return result;
	}
	
	private List<LinkedResource> getLinks(String resortID, String parkID) {
		List<LinkedResource> links = new ArrayList<LinkedResource>();
		links.add(getAttractionsLink(resortID, parkID));
		links.add(getDiningLink(resortID, parkID));
		links.add(getResortLink(resortID));
		links.add(getSelfLink(resortID));
		return links;
	}
	
	private LinkedResource getAttractionsLink(String resortID, String parkID) {
		return new LinkedResource("Attractions", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/" + parkID + "/attractions/");
	}
	
	private LinkedResource getDiningLink(String resortID, String parkID) {
		return new LinkedResource("Dining", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/" + parkID + "/dining/");
	}
	
	private LinkedResource getResortLink(String resortID) {
		return new LinkedResource("Resort", "http://parkbenchapi.com/api/resorts/" + resortID);
	}
	
	private LinkedResource getSelfLink(String resortID) {
		return new LinkedResource("self", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/");
	}
	
	/*private List<LinkedResource> getAttractionLinks(String resortID, String parkID) {
		List<LinkedResource> results = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getAttractions);
			query.setString(1, parkID);
			ResultSet queryResults = query.executeQuery();
			results = new ArrayList<LinkedResource>();
			while(queryResults.next()) {
				LinkedResource current = new LinkedResource(queryResults.getString("attraction_name"), "http://parkbenchapi.com/api/resorts/" + resortID + "/" + parkID + "/" + queryResults.getString("attractionID"));
				results.add(current);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return results;
	}*/

}
