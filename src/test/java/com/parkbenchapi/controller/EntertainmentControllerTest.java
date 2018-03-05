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

import com.parkbenchapi.controller.EntertainmentController;
import com.parkbenchapi.dao.EntertainmentDao;
import com.parkbenchapi.model.Entertainment;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(EntertainmentController.class)
public class EntertainmentControllerTest extends ParkBenchTest {

	@MockBean
	private EntertainmentDao dao;
	
	private List<Entertainment> entertainmentList;
	
	private Entertainment entertainment;
	
	private String entertainmentName = "Captain Jack Sparrow’s Pirate Tutorial";
	private String land = "Adventureland";
	private String parkName = "Magic Kingdom";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private String entertainmentId = "captain-jack-sparrows-pirate-tutorial";
	
	private String getEntertainmentByPark = "/entertainment/getEntertainmentByPark.json";
	private String getEntertainmentByIdResponse = "/entertainment/getEntertainmentById.json";	
	private String getAllEntertainmentResponse = "/entertainment/getAllEntertainment.json";
	private String getEntertainmentByNameResponse = "/entertainment/getEntertainmentByName.json";
	
	@Before
	public void setup() throws Exception {
		entertainmentList = new ArrayList<Entertainment>();
		entertainment = new Entertainment();
		entertainment.setName(entertainmentName);
		entertainment.setShortName(entertainmentName);
		entertainment.setLand(land);
		entertainment.setResort(resortName, resortId);
		entertainment.generateLinks(resortId, parkId, entertainmentId);
		entertainmentList.add(entertainment);
		entertainment.setPark(parkName, resortId, parkId);
	}
	
	@Test
	public void getEntertainmentByResort() throws Exception {
		
		Mockito.when(dao.getEntertainment(Matchers.anyString(), Matchers.anyString())).thenReturn(entertainmentList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}/entertainment", resortId, parkId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getEntertainmentByPark), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getEntertainmentById() throws Exception {
		Mockito.when(dao.getEntertainmentById(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(entertainment);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}/entertainment/{attractionId}", resortId, parkId, entertainmentId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getEntertainmentByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getEntertainment() throws Exception {
		
		Mockito.when(dao.getAllEntertainment()).thenReturn(entertainmentList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/entertainment")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllEntertainmentResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getEntertainmentByName() throws Exception {
		
		Mockito.when(dao.getAllEntertainment(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(entertainmentList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/entertainment")
				.param("name", entertainmentName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getEntertainmentByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
}
