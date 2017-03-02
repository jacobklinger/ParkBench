package com.parkbenchapi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.parkbenchapi.model.LinkedResource;
import com.parkbenchapi.model.ParkDining;

public class ParkDiningService extends ConnectionService{
	
	private String getAll = "SELECT * FROM ParkDining WHERE resortID = ? AND parkID = ? ORDER BY dining_name";
	private String getPark = "SELECT * FROM ParkDining WHERE resortID = ? AND parkID = ? AND diningID = ?";
	
	public ParkDiningService() {
		
	}
	
	public List<ParkDining> getAllDining(String resortID, String parkID) {
		List<ParkDining> dining = new ArrayList<ParkDining>();
		try {
			PreparedStatement query = conn.prepareStatement(getAll);
			query.setString(1, resortID);
			query.setString(2, parkID);
			ResultSet queryResults = query.executeQuery();
			while(queryResults.next()) {
				ParkDining current = entityToObject(queryResults);
				dining.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dining;
	}
	
	public ParkDining getDining(String resortID, String parkID, String diningID) {
		ParkDining result = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getPark);
			query.setString(1, resortID);
			query.setString(2, parkID);
			query.setString(3, diningID);
			ResultSet queryResults = query.executeQuery();
			if (queryResults.next()) {
				result = entityToObject(queryResults);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ParkDining entityToObject(ResultSet queryResults) throws SQLException {
		List<LinkedResource> links = getLinks(queryResults.getString("resortID"), queryResults.getString("parkID"), queryResults.getString("diningID"));
		ParkDining result = new ParkDining(queryResults.getString("diningID"), queryResults.getString("dining_name"), queryResults.getDouble("longitude"), queryResults.getDouble("latitude"), links);
		return result;
	}
	
	private List<LinkedResource> getLinks(String resortID, String parkID, String diningID) {
		List<LinkedResource> links = new ArrayList<LinkedResource>();
		links.add(getParkLink(resortID, parkID));
		links.add(getResortLink(resortID));
		links.add(getSelfLink(resortID, parkID, diningID));
		return links;
	}
	
	private LinkedResource getParkLink(String resortID, String parkID) {
		return new LinkedResource("Park", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/" + parkID);
	}
	
	private LinkedResource getResortLink(String resortID) {
		return new LinkedResource("Resort", "http://parkbenchapi.com/api/resorts/" + resortID);
	}
	
	private LinkedResource getSelfLink(String resortID, String parkID, String diningID) {
		return new LinkedResource("self", "http://parkbenchapi.com/api/resorts/" + resortID + "/parks/" + parkID + "/dining/" + diningID);
	}

}
