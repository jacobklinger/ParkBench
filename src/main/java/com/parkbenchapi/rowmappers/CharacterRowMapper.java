package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.CharacterMeet;

public class CharacterRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CharacterMeet character = new CharacterMeet();
		character.setCharacterName(rs.getString("name"));
		character.setAttractionName(rs.getString("attraction_name"));
		return character;
	}
	
}