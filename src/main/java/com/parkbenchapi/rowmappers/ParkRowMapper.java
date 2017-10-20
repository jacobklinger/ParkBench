package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Park;

public class ParkRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Park park = new Park();
		park.setName(rs.getString("name"));
		park.setShortName(rs.getString("short_name"));
		park.setLongitude(rs.getDouble("longitude"));
		park.setLatitude(rs.getDouble("latitude"));
		park.setResort(rs.getString("resort_name"), rs.getString("resortId"));
		park.generateLinks(rs.getString("parkId"));
		return park;
	}
	
}