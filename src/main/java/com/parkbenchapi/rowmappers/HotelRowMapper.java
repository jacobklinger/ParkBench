package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.model.LocatableResource;

public class HotelRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Hotel hotel = new Hotel();
		hotel.setName(rs.getString("name"));
		hotel.setShortName(rs.getString("short_name"));
		hotel.setLongitude(rs.getDouble("longitude"));
		hotel.setLatitude(rs.getDouble("latitude"));
		hotel.setResortArea(rs.getString("resort_area"));
		hotel.setResort(rs.getString("resort_name"), rs.getString("resortId"));
		hotel.generateLinks(rs.getString("hotelId"));
		return hotel;
	}
	
}