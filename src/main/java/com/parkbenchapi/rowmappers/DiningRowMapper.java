package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Dining;

public class DiningRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dining dining = new Dining();
		dining.setName(rs.getString("name"));
		dining.setShortName(rs.getString("short_name"));
		dining.setLongitude(rs.getDouble("longitude"));
		dining.setLatitude(rs.getDouble("latitude"));
		
		if (rs.getString("hotel") != null) {
			dining.setHotel(rs.getString("hotel"));
		}
		if (rs.getString("park") != null) {
			dining.setPark(rs.getString("park"));
		}
		if (rs.getString("land") != null) {
			dining.setLand(rs.getString("land"));
		}
		
		return dining;
	}
	
}