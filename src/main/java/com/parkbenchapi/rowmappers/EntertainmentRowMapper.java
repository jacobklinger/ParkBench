package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Entertainment;

public class EntertainmentRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Entertainment entertainment = new Entertainment();
		entertainment.setName(rs.getString("name"));
		entertainment.setShortName(rs.getString("short_name"));
		entertainment.setLongitude(rs.getDouble("longitude"));
		entertainment.setLatitude(rs.getDouble("latitude"));
		entertainment.generateLinks(rs.getString("entertainmentId"));
		return entertainment;
	}
	
}