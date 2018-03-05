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
import com.parkbenchapi.dao.ParkDao;
import com.parkbenchapi.model.Park;
import com.parkbenchapi.rowmappers.ParkRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class ParkDaoTest {
	
	@InjectMocks
	private ParkDao dao = new ParkDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<Park> parks;
	private Park park;
	
	private String parkName = "Magic Kingdom";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private double latitude = 28.417663;
	private double longitude = 81.581212;
	private double radius = 10000;
	
	@Before
	public void setup() throws Exception {
		parks = new ArrayList<Park>();
		park = new Park();
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
	public void getParksTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ParkRowMapper.class))).thenReturn(parks);
		List<Park> actualParks = dao.getParks(resortId);
		Assert.assertEquals(parks, actualParks);
	}
	
	@Test
	public void getParkByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ParkRowMapper.class))).thenReturn(park);
		Park actualPark = dao.getParkById(resortId, parkId);
		Assert.assertEquals(park, actualPark);
	}
	
	@Test
	public void getParkByLocationTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(ParkRowMapper.class))).thenReturn(parks);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius - 1);
		List<Park> actualParks = dao.getParkByLocation(longitude, latitude, radius);
		Assert.assertEquals(parks, actualParks);
	}
	
	@Test
	public void getParkByLocationExpectEmptyTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(ParkRowMapper.class))).thenReturn(parks);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius + 1);
		List<Park> actualParks = dao.getParkByLocation(longitude, latitude, radius);
		Assert.assertTrue(actualParks.isEmpty());
	}
	
	@Test
	public void getAllParksTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(ParkRowMapper.class))).thenReturn(parks);
		List<Park> actualParks = dao.getAllParks();
		Assert.assertEquals(parks, actualParks);
	}
	
	@Test
	public void getAllParksWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ParkRowMapper.class))).thenReturn(parks);
		List<Park> actualParks = dao.getAllParks(parkName, resortName);
		Assert.assertEquals(parks, actualParks);
	}
	
	@Test
	public void getAllParksTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ParkRowMapper.class))).thenReturn(parks);
		List<Park> actualParks = dao.getAllParks(null, null);
		Assert.assertEquals(parks, actualParks);
	}

}
