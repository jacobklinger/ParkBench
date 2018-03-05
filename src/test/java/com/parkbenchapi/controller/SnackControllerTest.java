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

import com.parkbenchapi.controller.SnackController;
import com.parkbenchapi.dao.SnackDao;
import com.parkbenchapi.model.Snack;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(SnackController.class)
public class SnackControllerTest extends ParkBenchTest {

	@MockBean
	private SnackDao dao;
	
	private List<Snack> snacks;
	
	private Snack snack;
	
	private String snackName = "Mickey Ice Cream Bar";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String snackId = "mickey-ice-cream-bar";
	
	private String getSnacksByResortIdResponse = "/snacks/getSnacksByResort.json";
	private String getSnackByIdResponse = "/snacks/getSnackById.json";
	private String getAllSnacksResponse = "/snacks/getAllSnacks.json";
	private String getSnacksByNameResponse = "/snacks/getSnacksByName.json";
	
	@Before
	public void setup() throws Exception {
		snacks = new ArrayList<Snack>();
		snack = new Snack();
		snack.setResort(resortName, resortId);
		snack.setName(snackName);
		snack.generateLinks(resortId, snackId);
		snacks.add(snack);
	}
	
	@Test
	public void getSnacksByResort() throws Exception {
		
		Mockito.when(dao.getSnacks(Matchers.anyString())).thenReturn(snacks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/snacks", resortId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getSnacksByResortIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getSnackById() throws Exception {
		Mockito.when(dao.getSnackById(Matchers.anyString(), Matchers.anyString())).thenReturn(snack);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/snacks/{snackId}", resortId, snackId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getSnackByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getSnacks() throws Exception {
		
		Mockito.when(dao.getAllSnacks()).thenReturn(snacks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/snacks")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllSnacksResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getSnacksByName() throws Exception {
		
		Mockito.when(dao.getAllSnacks(Matchers.anyString(), Matchers.anyString())).thenReturn(snacks);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/snacks")
				.param("name", snackName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getSnacksByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
}
