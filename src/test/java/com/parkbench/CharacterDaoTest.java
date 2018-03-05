package com.parkbench;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.parkbenchapi.dao.CharacterDao;
import com.parkbenchapi.model.CharacterMeet;
import com.parkbenchapi.rowmappers.CharacterRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class CharacterDaoTest {
	
	@InjectMocks
	private CharacterDao dao = new CharacterDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<CharacterMeet> characters;
	private CharacterMeet character;
	
	private String characterName = "Aladdin";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String characterId = "aladdin";
	
	@Before
	public void setup() throws Exception {
		characters = new ArrayList<CharacterMeet>();
		character = new CharacterMeet();
		character.setCharacterName(characterName);
		character.setResort(resortName, resortId);
		character.generateLinks(resortId, characterId);
		characters.add(character);
		Mockito.doNothing().when(jdbcTemplate).execute(Matchers.anyString());
	}
	
	@Test
	public void getCharactersTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(CharacterRowMapper.class))).thenReturn(characters);
		List<CharacterMeet> actualCharacters = dao.getCharacters(resortId);
		Assert.assertEquals(characters, actualCharacters);
	}
	
	@Test
	public void getCharacterByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(CharacterRowMapper.class))).thenReturn(character);
		CharacterMeet actualCharacter = dao.getCharacterById(resortId, characterId);
		Assert.assertEquals(character, actualCharacter);
	}
	
	@Test
	public void getAllCharactersTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(CharacterRowMapper.class))).thenReturn(characters);
		List<CharacterMeet> actualCharacters = dao.getAllCharacters();
		Assert.assertEquals(characters, actualCharacters);
	}
	
	@Test
	public void getAllCharactersWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(CharacterRowMapper.class))).thenReturn(characters);
		List<CharacterMeet> actualCharacters = dao.getAllCharacters(characterName, resortName);
		Assert.assertEquals(characters, actualCharacters);
	}
	
	@Test
	public void getAllCharactersTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(CharacterRowMapper.class))).thenReturn(characters);
		List<CharacterMeet> actualCharacters = dao.getAllCharacters(null, null);
		Assert.assertEquals(characters, actualCharacters);
	}

}
