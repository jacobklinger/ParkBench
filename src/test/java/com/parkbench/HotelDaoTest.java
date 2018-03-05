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
import com.parkbenchapi.dao.HotelDao;
import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.rowmappers.HotelRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class HotelDaoTest {
	
	@InjectMocks
	private HotelDao dao = new HotelDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<Hotel> parks;
	private Hotel park;
	
	private String parkName = "Magic Kingdom";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private double latitude = 28.417663;
	private double longitude = 81.581212;
	private double radius = 10000;
	
	@Before
	public void setup() throws Exception {
		parks = new ArrayList<Hotel>();
		park = new Hotel();
		park.setName(parkName);
		park.setShortName(parkName);
		park.setLatitude(latitude);
		park.setLongitude(longitude);
		park.setResort(resortName, resortId);
		park.generateLinks(resortId, parkId);
		parks.add(park);
		Mockito.doNothing().when(jdbcTemplate).execute(Matchers.anyString());
	}
	
	@Test
	public void getHotelsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(HotelRowMapper.class))).thenReturn(parks);
		List<Hotel> actualHotels = dao.getHotels(resortId);
		Assert.assertEquals(parks, actualHotels);
	}
	
	@Test
	public void getHotelByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(HotelRowMapper.class))).thenReturn(park);
		Hotel actualHotel = dao.getHotelById(resortId, parkId);
		Assert.assertEquals(park, actualHotel);
	}
	
	@Test
	public void getHotelByLocationTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(HotelRowMapper.class))).thenReturn(parks);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius - 1);
		List<Hotel> actualHotels = dao.getHotelsByLocation(longitude, latitude, radius);
		Assert.assertEquals(parks, actualHotels);
	}
	
	@Test
	public void getHotelByLocationExpectEmptyTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(HotelRowMapper.class))).thenReturn(parks);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius + 1);
		List<Hotel> actualHotels = dao.getHotelsByLocation(longitude, latitude, radius);
		Assert.assertTrue(actualHotels.isEmpty());
	}
	
	@Test
	public void getAllHotelsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(HotelRowMapper.class))).thenReturn(parks);
		List<Hotel> actualHotels = dao.getAllHotels();
		Assert.assertEquals(parks, actualHotels);
	}
	
	@Test
	public void getAllHotelsWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(HotelRowMapper.class))).thenReturn(parks);
		List<Hotel> actualHotels = dao.getAllHotels(parkName, resortName);
		Assert.assertEquals(parks, actualHotels);
	}
	
	@Test
	public void getAllHotelsTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(HotelRowMapper.class))).thenReturn(parks);
		List<Hotel> actualHotels = dao.getAllHotels(null, null);
		Assert.assertEquals(parks, actualHotels);
	}

}
