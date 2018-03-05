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

import com.parkbenchapi.controller.DiningController;
import com.parkbenchapi.dao.DiningDao;
import com.parkbenchapi.model.ParkDining;
import com.parkbenchapi.model.HotelDining;
import com.parkbenchapi.model.LocatableResource;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(DiningController.class)
public class DiningControllerTest extends ParkBenchTest {

	@MockBean
	private DiningDao dao;
	
	private ArrayList<LocatableResource> parkDiningList;
	private ParkDining parkDining;
	
	private ArrayList<LocatableResource> hotelDiningList;
	private HotelDining hotelDining;
	
	private ArrayList<LocatableResource> allDiningList;
	
	private String parkDiningName = "50's Prime Time Cafe";
	private String land = "Echo Lake";
	private String parkName = "Disney’s Hollywood Studios";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "hollywood-studios";
	private String parkDiningId = "50s-prime-time-cafe";
	private double parkDiningLatitude = 28.357405;
	private double parkDiningLongitude = -81.558897;
	private double radius = 10000;
	
	private String hotelDiningName = "Chef Mickey’s";
	private String hotelDiningId = "chef-mickeys";
	private String hotelName = "Disney's Contemporary Resort ";
	private String hotelId = "contemporary";
	private double hotelDiningLatitude = 28.414385;
	private double hotelDiningLongitude = -81.574385;
	
	private String getDiningByHotelId = "/dining/getDiningByHotelId.json";
	private String getDiningByParkId = "/dining/getDiningByParkId.json";
	private String getParkDiningByIdResponse = "/dining/getParkDiningById.json";
	private String getHotelDiningByIdResponse = "/dining/getHotelDiningById.json";
	private String getDiningByLocationResponse = "/dining/getDiningByLocation.json";
	private String getAllDiningResponse = "/dining/getAllDining.json";
	private String getDiningByNameResponse = "/dining/getDiningByName.json";
	
	@Before
	public void setup() throws Exception {
		parkDiningList = new ArrayList<LocatableResource>();
		parkDining = new ParkDining();
		parkDining.setName(parkDiningName);
		parkDining.setShortName(parkDiningName);
		parkDining.setLand(land);
		parkDining.setLatitude(parkDiningLatitude);
		parkDining.setLongitude(parkDiningLongitude);
		parkDining.setResort(resortName, resortId);
		parkDining.generateLinks(resortId, parkId, parkDiningId);
		parkDiningList.add(parkDining);
		parkDining.setPark(parkName, resortId, parkId);
		
		hotelDiningList = new ArrayList<LocatableResource>();
		hotelDining = new HotelDining();
		hotelDining.setName(hotelDiningName);
		hotelDining.setShortName(hotelDiningName);
		hotelDining.setLatitude(hotelDiningLatitude);
		hotelDining.setLongitude(hotelDiningLongitude);
		hotelDining.setResort(resortName, resortId);
		hotelDining.generateLinks(resortId, hotelId, hotelDiningId);
		hotelDiningList.add(hotelDining);
		hotelDining.setHotel(hotelName, resortId, hotelId);
		
		allDiningList = new ArrayList<LocatableResource>();
		allDiningList.add(parkDining);
		allDiningList.add(hotelDining);
	}
	
	@Test
	public void getDiningByParkId() throws Exception {
		
		Mockito.when(dao.getParkDining(Matchers.anyString(), Matchers.anyString())).thenReturn(parkDiningList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}/dining", resortId, parkId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getDiningByParkId), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getParkDiningById() throws Exception {
		Mockito.when(dao.getParkDiningById(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(parkDining);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/parks/{parkId}/dining/{attractionId}", resortId, parkId, parkDiningId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getParkDiningByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getDiningByHotelId() throws Exception {
		
		Mockito.when(dao.getHotelDining(Matchers.anyString(), Matchers.anyString())).thenReturn(hotelDiningList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/hotels/{hotelId}/dining", resortId, hotelId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getDiningByHotelId), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getHotelDiningById() throws Exception {
		Mockito.when(dao.getHotelDiningById(Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(hotelDining);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/hotels/{hotelId}/dining/{attractionId}", resortId, hotelId, hotelDiningId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getHotelDiningByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getDiningByLocation() throws Exception {
		
		Mockito.when(dao.getDiningByLocation(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(parkDiningList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/dining/location", resortId)
				.param("latitude", String.valueOf(parkDiningLatitude))
				.param("longitude", String.valueOf(parkDiningLongitude))
				.param("radius", String.valueOf(radius))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getDiningByLocationResponse), result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void getDining() throws Exception {
		
		Mockito.when(dao.getAllDining()).thenReturn(parkDiningList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/dining")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllDiningResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getDiningByName() throws Exception {
		
		Mockito.when(dao.getAllDining(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString())).thenReturn(parkDiningList);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/dining")
				.param("name", parkDiningName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getDiningByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
}
