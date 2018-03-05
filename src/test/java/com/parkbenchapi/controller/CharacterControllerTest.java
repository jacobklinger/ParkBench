package com.parkbenchapi.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.parkbenchapi.controller.CharacterController;
import com.parkbenchapi.dao.CharacterDao;
import com.parkbenchapi.model.CharacterMeet;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(CharacterController.class)
public class CharacterControllerTest extends ParkBenchTest {

	@MockBean
	private CharacterDao dao;
	
	private List<CharacterMeet> characters;
	
	private CharacterMeet character;
	
	private String characterName = "Aladdin";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String characterId = "aladdin";
	
	private String getCharacterMeetsByResortIdResponse = "/character/getCharactersByResort.json";
	private String getCharacterMeetByIdResponse = "/character/getCharacterById.json";
	private String getAllCharacterMeetsResponse = "/character/getAllCharacters.json";
	private String getCharacterMeetsByNameResponse = "/character/getCharactersByName.json";
	
	@Before
	public void setup() throws Exception {
		characters = new ArrayList<CharacterMeet>();
		character = new CharacterMeet();
		character.setResort(resortName, resortId);
		character.setCharacterName(characterName);
		character.generateLinks(resortId, characterId);
		characters.add(character);
	}
	
	@Test
	public void getCharacterMeetsByResort() throws Exception {
		
		Mockito.when(dao.getCharacters(Matchers.anyString())).thenReturn(characters);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/characters", resortId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getCharacterMeetsByResortIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getCharacterMeetById() throws Exception {
		Mockito.when(dao.getCharacterById(Matchers.anyString(), Matchers.anyString())).thenReturn(character);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/characters/{characterId}", resortId, characterId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getCharacterMeetByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getCharacterMeets() throws Exception {
		
		Mockito.when(dao.getAllCharacters()).thenReturn(characters);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/characters")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllCharacterMeetsResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getCharacterMeetsByName() throws Exception {
		
		Mockito.when(dao.getAllCharacters(Matchers.anyString(), Matchers.anyString())).thenReturn(characters);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/characters")
				.param("name", characterName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getCharacterMeetsByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
}
