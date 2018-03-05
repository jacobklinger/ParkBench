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

import com.parkbenchapi.controller.AttractionController;
import com.parkbenchapi.dao.AttractionDao;
import com.parkbenchapi.model.Attraction;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(AttractionController.class)
public class AttractionControllerTest extends ParkBenchTest {

	@MockBean
	private AttractionDao dao;
	
	private List<Attraction> attractions;
	
	private Attraction attraction;
	
	private String attractionName = "Astro Orbiter";
	private String land = "Tomorrowland";
	private String parkName = "Magic Kingdom";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private String attractionId = "astro-orbiter";
	private double latitude = 28.417663;
	private double longitude = 81.581212;
	private double radius = 10000;
	
	private String getAttractionsByResortIdResponse = "/attractions/getAttractionsByResortId.json";
	private String getAttractionByIdResponse = "/attractions/getAttractionById.json";
	private String getAttractionsByLocationResponse = "/attractions/getAttractionsByLocation.json";
	private String getAllAttractionsResponse = "/attractions/getAllAttractions.json";
	private String getAttractionsByNameResponse = "/attractions/getAttractionsByName.json";
	
	@Before
	public void setup() throws Exception {
		attractions = new ArrayList<Attraction>();
		attraction = new Attraction();
		attraction.setName(attractionName);
		attraction.setShortName(attractionName);
		attraction.setLand(land);
		attraction.setLatitude(28.41852);
		attraction.setLongitude(-81.578985);
		attraction.setResort(resortName, resortId);
		attraction.generateLinks(resortId, parkId, attractionId);
		attractions.add(attraction);
		attraction.setPark(parkName, resortId, parkId);
	}
	
	@Test
	public void getAttractionsByResort() throws Exception {
		
		Mockito.when(dao.getAttractions(Matchers.anyString(), Matchers.anyString())).thenReturn(attractions);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}/attractions", resortId, parkId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAttractionsByResortIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getAttractionById() throws Exception {
		Mockito.when(dao.getAttractionById(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(attraction);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}/attractions/{attractionId}", resortId, parkId, attractionId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAttractionByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getAttractionByLocation() throws Exception {
		
		Mockito.when(dao.getAttractionsByLocation(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(attractions);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/attractions/location", resortId)
				.param("latitude", String.valueOf(latitude))
				.param("longitude", String.valueOf(longitude))
				.param("radius", String.valueOf(radius))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAttractionsByLocationResponse), result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void getAttractions() throws Exception {
		
		Mockito.when(dao.getAllAttractions()).thenReturn(attractions);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/attractions")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllAttractionsResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getAttractionsByName() throws Exception {
		
		Mockito.when(dao.getAllAttractions(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(attractions);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/attractions")
				.param("name", attractionName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getAttractionsByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
}
