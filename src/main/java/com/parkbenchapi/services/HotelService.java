package com.parkbenchapi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.model.LinkedResource;
import com.parkbenchapi.model.Park;
import com.parkbenchapi.model.Resort;

public class HotelService extends ConnectionService{
	
	//SQL Queries
	private String getAll = "SELECT * FROM Hotels WHERE resortID = ? ORDER BY date_opened";
	private String getHotel = "SELECT * FROM Hotels WHERE resortID = ? AND hotelID = ?";
	
	public HotelService() {
		
	}
	
	/**
	 * Returns a list of hotels for a specific resort
	 * @param resortID
	 * @return
	 */
	public List<Hotel> getHotels(String resortID) {
		List<Hotel> hotels = new ArrayList<Hotel>();
		try {
			PreparedStatement query = conn.prepareStatement(getAll);
			query.setString(1, resortID);
			ResultSet queryResults = query.executeQuery();
			while(queryResults.next()) {
				Hotel current = entityToObject(queryResults);
				hotels.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return hotels;
	}
	
	/**
	 * Returns a specific hotel for a specific resort
	 * @param resortID
	 * @param hotelID
	 * @return
	 */
	public Hotel getHotel(String resortID, String hotelID) {
		Hotel result = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getHotel);
			query.setString(1, resortID);
			query.setString(2, hotelID);
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
	 * Converts a query result into a hotel object
	 * @param queryResults
	 * @return
	 * @throws SQLException
	 */
	private Hotel entityToObject(ResultSet queryResults) throws SQLException {
		List<LinkedResource> links = getLinks(queryResults.getString("resortID"), queryResults.getString("hotelID"));
		Hotel result = new Hotel(queryResults.getString("hotelID"), queryResults.getString("hotel_name"), queryResults.getDouble("longitude"), queryResults.getDouble("latitude"), queryResults.getDate("date_opened"), links);
		return result;
	}
	
	/**
	 * Returns a list of links to resort, dining options, and self
	 * @param resortID
	 * @param hotelID
	 * @return
	 */
	private List<LinkedResource> getLinks(String resortID, String hotelID) {
		List<LinkedResource> links = new ArrayList<LinkedResource>();
		links.add(getDiningLink(resortID, hotelID));
		links.add(getResortLink(resortID));
		links.add(getSelfLink(resortID, hotelID));
		return links;
	}
	
	/**
	 * Returns a dining link
	 * @param resortID
	 * @param hotelID
	 * @return
	 */
	private LinkedResource getDiningLink(String resortID, String hotelID) {
		return new LinkedResource("Dining", "http://parkbenchapi.com/api/resorts/" + resortID + "/hotels/" + hotelID + "/dining/");
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
	 * Returns a link to this resource
	 * @param resortID
	 * @param hotelID
	 * @return
	 */
	private LinkedResource getSelfLink(String resortID, String hotelID) {
		return new LinkedResource("self", "http://parkbenchapi.com/api/resorts/" + resortID + "/hotels/" + hotelID);
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
