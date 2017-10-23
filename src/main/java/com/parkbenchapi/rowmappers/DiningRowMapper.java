package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.HotelDining;
import com.parkbenchapi.model.ParkDining;

public class DiningRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		if (rs.getMetaData().getTableName(1).equals("park_dining")) {
			ParkDining dining = new ParkDining();
			dining.setName(rs.getString("name"));
			dining.setShortName(rs.getString("short_name"));
			dining.setLand(rs.getString("land"));
			dining.setLongitude(rs.getDouble("longitude"));
			dining.setLatitude(rs.getDouble("latitude"));
			dining.setPark(rs.getString("park_name"), rs.getString("resortId"), rs.getString("parkId"));
			dining.setResort(rs.getString("resort_name"), rs.getString("resortId"));
			dining.generateLinks(rs.getString("resortId"), rs.getString("parkId"), rs.getString("diningId"));
			
			return dining;
		}
		else {
			HotelDining dining = new HotelDining();
			dining.setName(rs.getString("name"));
			dining.setShortName(rs.getString("short_name"));
			dining.setLongitude(rs.getDouble("longitude"));
			dining.setLatitude(rs.getDouble("latitude"));
			dining.setHotel(rs.getString("hotel_name"), rs.getString("resortId"), rs.getString("hotelId"));
			dining.setResort(rs.getString("resort_name"), rs.getString("resortId"));
			dining.generateLinks(rs.getString("resortId"), rs.getString("hotelId"), rs.getString("diningId"));
			
			return dining;
		}

	}
	
}