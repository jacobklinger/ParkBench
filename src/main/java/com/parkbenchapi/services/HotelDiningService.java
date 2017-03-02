package com.parkbenchapi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.parkbenchapi.model.LinkedResource;
import com.parkbenchapi.model.HotelDining;

public class HotelDiningService extends ConnectionService{
	
	// SQL Queries
	private String getAll = "SELECT * FROM HotelDining WHERE resortID = ? AND hotelID = ? ORDER BY dining_name";
	private String getPark = "SELECT * FROM HotelDining WHERE resortID = ? AND hotelID = ? AND diningID = ?";
	
	public HotelDiningService() {
		
	}
	
	/**
	 * Returns a list of all dining options in a specific hotel
	 * @param resortID
	 * @param hotelID
	 * @return
	 */
	public List<HotelDining> getAllDining(String resortID, String hotelID) {
		List<HotelDining> dining = new ArrayList<HotelDining>();
		try {
			PreparedStatement query = conn.prepareStatement(getAll);
			query.setString(1, resortID);
			query.setString(2, hotelID);
			ResultSet queryResults = query.executeQuery();
			while(queryResults.next()) {
				HotelDining current = entityToObject(queryResults);
				dining.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dining;
	}
	
	/**
	 * Returns a specific dining option in a specific hotel
	 * @param resortID
	 * @param hotelID
	 * @param diningID
	 * @return
	 */
	public HotelDining getDining(String resortID, String hotelID, String diningID) {
		HotelDining result = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getPark);
			query.setString(1, resortID);
			query.setString(2, hotelID);
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
	
	/**
	 * Converts the result of a query to an object
	 * @param queryResults
	 * @return
	 * @throws SQLException
	 */
	private HotelDining entityToObject(ResultSet queryResults) throws SQLException {
		List<LinkedResource> links = getLinks(queryResults.getString("resortID"), queryResults.getString("hotelID"), queryResults.getString("diningID"));
		HotelDining result = new HotelDining(queryResults.getString("diningID"), queryResults.getString("dining_name"), queryResults.getDouble("longitude"), queryResults.getDouble("latitude"), links);
		return result;
	}
	
	/**
	 * Builds a list of links to hotel, resort, and self
	 * @param resortID
	 * @param parkID
	 * @param attractionID
	 * @return
	 */
	private List<LinkedResource> getLinks(String resortID, String hotelID, String diningID) {
		List<LinkedResource> links = new ArrayList<LinkedResource>();
		links.add(getHotelLink(resortID, hotelID));
		links.add(getResortLink(resortID));
		links.add(getSelfLink(resortID, hotelID, diningID));
		return links;
	}
	
	/**
	 * Returns hotel link
	 * @param resortID
	 * @param parkID
	 * @return
	 */
	private LinkedResource getHotelLink(String resortID, String hotelID) {
		return new LinkedResource("Park", "http://parkbenchapi.com/api/resorts/" + resortID + "/hotels/" + hotelID);
	}
	
	/**
	 * Returns a resort link
	 * @param resortID
	 * @return
	 */
	private LinkedResource getResortLink(String resortID) {
		return new LinkedResource("Resort", "http://parkbenchapi.com/api/resorts/" + resortID);
	}
	
	/**
	 * Returns a self link
	 * @param resortID
	 * @param hotelID
	 * @param diningID
	 * @return
	 */
	private LinkedResource getSelfLink(String resortID, String hotelID, String diningID) {
		return new LinkedResource("Resort", "http://parkbenchapi.com/api/resorts/" + resortID + "/hotels/" + hotelID + "/dining/" + diningID);
	}

}
