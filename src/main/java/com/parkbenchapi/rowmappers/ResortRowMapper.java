package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Resort;

public class ResortRowMapper implements RowMapper<Resort>{

	@Override
	public Resort mapRow(ResultSet rs, int rowNum) throws SQLException {
		Resort resort = new Resort();
		resort.setName(rs.getString("name"));
		resort.setShortName(rs.getString("short_name"));
		resort.setLongitude(rs.getDouble("longitude"));
		resort.setLatitude(rs.getDouble("latitude"));
		resort.generateLinks(rs.getString("resortId"));
		return resort;
	}
	
}