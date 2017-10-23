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
		entertainment.setPark(rs.getString("park_name"), rs.getString("resortId"), rs.getString("parkId"));
		entertainment.setResort(rs.getString("resort_name"), rs.getString("resortId"));
		entertainment.setLand(rs.getString("land"));
		entertainment.generateLinks(rs.getString("resortId"), rs.getString("parkId"), rs.getString("entertainmentId"));
		return entertainment;
	}
	
}