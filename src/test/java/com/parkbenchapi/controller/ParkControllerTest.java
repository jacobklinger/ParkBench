package com.parkbenchapi.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;

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

import com.parkbenchapi.controller.ParkController;
import com.parkbenchapi.dao.ParkDao;
import com.parkbenchapi.model.Park;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkController.class)
public class ParkControllerTest extends ParkBenchTest {

	@MockBean
	private ParkDao dao;
	
	private ArrayList<Park> parks;
	
	private Park park;
	
	private String parkName = "magic";
	private String resortName = "world";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private double latitude = 28.417663;
	private double longitude = 81.581212;
	private double radius = 10000;
	
	private String getParksByResortIdResponse = "/parks/getParksByResortId.json";
	private String getParkByIdResponse = "/parks/getParkById.json";
	private String  getParksByLocationResponse = "/parks/getsParkByLocation.json";
	private String getAllParksResponse = "/parks/getAllParks.json";
	private String getParksByNameResponse = "/parks/getParksByName.json";
	
	@Before
	public void setup() throws Exception {
		parks = new ArrayList<Park>();
		park = new Park();
		park.setName("Magic Kingdom");
		park.setShortName("Magic Kingdom");
		park.setLatitude(28.417663);
		park.setLongitude(-81.581212);
		park.setResort("Walt Disney World", "walt-disney-world");
		park.generateLinks("walt-disney-world", "magic-kingdom");
		parks.add(park);
	}
	
	@Test
	public void getParksByResort() throws Exception {
		
		Mockito.when(dao.getParks(Matchers.anyString())).thenReturn(parks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks", resortId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getParksByResortIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getParkById() throws Exception {
		Mockito.when(dao.getParkById(Matchers.anyString(), Matchers.anyString())).thenReturn(park);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}", resortId, parkId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getParkByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getParkByLocation() throws Exception {
		
		Mockito.when(dao.getParkByLocation(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(parks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/parks/location", resortId)
				.param("latitude", String.valueOf(latitude))
				.param("longitude", String.valueOf(longitude))
				.param("radius", String.valueOf(radius))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getParksByLocationResponse), result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void getParks() throws Exception {
		
		Mockito.when(dao.getAllParks()).thenReturn(parks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/parks")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllParksResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getParksByName() throws Exception {
		
		Mockito.when(dao.getAllParks(Matchers.anyString(), Matchers.anyString())).thenReturn(parks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/parks")
				.param("name", parkName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getParksByNameResponse ), result.getResponse().getContentAsString(), false);
	}
	
}
