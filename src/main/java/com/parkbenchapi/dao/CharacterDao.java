package com.parkbenchapi.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.CharacterMeet;
import com.parkbenchapi.rowmappers.CharacterRowMapper;

@Component
public class CharacterDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final String CHARACTER_COLUMNS = "SELECT character_meet.characterId, character_meet.name, resort.resortId as resortId, resort.name as resort_name";
	
	
	public List<CharacterMeet> getCharacters(String resortId) {
		jdbcTemplate.execute("use parkbench");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId"
				+ " WHERE character_meet.resortId = ?";
		List<CharacterMeet> characters = jdbcTemplate.query(sql, new Object[] {resortId}, new CharacterRowMapper());
		return characters;
	}
	
	public CharacterMeet getCharacterById(String resortId, String characterId) {
		jdbcTemplate.execute("use parkbench");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId"
				+ " WHERE character_meet.resortId = ? AND character_meet.characterId = ?";
		CharacterMeet character = (CharacterMeet) jdbcTemplate.queryForObject(sql, new Object[] {resortId, characterId}, new CharacterRowMapper());
		return character;
	}
	
	public List<CharacterMeet> getAllCharacters() {
		jdbcTemplate.execute("use parkbench");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId";
		List<CharacterMeet> characters = jdbcTemplate.query(sql, new CharacterRowMapper());
		return characters;
	}
	
	public List<CharacterMeet> getAllCharacters(String characterName, String resortName) {
		if (characterName == null) {
			characterName = "%";
		}
		else {
			characterName = "%" + characterName + "%";
		}

		if (resortName == null) {
			resortName = "%";
		}
		else {
			resortName = "%" + resortName + "%";
		}
		
		jdbcTemplate.execute("use parkbench");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId"
				+ " WHERE character_meet.name LIKE ? AND resort.name LIKE ?";
		List<CharacterMeet> characters = jdbcTemplate.query(sql, new Object[] {characterName, resortName}, new CharacterRowMapper());
		return characters;
	}

}
