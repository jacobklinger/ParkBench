package com.parkbenchapi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.parkbenchapi.model.Attraction;
import com.parkbenchapi.model.LinkedResource;
import com.parkbenchapi.model.Park;
import com.parkbenchapi.model.Resort;

public class AttractionService extends ConnectionService{
	
	// SQL Queries
	private String getAll = "SELECT * FROM Attractions WHERE resortID = ? AND parkID = ? ORDER BY date_opened, attraction_name";
	private String getPark = "SELECT * FROM Attractions WHERE resortID = ? AND parkID = ? AND attractionID = ?";
	
	public AttractionService() {
		
	}
	
	/**
	 * Returns a list of attractions for a specific park
	 * @param resortID
	 * @param parkID
	 * @return
	 */
	public List<Attraction> getAttractions(String resortID, String parkID) {
		List<Attraction> attractions = new ArrayList<Attraction>();
		try {
			PreparedStatement query = conn.prepareStatement(getAll);
			query.setString(1, resortID);
			query.setString(2, parkID);
			ResultSet queryResults = query.executeQuery();
			while(queryResults.next()) {
				Attraction current = entityToObject(queryResults);
				attractions.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attractions;
	}
	
	/**
	 * Returns a specific attraction for a specific park
	 * @param resortID
	 * @param parkID
	 * @param attractionID
	 * @return
	 */
	public Attraction getAttraction(String resortID, String parkID, String attractionID) {
		Attraction result = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getPark);
			query.setString(1, resortID);
			query.setString(2, parkID);
			query.setString(3, attractionID);
			ResultSet queryResults = query.executeQuery();
			if (queryResults.next()) {
				result = entityToObject(queryResults);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Converts the result of a query to an object
	 * @param queryResults
	 * @return
	 * @throws SQLException
	 */
	private Attraction entityToObject(ResultSet queryResults) throws SQLException {
		List<LinkedResource> links = getLinks(queryResults.getString("resortID"), queryResults.getString("parkID"), queryResults.getString("attractionID"));
		Attraction result = new Attraction(queryResults.getString("attractionID"), queryResults.getString("attraction_name"), queryResults.getDouble("longitude"), queryResults.getDouble("latitude"), queryResults.getDate("date_opened"), links);
		return result;
	}
	
	/**
	 * Builds a list of links to park, resort, and self
	 * @param resortID
	 * @param parkID
	 * @param attractionID
	 * @return
	 */
	private List<LinkedResource> getLinks(String resortID, String parkID, String attractionID) {
		List<LinkedResource> links = new ArrayList<LinkedResource>();
		links.add(getParkLink(resortID, parkID));
		links.add(getResortLink(resortID));
		links.add(getSelfLink(resortID, parkID, attractionID));
		return links;
	}
	
	/**
	 * Returns park link
	 * @param resortID
	 * @param parkID
	 * @return
	 */
	private LinkedResource getParkLink(String resortID, String parkID) {
		return new LinkedResource("Park", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/" + parkID);
	}
	
	/**
	 * Returns resort link
	 * @param resortID
	 * @return
	 */
	private LinkedResource getResortLink(String resortID) {
		return new LinkedResource("Resort", "http://parkbenchapi.com/api/resorts/" + resortID);
	}
	
	/**
	 * Returns self link
	 * @param resortID
	 * @param parkID
	 * @param attractionID
	 * @return
	 */
	private LinkedResource getSelfLink(String resortID, String parkID, String attractionID) {
		return new LinkedResource("self", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/" + parkID + "/attractions/" + attractionID);
	}

}
