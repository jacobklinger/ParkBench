package com.parkbenchapi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.parkbenchapi.model.LinkedResource;
import com.parkbenchapi.model.Resort;

/**
 * Service for retrieving all resorts, specific resorts, and parks for each resort from the database
 * @author Jacob Klinger
 *
 */
public class ResortsService extends ConnectionService{
	
	private String getAll = "SELECT * FROM Resorts ORDER BY date_opened";
	private String getSpecific = "SELECT * FROM Resorts WHERE resortID = ?";
	private String getParks = "SELECT parkID, park_name FROM Parks WHERE resortID = ?";
	
	public ResortsService() {
		super();
	}
	
	public List<Resort> getResorts() {
		List<Resort> resorts = new ArrayList<Resort>();
		
		try {
			System.out.println("test");
			PreparedStatement query = conn.prepareStatement(getAll);
			ResultSet results = query.executeQuery();
			while(results.next()) {
				List<LinkedResource> links = new ArrayList<LinkedResource>();
				links.add(getParksLink(results.getString(1)));
				Resort current = new Resort(results.getString(1), results.getString(2), results.getString(3), results.getString(4), results.getString(5), results.getDouble(6), results.getDouble(7), results.getDate(8), links);
				resorts.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resorts;
	}
	
	public Resort getResort(String resortID) {
		Resort result = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getSpecific);
			query.setString(1, resortID);
			ResultSet queryResults = query.executeQuery();
			queryResults.next();
			List<LinkedResource> links = new ArrayList<LinkedResource>();
			links.add(getParksLink(resortID));
			links.add(getHotelsLink(resortID));
			links.add(getSelfLink(resortID));
			result = new Resort(queryResults.getString(1), queryResults.getString(2), queryResults.getString(3), queryResults.getString(4), queryResults.getString(5), queryResults.getDouble(6), queryResults.getDouble(7), queryResults.getDate(8), links);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private LinkedResource getParksLink(String resortID) {
		return new LinkedResource("Parks", "http://parkbenchapi.com/api/api/resorts/" + resortID + "/parks");
	}
	
	private LinkedResource getHotelsLink(String resortID) {
		return new LinkedResource("Hotels", "http://parkbenchapi.com/api/api/resorts/" + resortID + "/hotels");
	}
	
	private LinkedResource getSelfLink(String resortID) {
		return new LinkedResource("self", "http://parkbenchapi.com/api/resorts/" + resortID);
	}
	
	/*private List<LinkedResource> getParksLinks(String resortID) {
		List<LinkedResource> results = null;
		
		try {
			PreparedStatement query = conn.prepareStatement(getParks);
			query.setString(1, resortID);
			ResultSet queryResults = query.executeQuery();
			results = new ArrayList<LinkedResource>();
			while(queryResults.next()) {
				LinkedResource current = new LinkedResource(queryResults.getString("park_name"), "http://parkbenchapi.com/api/resorts/" + resortID + "/" + queryResults.getString("parkID"));
				results.add(current);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return results;
	}*/

}
