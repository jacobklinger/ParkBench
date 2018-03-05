package com.parkbenchapi.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.parkbenchapi.controller.ResortController;
import com.parkbenchapi.dao.ResortDao;
import com.parkbenchapi.model.Resort;
import com.parkbenchapi.test.ParkBenchTest;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@WebMvcTest(ResortController.class)
public class ResortControllerTest extends ParkBenchTest {
	
	@MockBean
	private ResortDao dao;
	
	private ArrayList<Resort> resorts;
	
	private Resort resort;
	
	private String resortName = "World";
	private String resortId = "walt-disney-world";
	private double latitude = 28.393364; 
	private double longitude = -81.573823;
	private double radius = 10000;
	
	private String getResortsResponse = "/resorts/getResortsResponse.json";
	private String getResortsByNameResponse = "/resorts/getResortsByName.json";
	private String getResortByIdResponse = "/resorts/getResortByIdResponse.json";
	private String getResortByLocationResponse = "/resorts/getResortByLocationResponse.json";
	
	@Before
	public void setup() {
		resorts = new ArrayList<Resort>();
		resort = new Resort();
		resort.setName("Walt Disney World");
		resort.setShortName("Walt Disney World");
		resort.setLatitude(28.385233);
		resort.setLongitude(-81.563874);
		resort.generateLinks("walt-disney-world");
		resorts.add(resort);
	}
	
	@Test
	public void getResorts() throws Exception {
		
		Mockito.when(dao.getAllResorts()).thenReturn(resorts);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getResortsResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getResortsByName() throws Exception {
		
		Mockito.when(dao.getAllResorts(Matchers.anyString())).thenReturn(resorts);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts")
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		JSONAssert.assertEquals(fileToString(getResortsByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getResortById() throws Exception {
		
		Mockito.when(dao.getResortById(Matchers.anyString())).thenReturn(resort);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}", resortId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		JSONAssert.assertEquals(fileToString(getResortByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getResortByLocation() throws Exception {
		
		Mockito.when(dao.getResortsByLocation(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(resorts);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/location")
				.param("latitude", String.valueOf(latitude))
				.param("longitude", String.valueOf(longitude))
				.param("radius", String.valueOf(radius))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		JSONAssert.assertEquals(fileToString(getResortByLocationResponse), result.getResponse().getContentAsString(), false);
	}

}
