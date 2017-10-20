package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Hotel;

public class SnackRowMapper  implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Hotel hotel = new Hotel();
		hotel.setName(rs.getString("name"));
		hotel.setShortName(rs.getString("short_name"));
		hotel.setLongitude(rs.getDouble("longitude"));
		hotel.setLatitude(rs.getDouble("latitude"));
		hotel.setResortArea(rs.getString("resort_area"));
		return hotel;
	}
	
}