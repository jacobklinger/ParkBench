package com.parkbenchapi.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.parkbenchapi.model.CharacterMeet;

public class CharacterRowMapper implements RowMapper<CharacterMeet>{

	@Override
	public CharacterMeet mapRow(ResultSet rs, int rowNum) throws SQLException {
		CharacterMeet character = new CharacterMeet();
		character.setCharacterName(rs.getString("name"));
		character.setResort(rs.getString("resort_name"), rs.getString("resortId"));
		character.generateLinks(rs.getString("resortId"), rs.getString("characterId"));
		return character;
	}
	
}