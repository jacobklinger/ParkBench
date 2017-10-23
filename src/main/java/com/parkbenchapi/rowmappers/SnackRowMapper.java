package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.Snack;

public class SnackRowMapper  implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Snack snack = new Snack();
		snack.setName(rs.getString("name"));
		snack.setResort(rs.getString("resort_name"), rs.getString("resortId"));
		snack.generateLinks(rs.getString("resortId"), rs.getString("snackId"));
		return snack;
	}
	
}