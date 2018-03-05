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

import com.parkbenchapi.controller.HotelController;
import com.parkbenchapi.dao.HotelDao;
import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.test.ParkBenchTest;

@RunWith(SpringRunner.class)
@WebMvcTest(HotelController.class)
public class HotelControllerTest extends ParkBenchTest {

	@MockBean
	private HotelDao dao;
	
	private List<Hotel> hotels;
	
	private Hotel hotel;
	
	private String hotelName = "magic";
	private String resortName = "world";
	private String resortId = "walt-disney-world";
	private String hotelId = "contemporary-resort";
	private double latitude = 28.417663;
	private double longitude = 81.581212;
	private double radius = 10000;
	
	private String getHotelsByResortIdResponse = "/hotels/getHotelsByResortId.json";
	private String getHotelByIdResponse = "/hotels/getHotelById.json";
	private String getHotelsByLocationResponse = "/hotels/getHotelsByLocation.json";
	private String getAllHotelsResponse = "/hotels/getAllHotels.json";
	private String getHotelsByNameResponse = "/hotels/getHotelsByName.json";
	
	@Before
	public void setup() throws Exception {
		hotels = new ArrayList<Hotel>();
		hotel = new Hotel();
		hotel.setName("Disney's All-Star Movies Resort ");
		hotel.setShortName("All-Star Movies  ");
		hotel.setLatitude(28.335886);
		hotel.setLongitude(-81.572209);
		hotel.setResortArea("Animal Kingdom");
		hotel.setResort("Walt Disney World", "walt-disney-world");
		hotel.generateLinks("walt-disney-world", "all-star-movies");
		hotels.add(hotel);
	}
	
	@Test
	public void getHotelsByResort() throws Exception {
		
		Mockito.when(dao.getHotels(Matchers.anyString())).thenReturn(hotels);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/hotels", resortId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getHotelsByResortIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getHotelById() throws Exception {
		Mockito.when(dao.getHotelById(Matchers.anyString(), Matchers.anyString())).thenReturn(hotel);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/resorts/{resortId}/hotels/{hotelId}", resortId, hotelId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getHotelByIdResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getHotelByLocation() throws Exception {
		
		Mockito.when(dao.getHotelsByLocation(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(hotels);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/hotels/location", resortId)
				.param("latitude", String.valueOf(latitude))
				.param("longitude", String.valueOf(longitude))
				.param("radius", String.valueOf(radius))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getHotelsByLocationResponse), result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	public void getHotels() throws Exception {
		
		Mockito.when(dao.getAllHotels()).thenReturn(hotels);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/hotels")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		
		JSONAssert.assertEquals(fileToString(getAllHotelsResponse), result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getHotelsByName() throws Exception {
		
		Mockito.when(dao.getAllHotels(Matchers.anyString(), Matchers.anyString())).thenReturn(hotels);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders
				.get("/hotels")
				.param("name", hotelName)
				.param("name", resortName)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
		 
		JSONAssert.assertEquals(fileToString(getHotelsByNameResponse), result.getResponse().getContentAsString(), false);
	}
	
}
