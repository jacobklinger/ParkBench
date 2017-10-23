package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Attraction;

public class AttractionRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attraction attraction = new Attraction();
		attraction.setName(rs.getString("name"));
		attraction.setShortName(rs.getString("short_name"));
		attraction.setLongitude(rs.getDouble("longitude"));
		attraction.setLatitude(rs.getDouble("latitude"));
		attraction.setPark(rs.getString("park_name"), rs.getString("resortId"), rs.getString("parkId"));
		attraction.setResort(rs.getString("resort_name"), rs.getString("resortId"));
		attraction.setLand(rs.getString("land"));
		attraction.generateLinks(rs.getString("resortId"), rs.getString("parkId"), rs.getString("attractionId"));
		return attraction;
	}
	
}